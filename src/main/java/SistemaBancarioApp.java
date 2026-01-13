import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.Prestamo;
import com.banco.evaluacion.model.TipoPrestamo;
import com.banco.evaluacion.service.BlocHistorialService;
import com.banco.evaluacion.service.EvaluadorRiesgoService;

public class SistemaBancarioApp {
    static void main() {
        //PRUEBA DE LOS RECORDS, LOS DATOS SON EVALUADOS POR EL EVALUADOR DE RIESGO Y GUARDADOS EN UN ARCHIVO DE TEXTO
        Cliente cliente = new Cliente("Luis",35,90,3500,false);
        Prestamo prestamo = new Prestamo(1000,6,TipoPrestamo.PERSONAL);
        BlocHistorialService historial = new BlocHistorialService();
        EvaluadorRiesgoService evaluador = new EvaluadorRiesgoService(historial);

        evaluador.evaluar(cliente,prestamo);
        historial.mostrarResumen();
    }
}