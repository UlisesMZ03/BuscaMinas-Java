import com.fazecast.jSerialComm.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArduinoReceiver {

    private int variable = 0;

    public ArduinoReceiver() {
        SerialPort port = SerialPort.getCommPort("COM6"); // Cambiar a tu puerto Arduino
        port.openPort();
        port.setBaudRate(9600); // Configurar la velocidad del puerto serie según la configuración de Arduino
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 100, 0); // Configurar los tiempos de espera

        while (true) {
            byte[] buffer = new byte[1024];
            int numRead = port.readBytes(buffer, buffer.length);
            if (numRead > 0) {
                String receivedData = new String(buffer, 0, numRead);
                processData(receivedData);
            }
        }
    }

    public void processData(String receivedData) {
    // Utilizar una expresión regular para extraer solo los números de los datos recibidos
    Matcher matcher = Pattern.compile("\\d+").matcher(receivedData);
    int sum = 0;
    while (matcher.find()) {
        int data = Integer.parseInt(matcher.group());
        System.out.println("Dato: " + data);
        variable += data;
    }
    
    System.out.println(variable);
}

    public int getVariable() {
        return variable;
    }
}
