
import java.io.File;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

/**
 *
 * Clase GameMenuDemo que implementa la aplicación principal del juego
 * BuscaminasFX.
 *
 * @author Ulises Mendez
 */
public class GameMenuDemo extends Application {

    private BuscaminasFX buscaminas;
    private GameMenu gameMenu;

    private Stage primaryStage;
    private Image img = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/bg3.png");
    ImageView imgView = new ImageView(img);
    private int pag = 0;
    private File file = new File("/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/sounds/botonMenu.mp3");
    private Media media = new Media(file.toURI().toString());
    private MediaPlayer mediaPlayer;

    /**
     *
     * Método start que inicia la aplicación con la ventana principal.
     *
     * @param primaryStage el objeto Stage principal que se utilizará para
     * mostrar la aplicación.
     *
     * @throws Exception si ocurre algún error durante la ejecución del método.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        Pane root = new Pane();
        root.setPrefSize(400, 400);

        imgView.setFitWidth(400);
        imgView.setFitHeight(400);

        gameMenu = new GameMenu();
        gameMenu.setVisible(true);

        root.getChildren().addAll(imgView, gameMenu);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (!gameMenu.isVisible()) {
                    FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
                    ft.setFromValue(0);
                    ft.setToValue(1);

                    gameMenu.setVisible(true);
                    ft.play();
                } else {
                    FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
                    ft.setFromValue(1);
                    ft.setToValue(0);
                    ft.setOnFinished(evt -> gameMenu.setVisible(false));
                    ft.play();
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     *
     * La clase GameMenu representa un menú para un juego. Es una clase anidada
     * privada de otra clase.
     */
    private class GameMenu extends Parent {

        /**
         *
         * Crea una nueva instancia de la clase GameMenu.
         */
        public GameMenu() {

            VBox menu0 = new VBox(10);
            VBox menu1 = new VBox(10);
            VBox menu2 = new VBox(10);

            menu0.setTranslateX(50);
            menu0.setTranslateY(150);
            menu2.setTranslateX(50);
            menu2.setTranslateY(150);
            menu1.setTranslateX(50);
            menu1.setTranslateY(150);

            final int offset = 400;

            menu1.setTranslateX(offset);

            MenuButton btnTutorial = new MenuButton("TUTORIAL", 230, 30, 10);
            btnTutorial.setOnMouseClicked(event -> {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();

                img = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial1.1.png");
                imgView = new ImageView(img);
                getChildren().addAll(imgView);
                getChildren().add(menu2);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
                tt.setToX(menu0.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu2);
                tt1.setToX(menu0.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu0);
                });
            });

            MenuButton btnOptions = new MenuButton("PLAY", 230, 30, 10);
            btnOptions.setOnMouseClicked(event -> {

                getChildren().add(menu1);
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
                tt.setToX(menu0.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
                tt1.setToX(menu0.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu0);
                });
            });

            MenuButton btnExit = new MenuButton("EXIT", 230, 30, 10);
            btnExit.setOnMouseClicked(event -> {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                System.exit(0);
            });

            MenuButton btnBack = new MenuButton("BACK", 230, 30, 10);
            btnBack.setOnMouseClicked(event -> {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                getChildren().add(menu0);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
                tt.setToX(menu1.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
                tt1.setToX(menu1.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu1);
                });
            });
            MenuButton btnSingle = new MenuButton("Single Player", 230, 30, 10);
            MenuButton btnDummy = new MenuButton("Dummy AI", 230, 30, 10);
            MenuButton btnAdvance = new MenuButton("Advance AI", 230, 30, 10);
            btnDummy.setOnMouseClicked(event -> {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                if (buscaminas == null) {
                    buscaminas = new BuscaminasFX(2);
                }
                buscaminas.start(new Stage());
                primaryStage.close();
                gameMenu.setVisible(false);
            });
            btnSingle.setOnMouseClicked(event -> {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                if (buscaminas == null) {
                    buscaminas = new BuscaminasFX(1);
                }
                buscaminas.start(new Stage());
                primaryStage.close();
                gameMenu.setVisible(false);
            });
            btnAdvance.setOnMouseClicked(event -> {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                if (buscaminas == null) {
                    buscaminas = new BuscaminasFX(3);
                }
                buscaminas.start(new Stage());
                primaryStage.close();
                gameMenu.setVisible(false);
            });

            MenuButton btnBackTuto = new MenuButton("MENU", 55, 30, 2);

            btnBackTuto.setOnMouseClicked(event -> {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                pag = 0;
                img = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/bg3.png");
                imgView = new ImageView(img);
                imgView.setFitWidth(400);
                imgView.setFitHeight(400);

                getChildren().addAll(imgView, menu0);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
                tt.setToX(menu1.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
                tt1.setToX(menu2.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu2);
                });
            });

            MenuButton btnNextT = new MenuButton(" >", 30, 30, 5);

            MenuButton btnBackT = new MenuButton(" <", 30, 30, -5);
            btnBackTuto.setTranslateX(40);
            btnBackTuto.setTranslateY(215);
            btnNextT.setTranslateX(150);
            btnNextT.setTranslateY(215);
            btnBackT.setTranslateX(75);
            btnBackT.setTranslateY(215);
            HBox hbox = new HBox(10);
            hbox.getChildren().addAll(btnBackT, btnNextT, btnBackTuto);
            menu0.getChildren().addAll(btnOptions, btnTutorial, btnExit);
            menu1.getChildren().addAll(btnBack, btnSingle, btnDummy, btnAdvance);
            menu2.getChildren().addAll(hbox);
            Rectangle bg = new Rectangle(800, 600);
            bg.setFill(Color.GREY);
            bg.setOpacity(0.01);
            System.out.println("Hola");
            btnBackT.setOnMouseClicked(event -> {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                if (pag > 0) {
                    pag--;
                }

                getChildren().clear();
                System.out.println("Pagina: " + pag);
                if (pag == 0) {
                    getChildren().clear();
                    Image imga = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial1.1.png");
                    ImageView imgaView = new ImageView(imga);
                    getChildren().remove(menu2);
                    getChildren().addAll(imgaView, menu2);
                }
                if (pag == 1) {
                    getChildren().clear();
                    Image image1 = new Image("file:/C:/Usears/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial2.1.png");
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
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.6));
                    PauseTransition pause2 = new PauseTransition(Duration.seconds(0.6));
                    // Crea un objeto SequentialTransition para reproducir las transiciones en secuencia
                    SequentialTransition sequentialTransition = new SequentialTransition();
                    sequentialTransition.getChildren().addAll(pause, fadeIn, fadeOut, pause2);

                    // Reproduce la animación en bucle
                    sequentialTransition.setCycleCount(SequentialTransition.INDEFINITE);

                    // Crea un objeto StackPane y agrega los ImageView
                    StackPane root = new StackPane();
                    root.setAlignment(Pos.CENTER);

                    getChildren().remove(menu2);
                    getChildren().addAll(imageView1, imageView2, menu2);
                    sequentialTransition.play();
                }
                if (pag == 2) {
                    getChildren().clear();
                    Image image1 = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial3.1.png");
                    Image image2 = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial3.png");

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
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.6));
                    PauseTransition pause2 = new PauseTransition(Duration.seconds(0.6));
                    // Crea un objeto SequentialTransition para reproducir las transiciones en secuencia
                    SequentialTransition sequentialTransition = new SequentialTransition();
                    sequentialTransition.getChildren().addAll(pause, fadeIn, fadeOut, pause2);

                    // Reproduce la animación en bucle
                    sequentialTransition.setCycleCount(SequentialTransition.INDEFINITE);

                    // Crea un objeto StackPane y agrega los ImageView
                    StackPane root = new StackPane();
                    root.setAlignment(Pos.CENTER);

                    getChildren().remove(menu2);
                    getChildren().addAll(imageView1, imageView2, menu2);
                    sequentialTransition.play();
                }
                if (pag == 3) {
                    Image imga = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial4.png");
                    ImageView imgaView = new ImageView(imga);
                    getChildren().remove(menu2);
                    getChildren().addAll(imgaView, menu2);
                }
                if (pag == 4) {
                    Image imga = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial5.png");
                    ImageView imgaView = new ImageView(imga);
                    getChildren().remove(menu2);
                    getChildren().addAll(imgaView, menu2);
                }
                if (pag == 5) {
                    Image imga = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial6.png");
                    ImageView imgaView = new ImageView(imga);
                    getChildren().remove(menu2);
                    getChildren().addAll(imgaView, menu2);
                }

            });
            btnNextT.setOnMouseClicked(event -> {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                if (pag < 5) {
                    pag++;
                }

                System.out.println("Pagina: " + pag);
                if (pag == 0) {
                    img = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial1.1.png");
                    imgView = new ImageView(img);
                    getChildren().addAll(imgView);
                }
                if (pag == 1) {
                    Image image1 = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial2.1.png");
                    Image image2 = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial2.png");

                    // Crea dos objetos ImageView con las imágenes
                    ImageView imageView1 = new ImageView(image1);
                    ImageView imageView2 = new ImageView(image2);

                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), imageView1);
                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(1);

                    FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), imageView2);
                    fadeOut.setFromValue(1);
                    fadeOut.setToValue(0);

                    PauseTransition pause = new PauseTransition(Duration.seconds(0.6));
                    PauseTransition pause2 = new PauseTransition(Duration.seconds(0.6));
                    // Crea un objeto SequentialTransition para reproducir las transiciones en secuencia
                    SequentialTransition sequentialTransition = new SequentialTransition();
                    sequentialTransition.getChildren().addAll(pause, fadeIn, fadeOut, pause2);

                    sequentialTransition.setCycleCount(SequentialTransition.INDEFINITE);

                    StackPane root = new StackPane();
                    root.setAlignment(Pos.CENTER);

                    getChildren().remove(menu2);
                    getChildren().addAll(imageView1, imageView2, menu2);
                    sequentialTransition.play();
                }

                if (pag == 2) {

                    Image image1 = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial3.1.png");
                    Image image2 = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial3.png");

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
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.6));
                    PauseTransition pause2 = new PauseTransition(Duration.seconds(0.6));
                    // Crea un objeto SequentialTransition para reproducir las transiciones en secuencia
                    SequentialTransition sequentialTransition = new SequentialTransition();
                    sequentialTransition.getChildren().addAll(pause, fadeIn, fadeOut, pause2);

                    sequentialTransition.setCycleCount(SequentialTransition.INDEFINITE);

                    getChildren().remove(menu2);
                    getChildren().addAll(imageView1, imageView2, menu2);
                    sequentialTransition.play();
                }
                if (pag == 3) {
                    Image imga = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial4.png");
                    ImageView imgaView = new ImageView(imga);

                    getChildren().remove(menu2);
                    getChildren().addAll(imgaView, menu2);
                }
                if (pag == 4) {
                    Image imga = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial5.png");
                    ImageView imgaView = new ImageView(imga);

                    getChildren().remove(menu2);
                    getChildren().addAll(imgaView, menu2);
                }
                if (pag == 5) {
                    Image imga = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/tutorial6.png");
                    ImageView imgaView = new ImageView(imga);

                    getChildren().remove(menu2);

                    getChildren().addAll(imgaView, menu2);
                }
            });

            getChildren().addAll(bg, menu0);
        }
    }

    /**
     *
     * La clase MenuButton representa un botón de menú. Es una clase anidada
     * privada y estática de otra clase.
     *
     * @author Ulises Mendez
     */
    private static class MenuButton extends StackPane {

        private Text text;

        /**
         *
         * Crea una nueva instancia de la clase MenuButton con un nombre, una
         * posición x y y y una traslación dada.
         *
         * @param name el nombre del botón
         *
         * @param x la posición x del botón
         *
         * @param y la posición y del botón
         *
         * @param translate la traslación del botón
         */
        public MenuButton(String name, int x, int y, int translate) {
            Font pixelFont = Font.loadFont("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/fonts/digital-7.ttf", 30);
            text = new Text(name);
            text.setFont(pixelFont);
            text.setFill(Color.WHITE);

            Rectangle bg = new Rectangle(x, y);
            bg.setOpacity(0.6);
            bg.setFill(Color.BLACK);
            bg.setEffect(new GaussianBlur(2));

            setAlignment(Pos.CENTER_LEFT);
            setRotate(-0.5);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                bg.setTranslateX(translate);
                text.setTranslateX(translate);
                bg.setFill(Color.WHITE);
                text.setFill(Color.BLACK);
            });

            setOnMouseExited(event -> {
                bg.setTranslateX(0);
                text.setTranslateX(0);
                bg.setFill(Color.BLACK);
                text.setFill(Color.WHITE);
            });

            DropShadow drop = new DropShadow(50, Color.WHITE);
            drop.setInput(new Glow());

            setOnMousePressed(event -> setEffect(drop));
            setOnMouseReleased(event -> setEffect(null));
        }
    }

    /**
     *
     * El método main es el punto de entrada de la aplicación. Se encarga de
     * iniciar la aplicación JavaFX.
     *
     * @param args los argumentos de línea de comando (no se utilizan en este
     * caso)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
