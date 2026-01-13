import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.Prestamo;
import com.banco.evaluacion.model.TipoPrestamo;
import com.banco.evaluacion.service.BlocHistorialService;
import com.banco.evaluacion.service.EvaluadorRiesgoService;

import java.util.Random;

public class SistemaBancarioApp {
    static void main() {
        //PRUEBA DE LOS RECORDS, LOS DATOS SON EVALUADOS POR EL EVALUADOR DE RIESGO Y GUARDADOS EN UN ARCHIVO DE TEXTO
        Random random = new Random();
        BlocHistorialService historial = new BlocHistorialService();
        EvaluadorRiesgoService evaluador = new EvaluadorRiesgoService(historial);
//        Cliente cliente = new Cliente("Mario",25,89,4200,false);
//        Prestamo prestamo = new Prestamo(5000, 6,TipoPrestamo.PERSONAL);

        String[] nombres = {"Ana", "Luis", "Carlos", "Maria", "Jose", "Lucia", "Jorge", "Elena", "Pedro", "Sofia"};

        for (int i = 1; i <= 20; i++) {
            // --- DATOS DEL CLIENTE ---
            String nombre = nombres[random.nextInt(nombres.length)] + " " + i;
            int edad = 15 + random.nextInt(65);
            int score = 20 + random.nextInt(80);

            // Generar sueldo con 2 decimales
            double sueldoSocio = 1000 + (5000 * random.nextDouble());
            double sueldoLimpio = Math.round(sueldoSocio * 100.0) / 100.0; // El truco de los 2 decimales

            boolean tieneDeuda = random.nextBoolean();

            Cliente cliente = new Cliente(nombre, edad, score, sueldoLimpio, tieneDeuda);

            // --- DATOS DEL PRÉSTAMO ---
            double montoBruto = 500 + (15000 * random.nextDouble());
            double montoLimpio = Math.round(montoBruto * 100.0) / 100.0; // Redondeo a 2 decimales

            int plazo = 6 + (random.nextInt(6) * 6);
            TipoPrestamo tipo = TipoPrestamo.values()[random.nextInt(TipoPrestamo.values().length)];

            Prestamo prestamo = new Prestamo(montoLimpio, plazo, tipo);

            System.out.println("\n>>> Evaluación #" + i);
            evaluador.evaluar(cliente, prestamo);
        }

        historial.mostrarResumen();
    }
}
