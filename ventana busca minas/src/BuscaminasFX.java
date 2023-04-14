
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import static javafx.scene.text.Font.font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class BuscaminasFX extends Application {

    private final int nFilas = 8;
    private final int nColumnas = 8;
    private Label labelSegundos = new Label("0");
    Rectangle rect = new Rectangle(40, 40);
    private Button[][] casillas = new Button[nFilas][nColumnas];
    private Buscaminas buscaminas = new Buscaminas();
    private int turno = 1;
    boolean azul = false;
    private Pane pane = new Pane();
    private int banderas = 0;

    Font font = Font.font("Arial", FontWeight.BOLD, 14);
    DropShadow shadow = new DropShadow(10, Color.BLACK);

    private int contadorTurno = 0;
    private int segundos = 0;
    private Timer timer;

    @Override
    public void start(Stage primaryStage) {
        iniciarContador();
        // Pane pane = new Pane();
        GridPane root = new GridPane();
        pane.getChildren().add(root);
        root.setTranslateX(40);
        root.setTranslateY(60);
        labelSegundos.setLayoutX(67);
        labelSegundos.setLayoutY(10);

        Button bonus = new Button();
        bonus.setLayoutX(182);
        bonus.setLayoutY(11);
        bonus.setPrefSize(35, 35);
        //pane.getChildren().add(bonus);

        Image bonusRed = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/bonusRed.png");
        Image bonusGreen = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/bonusGreen.png");

        BackgroundImage backgroundBonusR = new BackgroundImage(bonusRed, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
        BackgroundImage backgroundBonusG = new BackgroundImage(bonusGreen, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background backgroundR = new Background(backgroundBonusR);
        Background backgroundG = new Background(backgroundBonusG);
        bonus.setBackground(backgroundR);
        bonus.setEffect(shadow);

        pane.getChildren().add(bonus);

        bonus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (contadorTurno >= 2) {
                    
                    bonus.setEffect(shadow);
                }
                if (turno == 1 && contadorTurno >= 2) {
                    
                    System.out.println("Turno1");
                    if (buscaminas.listaSeg.getSize() > 0) {
                        System.out.println("Hola");
                        Node temp = buscaminas.listaSeg.head;
                        buscaminas.listaSeg.removeFNode();

                        buscaminas.descubrirCasilla(temp.j - 1, temp.i - 1);
                        mostrarTablero();
                        turno = 2;
                        contadorTurno -= 2;
                        if(contadorTurno>=2){
                            bonus.setBackground(backgroundG);
                        }
                        else if (contadorTurno<2){
                            bonus.setBackground(backgroundR);
                            
                        }
                        
                    } else {
                        bonus.setBackground(backgroundR);
                        System.out.println("Lista seg vacia");
                    }
                } else {

                    System.out.println("Sno");
                }

            }

        });
        Image imagenFondo = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/fondo.png");
        BackgroundImage fondo = new BackgroundImage(imagenFondo,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background fondoCompleto = new Background(fondo);

        pane.setBackground(fondoCompleto);

        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {

                Button casilla = new Button();
                casilla.setMinWidth(40);
                casilla.setMinHeight(40);

                // Agregar la variable booleana azul
// Agregar el controlador del evento MouseEvent.MOUSE_PRESSED
                casilla.setOnAction(event -> {

                    int fila = GridPane.getRowIndex(casilla);
                    int columna = GridPane.getColumnIndex(casilla);
                    if (turno == 1 ) {
                        if (buscaminas.tableroVisible[fila][columna]==8){
                            if (contadorTurno >= 2 && buscaminas.listaSeg.getSize() > 0) {
                            bonus.setBackground(backgroundG);
                        }
                        if (contadorTurno < 2) {
                            bonus.setBackground(backgroundR);
                        }
                        if (buscaminas.listaSeg.contains(columna + 1, fila + 1)) {
                            buscaminas.listaSeg.removeNode(columna + 1, fila + 1);

                        }
                        buscaminas.descubrirCasilla(fila, columna);
                        turno = 2;
                        if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                            System.out.println("Jugador gano");
                        } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                            System.out.println("Jugador perdio");
                        }
                        }
                        
                        
                    } else {
                        if (contadorTurno >= 0) {
                            contadorTurno++;
                            //System.out.println(contadorTurno);
                        }
                        if (contadorTurno >= 2 && buscaminas.listaSeg.getSize() > 0) {
                            bonus.setBackground(backgroundG);
                        }
                        if (contadorTurno < 2) {
                            bonus.setBackground(backgroundR);
                        }
                        

                        if (buscaminas.listaSeg.getSize() > 0) {
                            Node temp = buscaminas.listaSeg.head;
                            buscaminas.listaSeg.removeFNode();

                            buscaminas.descubrirCasilla(temp.j - 1, temp.i - 1);
                            if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                                System.out.println("Bot gano");
                            } else {
                                System.out.println("Bot perdio");
                            }
                        } else {
                            buscaminas.agregarListInc(fila, columna);
                            Node temp = buscaminas.listaInc.removeNode();

                            buscaminas.descubrirCasilla(temp.j - 1, temp.i - 1);
                            buscaminas.listaInc.clear();
                            if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                                System.out.println("Bot gano");
                            } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                                System.out.println("Bot perdio");
                            }
                        }

                        turno = 1;

                    }

                    mostrarTablero();
                    buscaminas.mostrarTablero();
                    //System.out.println(buscaminas.casillasVacias(fila,columna));
                    buscaminas.casillaMina(fila, columna);
                    buscaminas.casillaSeg(fila, columna);

                    //System.out.println("Minasssss:   "+buscaminas.nMinas(fila, columna));
                    if (buscaminas.juegoTerminado) {
                        mostrarMensajeFinal();
                    }
                });
                casilla.setShape(rect);
                casilla.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
                //casilla.setEffect(new DropShadow());
                casilla.setEffect(shadow);
                root.add(casilla, j, i);
                casillas[i][j] = casilla;

            }
        }

        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setTitle("Buscaminas");
        primaryStage.setScene(scene);
        primaryStage.show();
        buscaminas.colocarMinas();
    }

    public void iniciarContador() {
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                segundos++;

                Platform.runLater(() -> {
                    if (!pane.getChildren().contains(labelSegundos)) {
                        labelSegundos.setStyle("-fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 28;-fx-text-fill: red");
                        labelSegundos.setAlignment(Pos.CENTER);
                        labelSegundos.setPrefWidth(75);
                        labelSegundos.setPrefHeight(37);
                        pane.getChildren().add(labelSegundos);

                    }
                    labelSegundos.setText(String.valueOf(segundos));
                });

            }
        }, 0, 1000);
    }

    public void detenerContador() {
        timer.cancel();
    }

    private void mostrarTablero() {
        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                Button casilla = casillas[i][j];
                if (buscaminas.visible[i][j]) {
                    if (buscaminas.tablero[i][j] == 0) {
                        casilla.setEffect(new InnerShadow());

                        casilla.setStyle("-fx-background-color: 	DARKGRAY;-fx-border-color: black; -fx-border-width: 1px;");
                    } else if (buscaminas.tablero[i][j] == 9) {
                        casilla.setText("X");
                    } else {
                        casilla.setText(Integer.toString(buscaminas.tablero[i][j]));
                        if (buscaminas.tablero[i][j] > 0) {
                            if (buscaminas.tablero[i][j] == 1) {
                                casilla.setStyle("-fx-background-color: grey; -fx-text-fill: blue;-fx-border-color: black; -fx-border-width: 1px;");
                                buscaminas.tableroVisible[i][j] = 1;
                                casilla.setEffect(new InnerShadow());
                            }
                            if (buscaminas.tablero[i][j] == 2) {
                                casilla.setStyle("-fx-background-color: grey; -fx-text-fill: LIMEGREEN;-fx-border-color: black; -fx-border-width: 1px;");
                                buscaminas.tableroVisible[i][j] = 2;
                                casilla.setEffect(new InnerShadow());

                            }
                            if (buscaminas.tablero[i][j] == 3) {
                                casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                buscaminas.tableroVisible[i][j] = 3;
                                casilla.setEffect(new InnerShadow());
                            }
                            if (buscaminas.tablero[i][j] == 4) {
                                casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                buscaminas.tableroVisible[i][j] = 4;
                                casilla.setEffect(new InnerShadow());
                            }
                            if (buscaminas.tablero[i][j] == 5) {
                                casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                buscaminas.tableroVisible[i][j] = 5;
                                casilla.setEffect(new InnerShadow());
                            }
                            if (buscaminas.tablero[i][j] == 6) {
                                casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                buscaminas.tableroVisible[i][j] = 5;
                                casilla.setEffect(new InnerShadow());
                            }
                            if (buscaminas.tablero[i][j] == 7) {
                                casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                buscaminas.tableroVisible[i][j] = 7;
                                casilla.setEffect(new InnerShadow());
                            }
                            if (buscaminas.tablero[i][j] == 8) {
                                casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                buscaminas.tableroVisible[i][j] = 8;
                                casilla.setEffect(new InnerShadow());
                            }
                        }

                    }
                } else {
                    casilla.setText("");
                }
            }
        }
    }

    private void mostrarMensajeFinal() {
        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                Button casilla = casillas[i][j];
                casilla.setDisable(true);
                if (buscaminas.tablero[i][j] == 9) {
                    casilla.setText("X");
                } else {
                    casilla.setText(Integer.toString(buscaminas.tablero[i][j]));
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
