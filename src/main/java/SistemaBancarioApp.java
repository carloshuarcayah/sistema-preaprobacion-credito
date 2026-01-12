import com.banco.evaluacion.exception.PreAprobacionException;
import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.Prestamo;
import com.banco.evaluacion.model.TipoPrestamo;
import com.banco.evaluacion.service.CalculadoraPrestamo;

public class SistemaBancarioApp {
    static void main() {
        //PRUEBA DE LOS RECORDS, TODOS LOS DATOS SON CORRECTOS Y SUPUESTAMENTE YA PASARON
        Cliente cliente = new Cliente("Luis",19,89,4000,false);
        Prestamo prestamo = new Prestamo(5000, 6,TipoPrestamo.PERSONAL);
        CalculadoraPrestamo calculadoraPrestamo = new CalculadoraPrestamo();

        System.out.println("\nCliente:");
        System.out.println("Nombre: " + cliente.nombre());
        System.out.println("Sueldo Neto: S/ " + cliente.sueldoNeto());
        System.out.println("Historial Crediticio: " + cliente.historialCrediticio() + "/100");

        System.out.println("\n--- Detalles prestamo ---");
        System.out.println("Monto solicitado: S/ " + prestamo.monto());
        System.out.println("Tipo: " + prestamo.tipoPrestamo());
        System.out.println("Plazo: " + prestamo.plazoMeses() + " meses");

        //YA HACEMOS CALCULOS Y CONTROLAMOS EXCEPCION
        try {
            double cuotaAproximada = calculadoraPrestamo.calcularCuotaMensual(prestamo);
            System.out.println("\nCuota mensual base (sin intereses): S/ " + String.format("%.2f", cuotaAproximada));
        }catch (PreAprobacionException e){
            System.err.println(e.getMessage());
        }
    }
}
