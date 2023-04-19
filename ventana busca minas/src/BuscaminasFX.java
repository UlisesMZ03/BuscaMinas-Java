
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
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
import javafx.util.Duration;

public class BuscaminasFX extends Application {

    private final int nFilas = 8;
    private final int nColumnas = 8;
    private Label labelSegundos = new Label("0");
    private Label labelControl = new Label("0");
    Rectangle rect = new Rectangle(40, 40);
    Rectangle puntero = new Rectangle(40, 40);

    private Button[][] casillas = new Button[nFilas][nColumnas];
    private boolean[][] banderas = new boolean[nFilas][nColumnas];
    private Buscaminas buscaminas = new Buscaminas();
    private int turno = 1;
    boolean azul = false;
    private Pane pane = new Pane();
    private int filaPuntero = 0;
    private int columnaPuntero = 0;
    DropShadow shadow = new DropShadow(5, Color.BLACK);

    ArduinoReceiver arduinoReceiver;
    private int variableValue;

    private int contadorTurno = 0;
    private int segundos = 0;
    private Timer timer;

    Image casillaB = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/casilla.png");
    BackgroundImage backgroundCasilla = new BackgroundImage(casillaB, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(0, 0, true, true, false, false));

    Background backgroundC = new Background(backgroundCasilla);
    private int cantJug;

    public BuscaminasFX(int cantJug) {
        this.cantJug = cantJug;
    }

    @Override
    public void start(Stage primaryStage) {
        Thread arduinoThread = new Thread(() -> {
            arduinoReceiver = new ArduinoReceiver();

            while (true) {
                int variableValue = arduinoReceiver.getVariable();
                labelControl.setText("Valor de la variable: " + variableValue);
                // Actualizar el valor de la etiqueta en la interfaz de usuario
                Platform.runLater(() -> {
                    labelControl.setText("Valor de la variable: " + variableValue);
                });
            }
        });
        arduinoThread.setDaemon(true);
        arduinoThread.start();

        iniciarContador();
        // Pane pane = new Pane();
        GridPane root = new GridPane();
        pane.getChildren().add(root);
        pane.getChildren().add(puntero);
        puntero.setLayoutX(40);
        puntero.setLayoutY(63);
        puntero.setFill(null);
        puntero.setStrokeWidth(2);
        puntero.setStroke(Color.GREEN);
        puntero.setFill(null);
        puntero.setStrokeWidth(3);
        puntero.setStroke(Color.GREEN);
        root.setTranslateX(40);
        root.setTranslateY(63);
        labelSegundos.setLayoutX(67);
        labelSegundos.setLayoutY(10);
        labelControl.setLayoutX(77);
        labelControl.setLayoutY(20);
        pane.getChildren().contains(labelControl);

        Button bonus = new Button();
        bonus.setLayoutX(182);
        bonus.setLayoutY(11);
        bonus.setPrefSize(35, 35);
        //pane.getChildren().add(bonus);

        Image bonusRed = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/bonusRed.png");
        Image bonusGreen = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/bonusGreen.png");
        //BackgroundImage backgroundBandera = new BackgroundImage(bandera, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));

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

                        Node temp = buscaminas.listaSeg.head;
                        buscaminas.listaSeg.removeFNode();

                        buscaminas.descubrirCasilla(temp.j - 1, temp.i - 1);
                        mostrarTablero();
                        turno = 2;
                        contadorTurno -= 2;
                        if (contadorTurno >= 2) {
                            bonus.setBackground(backgroundG);
                        } else if (contadorTurno < 2) {
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
        Image imagenFondo = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/fondo.png");
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
                casilla.setOnMouseClicked(event -> {

                    int fila = GridPane.getRowIndex(casilla);
                    int columna = GridPane.getColumnIndex(casilla);
                    filaPuntero = fila;
                    columnaPuntero = columna;
                    actualizarPuntero(fila, columna);
                    if (cantJug == 1) {
                        if (turno == 1) {
                            if (event.getButton() == MouseButton.SECONDARY) {
                                if (banderas[fila][columna]) {
                                    banderas[fila][columna] = false;
                                } else {
                                    System.out.println("Bandera agregada" + columna + "," + fila);
                                    // Agregar la casilla al arreglo banderas
                                    banderas[fila][columna] = true;
                                }

                            }
                            if (event.getButton() == MouseButton.PRIMARY) {
                                {
                                    if (buscaminas.tableroVisible[fila][columna] == 8) {
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
                                        turno = 1;
                                        if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                                            System.out.println("Jugador gano");
                                        } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                                            System.out.println("Jugador perdio");
                                        }
                                    }
                                }
                            }
                        }
                    } else if (cantJug == 3) {

                        if (turno == 1) {
                            if (event.getButton() == MouseButton.SECONDARY) {
                                if (banderas[fila][columna]) {
                                    banderas[fila][columna] = false;
                                } else {
                                    System.out.println("Bandera agregada" + columna + "," + fila);
                                    // Agregar la casilla al arreglo banderas
                                    banderas[fila][columna] = true;
                                }

                            }
                            if (event.getButton() == MouseButton.PRIMARY) {

                                {
                                    if (buscaminas.tableroVisible[fila][columna] == 8) {
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
                    } else if (cantJug == 2) {

                        if (turno == 1) {
                            if (event.getButton() == MouseButton.SECONDARY) {

                                if (banderas[fila][columna]) {
                                    banderas[fila][columna] = false;
                                } else {
                                    System.out.println("Bandera agregada" + columna + "," + fila);
                                    // Agregar la casilla al arreglo banderas
                                    banderas[fila][columna] = true;
                                }

                            }
                            if (event.getButton() == MouseButton.PRIMARY) {
                                buscaminas.listaInc.clear();
                                {
                                    if (buscaminas.tableroVisible[fila][columna] == 8) {
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
                                }
                            }
                        } else {

                            Node temp = buscaminas.listaInc.removeNode();

                            buscaminas.descubrirCasilla(temp.j - 1, temp.i - 1);

                            if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                                System.out.println("Bot gano");
                            } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                                System.out.println("Bot perdio");
                            }
                            turno = 1;
                        }

                    }

                    mostrarTablero();

                    labelControl.setText(String.valueOf(variableValue));
                    buscaminas.mostrarTablero();
                    //System.out.println(buscaminas.casillasVacias(fila,columna));
                    buscaminas.casillaMina(fila, columna);
                    buscaminas.casillaSeg(fila, columna);
                    buscaminas.agregarListInc(fila, columna);
                    //buscaminas.casillaPorcentaje(fila, columna);

                    buscaminas.casillaSeg2(fila, columna);
                    //buscaminas.listaProb.clear();
                    //buscaminas.agregarListInc(fila, columna);
                    //buscaminas.casillaNSeg(fila, columna);

                    //System.out.println("Minasssss:   "+buscaminas.nMinas(fila, columna));
                    if (buscaminas.juegoTerminado) {
                        mostrarMensajeFinal();
                    }
                });
                casilla.setOnKeyPressed(event -> {
                    int fila = filaPuntero;
                    int columna = columnaPuntero;

                    if (cantJug == 1) {
                        if (turno == 1) {
                            if (event.getCode() == KeyCode.K) {
                                if (banderas[fila][columna]) {
                                    banderas[fila][columna] = false;
                                } else {
                                    System.out.println("Bandera agregada" + columna + "," + fila);
                                    // Agregar la casilla al arreglo banderas
                                    banderas[fila][columna] = true;
                                }

                            }

                            if (event.getCode() == KeyCode.UP) {
                                // Lógica para mover hacia arriba
                                actualizarPuntero(fila, columna);
                                filaPuntero--;
                                actualizarPuntero(filaPuntero, columna);

                            }
                            if (event.getCode() == KeyCode.DOWN) {
                                // Lógica para mover hacia abajo
                                actualizarPuntero(fila, columna);
                                filaPuntero++;
                                actualizarPuntero(filaPuntero, columna);

                            }
                            if (event.getCode() == KeyCode.LEFT) {
                                actualizarPuntero(fila, columna);
                                columnaPuntero--;
                                actualizarPuntero(fila, columnaPuntero);

                            }
                            if (event.getCode() == KeyCode.RIGHT) {
                                actualizarPuntero(fila, columna);
                                columnaPuntero++;
                                actualizarPuntero(fila, columnaPuntero);

                            }
                            if (event.getCode() == KeyCode.SPACE) {
                                {
                                    {
                                        if (buscaminas.tableroVisible[fila][columna] == 8) {
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
                                            turno = 1;
                                            if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                                                System.out.println("Jugador gano");
                                            } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                                                System.out.println("Jugador perdio");
                                            }

                                        }
                                    }
                                }
                            }
                        }

                    } else if (cantJug == 3) {

                        if (turno == 1) {
                            if (event.getCode() == KeyCode.K) {
                                if (banderas[fila][columna]) {
                                    banderas[fila][columna] = false;
                                } else {
                                    System.out.println("Bandera agregada" + columna + "," + fila);
                                    // Agregar la casilla al arreglo banderas
                                    banderas[fila][columna] = true;
                                }

                            }
                            if (event.getCode() == KeyCode.UP) {
                                // Lógica para mover hacia arriba
                                actualizarPuntero(fila, columna);
                                filaPuntero--;
                                actualizarPuntero(filaPuntero, columna);

                            }
                            if (event.getCode() == KeyCode.DOWN) {
                                // Lógica para mover hacia abajo
                                actualizarPuntero(fila, columna);
                                filaPuntero++;
                                actualizarPuntero(filaPuntero, columna);

                            }
                            if (event.getCode() == KeyCode.LEFT) {
                                actualizarPuntero(fila, columna);
                                columnaPuntero--;
                                actualizarPuntero(fila, columnaPuntero);

                            }
                            if (event.getCode() == KeyCode.RIGHT) {
                                actualizarPuntero(fila, columna);
                                columnaPuntero++;
                                actualizarPuntero(fila, columnaPuntero);

                            }
                            if (event.getCode() == KeyCode.SPACE) {

                                if (buscaminas.tableroVisible[fila][columna] == 8) {
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
                            }
                        } else {
                            if (event.getCode() == KeyCode.B) {
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
                        }

                    } else if (cantJug == 2) {

                        if (turno == 1) {
                            if (event.getCode() == KeyCode.K) {

                                if (banderas[fila][columna]) {
                                    banderas[fila][columna] = false;
                                } else {
                                    System.out.println("Bandera agregada" + columna + "," + fila);
                                    // Agregar la casilla al arreglo banderas
                                    banderas[fila][columna] = true;
                                }

                            }
                            if (event.getCode() == KeyCode.UP) {
                                // Lógica para mover hacia arriba
                                actualizarPuntero(fila, columna);
                                filaPuntero--;
                                actualizarPuntero(filaPuntero, columna);

                            }
                            if (event.getCode() == KeyCode.DOWN) {
                                // Lógica para mover hacia abajo
                                actualizarPuntero(fila, columna);
                                filaPuntero++;
                                actualizarPuntero(filaPuntero, columna);

                            }
                            if (event.getCode() == KeyCode.LEFT) {
                                actualizarPuntero(fila, columna);
                                columnaPuntero--;
                                actualizarPuntero(fila, columnaPuntero);

                            }
                            if (event.getCode() == KeyCode.RIGHT) {
                                actualizarPuntero(fila, columna);
                                columnaPuntero++;
                                actualizarPuntero(fila, columnaPuntero);

                            }

                            if (event.getCode() == KeyCode.SPACE) {

                                System.out.println("Fila" + fila + "columna" + columna);
                                buscaminas.listaInc.clear();

                                if (buscaminas.tableroVisible[fila][columna] == 8) {
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
                            }

                        } else {
                            if (event.getCode() == KeyCode.B) {
                                Node temp = buscaminas.listaInc.removeNode();

                                buscaminas.descubrirCasilla(temp.j - 1, temp.i - 1);

                                if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                                    System.out.println("Bot gano");
                                } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                                    System.out.println("Bot perdio");
                                }
                                turno = 1;
                            }
                        }

                    }

                    mostrarTablero();
                    labelControl.setText("Valor de la variable: " + variableValue);
                    buscaminas.mostrarTablero();
                    //System.out.println(buscaminas.casillasVacias(fila,columna));
                    buscaminas.casillaMina(fila, columna);
                    buscaminas.casillaSeg(fila, columna);
                    buscaminas.agregarListInc(fila, columna);
                    //buscaminas.casillaPorcentaje(fila, columna);
                    buscaminas.listaProb.clear();
                    buscaminas.casillaSeg2(fila, columna);
                    //buscaminas.listaProb.clear();
                    //buscaminas.agregarListInc(fila, columna);
                    //buscaminas.casillaNSeg(fila, columna);

                    //System.out.println("Minasssss:   "+buscaminas.nMinas(fila, columna));
                    if (buscaminas.juegoTerminado) {
                        mostrarMensajeFinal();
                    }
                }
                );

                casilla.setShape(rect);
                casilla.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
                //casilla.setEffect(new DropShadow());
                casilla.setEffect(shadow);
                casilla.setBackground(backgroundC);
                root.add(casilla, j, i);
                casillas[i][j] = casilla;

            }
        }

        Scene scene = new Scene(pane, 400, 400);

        primaryStage.setTitle(
                "Buscaminas");
        primaryStage.setScene(scene);

        primaryStage.show();

        buscaminas.colocarMinas();
    }

    public void actualizarPuntero(int fila, int columna) {
        puntero.setLayoutX((columna * 40) + 40);
        puntero.setLayoutY((fila * 40) + 63);
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
                    labelSegundos.setText(String.valueOf(variableValue));
                });

            }
        }, 0, 1000);
    }

    public void detenerContador() {
        timer.cancel();
    }

    private void mostrarTablero() {
        Image bandera = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/bandera.png");
        BackgroundImage backgroundBandera = new BackgroundImage(bandera, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(0, 0, true, true, false, false));

        Background backgroundB = new Background(backgroundBandera);

        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                Button casilla = casillas[i][j];

                if (!buscaminas.visible[i][j]) {
                    if (banderas[i][j]) {
                        casilla.setBackground(backgroundB);
                        //casilla.setStyle("-fx-background-color: 	Blue;-fx-border-color: black; -fx-border-width: 1px;");
                    } else {
                        casilla.setBackground(backgroundC);
                        casilla.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

                    }
                }
                if (buscaminas.visible[i][j]) {
                    if (buscaminas.tablero[i][j] == 0) {
                        //casilla.setEffect(new InnerShadow());

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
        detenerContador();
        Image casillaM = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/mina.png");
        BackgroundImage backgroundMina = new BackgroundImage(casillaM, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(0, 0, true, true, false, false));

        Background backgroundM = new Background(backgroundMina);
        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                Button casilla = casillas[i][j];
                casilla.setDisable(true);
                if (buscaminas.tablero[i][j] == 9) {
                    casilla.setBackground(backgroundM);
                } else {
                    casilla.setText(Integer.toString(buscaminas.tablero[i][j]));
                }
            }
        }
    }

}
