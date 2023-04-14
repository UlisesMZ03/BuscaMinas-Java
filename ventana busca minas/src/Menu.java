import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Menu extends Application {
    private BuscaminasFX buscaminas;

    @Override
    public void start(Stage primaryStage) {
        Button btnJugar = new Button("Jugar");
        btnJugar.setOnAction(e -> {
            if (buscaminas == null) {
                buscaminas = new BuscaminasFX();
            }
            buscaminas.start(new Stage());
        });

        StackPane root = new StackPane(btnJugar);
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("BuscaminasFX - Menú");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
