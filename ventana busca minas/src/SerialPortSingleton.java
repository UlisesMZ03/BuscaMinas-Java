import com.fazecast.jSerialComm.SerialPort;

public class SerialPortSingleton {

    private static SerialPort port;

    public static SerialPort getSerialPort(String portName) {
        if (port == null) {
            port = SerialPort.getCommPort(portName);
            port.openPort();
            port.setBaudRate(9600);
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 100, 0);
        }
        return port;
    }

}
