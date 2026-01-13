import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.EstadoPrestamo;
import com.banco.evaluacion.model.Prestamo;
import com.banco.evaluacion.model.TipoPrestamo;
import com.banco.evaluacion.repository.ClienteRepository;
import com.banco.evaluacion.service.BlocHistorialService;
import com.banco.evaluacion.service.EvaluadorRiesgoService;

import java.sql.SQLException;

public class SistemaBancarioApp {
    static void main() {
        Cliente diego = new Cliente("Diego", 30, 70,7500.0,false);
        ClienteRepository repo = new ClienteRepository();

        try {
            int id = repo.guardar(diego);
            System.out.println("Cliente guardado con ID: " + id);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}