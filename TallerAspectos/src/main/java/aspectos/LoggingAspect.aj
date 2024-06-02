package aspectos;

import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

aspect LoggingAspect {

    private Logger logger = Logger.getLogger("FigureLogger");

    // Aqui se crea el Logger.
    static {
        try {
            FileHandler fileHandler = new FileHandler("log_figuras.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            Logger.getLogger("FigureLogger").addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Se crea el pointcut que recibe un object de tipo GeometricalObject que guarda toda la informacion de la figura.
    //Este pointcut se ejecuta
    pointcut creacionFigura(GeometricalObject object): 
    	call(void hr.fer.zemris.java.hw16.jvdraw.model.DocumentModel.add(GeometricalObject)) && args(object); //Como argumento recibe el objeto (Para poder sacar su informacion)

    //Se ejecuta antes
    before(GeometricalObject object): creacionFigura(object) {
        crearLog(object); //Se llama a la funcion para crear el log
    }

    private void crearLog(GeometricalObject object) {
        String logMessage = String.format("Fecha y Hora: %tF %tT, Tipo de Figura: %s, Color: %s, Coordenadas: (%d, %d)",
                System.currentTimeMillis(), System.currentTimeMillis(),
                object.getClass().getSimpleName(), object.getFgColor(), object.getStartPoint().x, object.getStartPoint().y);

        logger.info(logMessage);
    }
}
