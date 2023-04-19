public class OtherClass {
    public static void main(String[] args) {
        ArduinoReceiver arduinoReceiver = new ArduinoReceiver();
        int variableValue = arduinoReceiver.getVariable();
        System.out.println("Valor de la variable: " + variableValue);
    }
}
