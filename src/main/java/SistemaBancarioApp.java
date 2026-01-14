import com.banco.evaluacion.exception.PreAprobacionException;
import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.EstadoPrestamo;
import com.banco.evaluacion.model.Prestamo;
import com.banco.evaluacion.model.TipoPrestamo;
import com.banco.evaluacion.repository.ClienteRepository;
import com.banco.evaluacion.repository.PrestamoRepository;
import com.banco.evaluacion.service.BlocHistorialService;
import com.banco.evaluacion.service.EvaluadorRiesgoService;

import java.sql.SQLException;

public class SistemaBancarioApp {
    static void main() {
//        Cliente cliente = new Cliente("Luisa", 20, 89,8500.0,false,true);
//        Prestamo prestamo = new Prestamo(2000,12, TipoPrestamo.PERSONAL, EstadoPrestamo.PENDIENTE);
        EvaluadorRiesgoService evaluadorRiesgoService = new EvaluadorRiesgoService(new BlocHistorialService());
//        ClienteRepository repo = new ClienteRepository();
//        PrestamoRepository prestamoRepository = new PrestamoRepository();
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