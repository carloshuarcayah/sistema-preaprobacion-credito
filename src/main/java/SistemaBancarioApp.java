import com.banco.evaluacion.exception.PreAprobacionException;
import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.EstadoPrestamo;
import com.banco.evaluacion.model.Prestamo;
import com.banco.evaluacion.model.TipoPrestamo;
import com.banco.evaluacion.repository.ClienteRepository;
import com.banco.evaluacion.repository.PrestamoRepository;
import com.banco.evaluacion.service.BlocHistorialService;
import com.banco.evaluacion.service.CalculadoraPrestamo;
import com.banco.evaluacion.service.EvaluadorRiesgoService;

import java.sql.SQLException;

public class SistemaBancarioApp {
    static void main() {
        BlocHistorialService blocService = new BlocHistorialService();
        ClienteRepository repoCliente = new ClienteRepository();
        PrestamoRepository repoPrestamo = new PrestamoRepository();
        CalculadoraPrestamo calculadoraPrestamo = new CalculadoraPrestamo();
        EvaluadorRiesgoService evaluadorRiesgoService = new EvaluadorRiesgoService(blocService,calculadoraPrestamo,repoCliente,repoPrestamo);
        try {
//            evaluadorRiesgoService.evaluar(cliente,prestamo);
//
//            int idGenerado = repo.guardar(cliente);
//            System.out.println("Cliente guardado con ID: " + idGenerado);
//            System.out.println("Cliente encontrado: "+cliente);
//          repo.eliminarClientePorId(4);
//            prestamoRepository.guardar(prestamo,idGenerado,"Prestamo ha sido aprobado");
            evaluadorRiesgoService.revisarPendientes();
        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
        }
        catch(PreAprobacionException e){
            System.err.println("Error de negocios: "+e.getMessage());
        }
    }
}