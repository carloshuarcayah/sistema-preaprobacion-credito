import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.Prestamo;

public class SistemaBancarioApp {
    static void main() {
        //PRUEBA DE LOS RECORDS, TODOS LOS DATOS SON CORRECTOS Y SUPUESTAMENTE YA PASARON
        Cliente cliente = new Cliente("Luis",19,89,4000,false);
        Prestamo prestamo = new Prestamo(5000, 6,"Personal");

        System.out.println("\nCliente:");
        System.out.println("Nombre: " + cliente.nombre());
        System.out.println("Sueldo Neto: S/ " + cliente.sueldoNeto());
        System.out.println("Historial Crediticio: " + cliente.historialCrediticio() + "/100");

        System.out.println("\n--- Detalles prestamo ---");
        System.out.println("Monto solicitado: S/ " + prestamo.monto());
        System.out.println("Tipo: " + prestamo.tipoPrestamo());
        System.out.println("Plazo: " + prestamo.plazoMeses() + " meses");

        //AQUI SIN INTERESES, LUEGO SE APLICARAN LOS INTERESES SEGUN EL TIPO DE PRESTAMO
        double cuotaAproximada = prestamo.monto() / prestamo.plazoMeses();
        System.out.println("\nCuota mensual base (sin intereses): S/ " + String.format("%.2f", cuotaAproximada));
    }
}
