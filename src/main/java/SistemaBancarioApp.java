import com.banco.evaluacion.repository.ClienteRepository;
import com.banco.evaluacion.repository.PrestamoRepository;
import com.banco.evaluacion.service.BlocHistorialService;
import com.banco.evaluacion.service.CalculadoraPrestamo;
import com.banco.evaluacion.service.EvaluadorRiesgoService;

import java.sql.SQLException;

public class SistemaBancarioApp {
    static void main() {

        BlocHistorialService blocService = new BlocHistorialService(); // guarda la informacion los prestamos aprobados o rechazados
        ClienteRepository repoCliente = new ClienteRepository();
        PrestamoRepository repoPrestamo = new PrestamoRepository();
        CalculadoraPrestamo calculadoraPrestamo = new CalculadoraPrestamo(); //hace el calculo de las prestamos
        EvaluadorRiesgoService evaluadorRiesgoService = new EvaluadorRiesgoService(blocService,calculadoraPrestamo,repoCliente,repoPrestamo);

        System.out.println("INICIANDO REVISION DE PRESTAMOS");
        try {
            System.out.println("revisando...");
            evaluadorRiesgoService.revisarPendientes();
            System.out.println("REVISION TERMINADA");
        } catch (SQLException e) {
            System.err.println("Error de conexion a la base de datos: " + e.getMessage());
        }
        catch(Exception e){
            System.err.println("Error inesperado: "+e.getMessage());
        }
    }
}