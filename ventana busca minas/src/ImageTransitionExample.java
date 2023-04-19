import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ImageTransitionExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Carga las imágenes
        Image image1 = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial2.1.png");
        Image image2 = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial2.png");

        // Crea dos objetos ImageView con las imágenes
        ImageView imageView1 = new ImageView(image1);
        ImageView imageView2 = new ImageView(image2);

        // Ajusta el tamaño de los ImageView a la ventana
        

        // Crea dos objetos FadeTransition para desvanecer las imágenes dentro y fuera
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), imageView1);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), imageView2);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        // Crea un objeto PauseTransition para esperar 2 segundos antes de comenzar la animación
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
        // Crea un objeto SequentialTransition para reproducir las transiciones en secuencia
        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(pause, fadeIn, fadeOut,pause2);

        // Reproduce la animación en bucle
        sequentialTransition.setCycleCount(SequentialTransition.INDEFINITE);

        // Crea un objeto StackPane y agrega los ImageView
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(imageView1, imageView2);

        // Crea la escena y la muestra
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Comienza la animación
        sequentialTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
