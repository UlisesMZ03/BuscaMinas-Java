
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BuscaminasF extends Application {

    private Label labelVariable1;
    private Label labelVariable2;

    @Override
    public void start(Stage primaryStage) {
        // Crear los Labels y agregarlos a la escena
        labelVariable1 = new Label();
        labelVariable2 = new Label();
        VBox root = new VBox();
        root.getChildren().addAll(labelVariable1, labelVariable2);
        Scene scene = new Scene(root, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Crear el hilo para actualizar las propiedades variable1Property y variable2Property de ArduinoReceiver
        ArduinoReceiver arduinoReceiver = new ArduinoReceiver();
        Button enviarSenalButton = new Button("Enviar señal");
        enviarSenalButton.setOnAction(event -> {
            arduinoReceiver.enviarSenal("1"); // Reemplaza esto con la señal que deseas enviar

        });
        root.getChildren().addAll(enviarSenalButton);
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    labelVariable1.setText("Variable 1: " + arduinoReceiver.variableProperty().get());
                    labelVariable2.setText("Variable 2: " + arduinoReceiver.variable2XProperty().get());
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
