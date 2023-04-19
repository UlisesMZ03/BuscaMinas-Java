public class Main {
  public static void main(String[] args) {
    ArduinoReceiver receiver = new ArduinoReceiver();
    
    // Crear y ejecutar un hilo que imprime el valor de la variable compartida
    Thread t1 = new Thread(() -> {
      while (true) {
        System.out.println(receiver.getVariable());
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    t1.start();
  }
}
