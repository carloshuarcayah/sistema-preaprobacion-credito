import com.banco.evaluacion.exception.PreAprobacionException;
import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.repository.ClienteRepository;

import java.sql.SQLException;

public class SistemaBancarioApp {
    static void main() {
        Cliente cliente = new Cliente("Mario", 30, 89,3500.0,false,true);
        ClienteRepository repo = new ClienteRepository();

        try {
            int idGenerado = repo.guardar(cliente);
            System.out.println("Cliente guardado con ID: " + idGenerado);
            cliente = repo.buscarPorId(1).orElseThrow(()->new PreAprobacionException("error desconocido"));
            System.out.println("Cliente encontrado: "+cliente);

        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
        }
        catch(PreAprobacionException e){
            System.err.println("Error de negocios: "+e.getMessage());
        }
    }
}