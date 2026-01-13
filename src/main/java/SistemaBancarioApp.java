import com.banco.evaluacion.exception.PreAprobacionException;
import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.Prestamo;
import com.banco.evaluacion.model.TipoPrestamo;
import com.banco.evaluacion.service.BlocHistorialService;
import com.banco.evaluacion.service.CalculadoraPrestamo;
import com.banco.evaluacion.service.EvaluadorRiesgoService;

public class SistemaBancarioApp {
    static void main() {
        //PRUEBA DE LOS RECORDS, LOS DATOS SON EVALUADOS POR EL EVALUADOR DE RIESGO Y GUARDADOS EN UN ARCHIVO DE TEXTO
        EvaluadorRiesgoService evaluadorRiesgoService = new EvaluadorRiesgoService();
        Cliente cliente = new Cliente("Juan",19,89,1200,false);
        Prestamo prestamo = new Prestamo(5000, 6,TipoPrestamo.PERSONAL);
        CalculadoraPrestamo calculadoraPrestamo = new CalculadoraPrestamo();
        BlocHistorialService blocHistorialService = new BlocHistorialService();

        System.out.println("\nCliente:");
        System.out.println("Nombre: " + cliente.nombre());
        System.out.println("Sueldo Neto: S/ " + cliente.sueldoNeto());
        System.out.println("Historial Crediticio: " + cliente.historialCrediticio() + "/100");

        System.out.println("\n--- Detalles prestamo ---");
        System.out.println("Monto solicitado: S/ " + prestamo.monto());
        System.out.println("Tipo: " + prestamo.tipoPrestamo());
        System.out.println("Plazo: " + prestamo.plazoMeses() + " meses");

        try {
            evaluadorRiesgoService.validarPrestamo(cliente,prestamo);
            double cuotaAproximada = calculadoraPrestamo.calcularCuotaMensual(prestamo);
            System.out.println("\nCuota mensual base (sin intereses): S/ " + String.format("%.2f", cuotaAproximada));
            blocHistorialService.actualizar(cliente,prestamo,true,"Prestamo aprobado.");
        }catch (PreAprobacionException e){
            System.err.println(e.getMessage());
            blocHistorialService.actualizar(cliente,prestamo,false,e.getMessage());
        }
    }
}
