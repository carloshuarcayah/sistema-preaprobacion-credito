import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.Prestamo;
import com.banco.evaluacion.model.TipoPrestamo;
import com.banco.evaluacion.service.EvaluadorRiesgoService;

public class SistemaBancarioApp {
    static void main() {
        //PRUEBA DE LOS RECORDS, LOS DATOS SON EVALUADOS POR EL EVALUADOR DE RIESGO Y GUARDADOS EN UN ARCHIVO DE TEXTO
        EvaluadorRiesgoService evaluadorRiesgoService = new EvaluadorRiesgoService();
        Cliente cliente = new Cliente("Mario",25,89,4200,false);
        Prestamo prestamo = new Prestamo(5000, 6,TipoPrestamo.PERSONAL);

        evaluadorRiesgoService.evaluar(cliente,prestamo);
    }
}
