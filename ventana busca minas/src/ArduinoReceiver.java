
import com.fazecast.jSerialComm.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleIntegerProperty;

class ArduinoReceiver {

    private SimpleIntegerProperty variableY = new SimpleIntegerProperty();
    private SimpleIntegerProperty variableX = new SimpleIntegerProperty();
SerialPort port;
    public ArduinoReceiver() {
        // Configurar el puerto serie
        port = SerialPort.getCommPort("COM6"); // Cambiar a tu puerto Arduino
        port.openPort();
        port.setBaudRate(9600); // Configurar la velocidad del puerto serie según la configuración de Arduino
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 100, 0); // Configurar los tiempos de espera

        // Crear un hilo para leer los datos del puerto serie y actualizar la propiedad variableProperty
        Thread thread = new Thread(() -> {
            while (true) {
                byte[] buffer = new byte[1024];
                int numRead = port.readBytes(buffer, buffer.length);
                
                if (numRead > 0) {
                    String receivedData = new String(buffer, 0, numRead);
                    Matcher matcher = Pattern.compile("\\d+").matcher(receivedData);
                    int sum = 0;
                    while (matcher.find()) {
                        int data = Integer.parseInt(matcher.group());
                        if (data == 1) {
                            if (variableY.get() < 7) {
                                variableY.set(variableY.get() + 1);
                            }

                        } else if (data == 4) {
                            if (variableY.get() > 0) {
                                variableY.set(variableY.get() - 1);
                            }
                        } else if (data == 2) {
                            if (variableX.get() < 7) {
                                variableX.set(variableX.get() + 1);
                            }
                        } else if (data == 3) {
                            if (variableX.get() > 0) {
                                variableX.set(variableX.get() - 1);
                            }
                        }

                    }

                }
            }
        }
        );
        thread.setDaemon(true);
        thread.start();
    }
    public void setVariableY(int value) {
        variableY.set(value);
    }
    public void setVariableX(int value) {
        variableX.set(value);
    }

    public SimpleIntegerProperty variableProperty() {
        return variableY;
    }

    public SimpleIntegerProperty variable2XProperty() {
        return variableX;
    }
    public void enviarSenal(String senal) {
    port.writeBytes(senal.getBytes(), senal.length());
        System.out.println("Señal enviada");
}

}
