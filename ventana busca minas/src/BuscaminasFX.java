
import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    private Label labelMinas = new Label("0");
    private Label labelControl = new Label("0");
    Rectangle rect = new Rectangle(40, 40);
    Rectangle puntero = new Rectangle(40, 40);
    //Media soundPop = new Media(new File("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/sounds/pop.mp3").toURI().toString());
    private Font pixelFont = Font.loadFont("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/fonts/digital-7.ttf", 30);
    private Font pixelFontF = Font.loadFont("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/fonts/digital-7.ttf", 40);
    private int resultadoF;
    private File file = new File("/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/sounds/pop.mp3");
    private Media media = new Media(file.toURI().toString());

    private File fileError = new File("/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/sounds/error.mp3");
    private Media mediaError = new Media(fileError.toURI().toString());
    private Font pixelFontC = Font.loadFont("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/fonts/digital-7.ttf", 18);
    private Button[][] casillas = new Button[nFilas][nColumnas];
    private boolean[][] banderas = new boolean[nFilas][nColumnas];
    private Buscaminas buscaminas = new Buscaminas();
    private int turno = 1;
    private int nBandera = 0;
    private Pane pane = new Pane();
    private int filaPuntero = 0;
    private int columnaPuntero = 0;
    private int selecArd = 0;
    DropShadow shadow = new DropShadow(5, Color.BLACK);
    private Scene SceneInicial;
    private int contadorTurno = 0;
    private int segundos = 0;
    private Timer timer;
    private MediaPlayer mediaPlayer;
    private Label labelVariable;
    ScheduledExecutorService executorService ;
    // KeyEvent keyPressEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.S, false, false, false, false);
    Image casillaB = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/casilla.png");
    BackgroundImage backgroundCasilla = new BackgroundImage(casillaB, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(0, 0, true, true, false, false));
    ArduinoReceiver arduinoReceiver;
    Background backgroundC = new Background(backgroundCasilla);
    private int cantJug;
    private boolean hiloTF = true;
    private Thread thread;

    public BuscaminasFX(int cantJug) {
        this.cantJug = cantJug;
        
    }

    @Override
    public void start(Stage primaryStage) {
        labelMinas.setText(String.valueOf(buscaminas.nMinas));
        labelVariable = new Label();
        pane.getChildren().add(labelVariable);

        // Escuchar los cambios de la propiedad variableProperty
        labelControl = new Label();

        thread = new Thread(() -> {
            arduinoReceiver = new ArduinoReceiver();
            while (hiloTF) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    actualizarPuntero(arduinoReceiver.variableProperty().get(), arduinoReceiver.variable2XProperty().get());
                    filaPuntero = arduinoReceiver.variableProperty().get();

                    columnaPuntero = arduinoReceiver.variable2XProperty().get();
                    selecArd = arduinoReceiver.variableSProperty().get();

                });
            }
            arduinoReceiver.port.closePort();

            System.out.println("HILO CERRADOOOooooooooooo");
        });
        thread.setDaemon(true);
        thread.start();

        iniciarContador();
        // Pane pane = new Pane();
        GridPane root = new GridPane();
        pane.getChildren().add(root);
        pane.getChildren().add(puntero);
        pane.getChildren().add(labelControl);
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
        labelMinas.setFont(pixelFont);
        labelMinas.setStyle("-fx-text-fill: red");
        labelMinas.setAlignment(Pos.CENTER_RIGHT);
        labelMinas.setPrefWidth(75);
        labelMinas.setPrefHeight(37);
        pane.getChildren().add(labelMinas);
        labelMinas.setLayoutX(263);
        labelMinas.setLayoutY(9);
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
        Image imagenFondo = new Image("file:/C:/Users/ulise/Desktop/TEC/Algoritmos y estructura de datos I/BuscaMinas/ventana busca minas/src/images/fondo3.png");
        BackgroundImage fondo = new BackgroundImage(imagenFondo,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background fondoCompleto = new Background(fondo);

        pane.setBackground(fondoCompleto);

        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                try {
                    Robot robot = new Robot();
                    executorService = Executors.newSingleThreadScheduledExecutor();
                    executorService.scheduleAtFixedRate(() -> {
                        robot.keyPress(KeyEvent.VK_S);
                    }, 0, 100, TimeUnit.MILLISECONDS); // Presionar cada 100ms
                } catch (AWTException e) {
                    e.printStackTrace();
                }
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
                    arduinoReceiver.setVariableY(fila);
                    arduinoReceiver.setVariableX(columna);
                    actualizarPuntero(fila, columna);
                    if (cantJug == 1) {
                        if (turno == 1) {
                            if (event.getButton() == MouseButton.SECONDARY) {
                                if (banderas[fila][columna]) {
                                    banderas[fila][columna] = false;
                                    arduinoReceiver.enviarSenal("1");

                                } else {
                                    System.out.println("Bandera agregada" + columna + "," + fila);
                                    // Agregar la casilla al arreglo banderas
                                    banderas[fila][columna] = true;
                                    arduinoReceiver.enviarSenal("1");
                                }

                                // Reemplaza esto con la señal que deseas enviar
                            }
                            if (event.getButton() == MouseButton.PRIMARY) {

                                if (buscaminas.tableroVisible[fila][columna] == 8) {
                                    if (mediaPlayer != null) {
                                        mediaPlayer.stop();
                                    }
                                    mediaPlayer = new MediaPlayer(media);
                                    mediaPlayer.play();

                                    if (contadorTurno >= 2 && buscaminas.listaSeg.getSize() > 0) {
                                        bonus.setBackground(backgroundG);
                                    }
                                    if (contadorTurno < 2) {
                                        bonus.setBackground(backgroundR);
                                    }
                                    if (buscaminas.listaSeg.contains(columna + 1, fila + 1)) {
                                        buscaminas.listaSeg.removeNode(columna + 1, fila + 1);

                                    }
                                    arduinoReceiver.enviarSenal("2");
                                    buscaminas.descubrirCasilla(fila, columna);
                                    turno = 1;
                                    if (buscaminas.juegoTerminado && buscaminas.haGanado()) {
                                        resultadoF = 1;
                                        System.out.println("Jugador gano");
                                    } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                                        resultadoF = 0;
                                        System.out.println("Jugador perdio");
                                    }
                                } else if (buscaminas.tableroVisible[fila][columna] != 8 && buscaminas.tableroVisible[fila][columna] != 0) {
                                    if (mediaPlayer != null) {
                                        mediaPlayer.stop();
                                    }
                                    mediaPlayer = new MediaPlayer(mediaError);
                                    mediaPlayer.play();
                                }

                            }
                        }
                    } else if (cantJug == 3) {

                        if (turno == 1) {
                            if (event.getButton() == MouseButton.SECONDARY) {
                                if (banderas[fila][columna]) {
                                    banderas[fila][columna] = false;
                                    arduinoReceiver.enviarSenal("1");
                                } else {
                                    System.out.println("Bandera agregada" + columna + "," + fila);
                                    // Agregar la casilla al arreglo banderas
                                    banderas[fila][columna] = true;
                                    arduinoReceiver.enviarSenal("1");
                                }

                            }
                            if (event.getButton() == MouseButton.PRIMARY) {

                                if (buscaminas.tableroVisible[fila][columna] == 8) {
                                    if (mediaPlayer != null) {
                                        mediaPlayer.stop();
                                    }
                                    mediaPlayer = new MediaPlayer(media);
                                    mediaPlayer.play();
                                    if (contadorTurno >= 2 && buscaminas.listaSeg.getSize() > 0) {
                                        bonus.setBackground(backgroundG);
                                    }
                                    if (contadorTurno < 2) {
                                        bonus.setBackground(backgroundR);
                                    }
                                    if (buscaminas.listaSeg.contains(columna + 1, fila + 1)) {
                                        buscaminas.listaSeg.removeNode(columna + 1, fila + 1);

                                    }
                                    arduinoReceiver.enviarSenal("2");
                                    buscaminas.descubrirCasilla(fila, columna);
                                    turno = 2;
                                    if (buscaminas.juegoTerminado && buscaminas.haGanado()) {
                                        resultadoF = 1;
                                        System.out.println("Jugador gano");
                                    } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                                        System.out.println("Jugador perdio");
                                    }
                                } else if (buscaminas.tableroVisible[fila][columna] != 8 && buscaminas.tableroVisible[fila][columna] != 0) {
                                    if (mediaPlayer != null) {
                                        mediaPlayer.stop();
                                    }
                                    mediaPlayer = new MediaPlayer(mediaError);
                                    mediaPlayer.play();
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
                                    resultadoF = 0;
                                } else {
                                    System.out.println("Bot perdio");
                                    resultadoF = 1;
                                }
                            } else {

                                Node temp = buscaminas.listaInc.removeNode();
                                arduinoReceiver.enviarSenal("3");
                                buscaminas.descubrirCasilla(temp.j - 1, temp.i - 1);
                                buscaminas.listaInc.clear();
                                if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                                    System.out.println("Bot gano");
                                    resultadoF = 0;
                                } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                                    System.out.println("Bot perdio");
                                    resultadoF = 1;
                                }
                            }

                            turno = 1;

                        }
                    } else if (cantJug == 2) {

                        if (turno == 1) {
                            if (event.getButton() == MouseButton.SECONDARY) {

                                if (banderas[fila][columna]) {
                                    banderas[fila][columna] = false;
                                    arduinoReceiver.enviarSenal("1");
                                } else {
                                    System.out.println("Bandera agregada" + columna + "," + fila);
                                    // Agregar la casilla al arreglo banderas
                                    banderas[fila][columna] = true;
                                    arduinoReceiver.enviarSenal("1");
                                }

                            }
                            if (event.getButton() == MouseButton.PRIMARY) {

                                buscaminas.listaInc.clear();

                                if (buscaminas.tableroVisible[fila][columna] == 8) {
                                    if (mediaPlayer != null) {
                                        mediaPlayer.stop();
                                    }
                                    mediaPlayer = new MediaPlayer(media);
                                    mediaPlayer.play();
                                    if (contadorTurno >= 2 && buscaminas.listaSeg.getSize() > 0) {
                                        bonus.setBackground(backgroundG);
                                    }
                                    if (contadorTurno < 2) {
                                        bonus.setBackground(backgroundR);
                                    }
                                    if (buscaminas.listaSeg.contains(columna + 1, fila + 1)) {
                                        buscaminas.listaSeg.removeNode(columna + 1, fila + 1);

                                    }
                                    arduinoReceiver.enviarSenal("2");
                                    buscaminas.descubrirCasilla(fila, columna);
                                    turno = 2;

                                    if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                                        System.out.println("Jugador ganooooo");
                                        resultadoF = 1;

                                    } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                                        System.out.println("Jugador perdiooooo");
                                        resultadoF = 0;
                                    }

                                } else if (buscaminas.tableroVisible[fila][columna] != 8 && buscaminas.tableroVisible[fila][columna] != 0) {
                                    if (mediaPlayer != null) {
                                        mediaPlayer.stop();
                                    }
                                    mediaPlayer = new MediaPlayer(mediaError);
                                    mediaPlayer.play();
                                }
                            }
                        } else {

                            Node temp = buscaminas.listaInc.removeNode();
                            arduinoReceiver.enviarSenal("3");
                            buscaminas.descubrirCasilla(temp.j - 1, temp.i - 1);

                            if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                                System.out.println("Bot ganoooo");
                                resultadoF = 0;
                            } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                                System.out.println("Bot perdioooo");
                                resultadoF = 1;
                            }
                            turno = 1;
                        }

                    }

                    mostrarTablero();
                    contadorBanderas();
                    // variableValue = arduinoReceiver.getVariable();

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
                            if (event.getCode() == KeyCode.K || selecArd==2) {
                                arduinoReceiver.setVariableSelec(0);
                                if (banderas[fila][columna]) {
                                    banderas[fila][columna] = false;
                                } else {
                                    System.out.println("Bandera agregada" + columna + "," + fila);
                                    arduinoReceiver.enviarSenal("1");
                                    // Agregar la casilla al arreglo banderas
                                    banderas[fila][columna] = true;
                                }

                            }

                            if (event.getCode() == KeyCode.UP) {
                                // Lógica para mover hacia arriba
                                actualizarPuntero(fila, columna);
                                arduinoReceiver.setVariableY(arduinoReceiver.variableProperty().get() - 1);
                                filaPuntero--;
                                actualizarPuntero(filaPuntero, columna);

                            }
                            if (event.getCode() == KeyCode.DOWN) {
                                // Lógica para mover hacia abajo
                                actualizarPuntero(fila, columna);
                                filaPuntero++;
                                arduinoReceiver.setVariableY(arduinoReceiver.variableProperty().get() + 1);
                                actualizarPuntero(filaPuntero, columna);

                            }
                            if (event.getCode() == KeyCode.LEFT) {
                                arduinoReceiver.setVariableX(arduinoReceiver.variable2XProperty().get() - 1);
                                actualizarPuntero(fila, columna);
                                columnaPuntero--;
                                actualizarPuntero(fila, columnaPuntero);

                            }
                            if (event.getCode() == KeyCode.RIGHT) {
                                arduinoReceiver.setVariableX(arduinoReceiver.variable2XProperty().get() + 1);
                                actualizarPuntero(fila, columna);
                                columnaPuntero++;
                                actualizarPuntero(fila, columnaPuntero);

                            }
                            if (event.getCode() == KeyCode.SPACE || selecArd == 1) {
                                arduinoReceiver.setVariableSelec(0);
                                
                                    
                                        if (buscaminas.tableroVisible[fila][columna] == 8) {
                                            if (mediaPlayer != null) {
                                                mediaPlayer.stop();
                                            }
                                            mediaPlayer = new MediaPlayer(media);
                                            mediaPlayer.play();
                                            if (contadorTurno >= 2 && buscaminas.listaSeg.getSize() > 0) {
                                                bonus.setBackground(backgroundG);
                                            }
                                            if (contadorTurno < 2) {
                                                bonus.setBackground(backgroundR);
                                            }
                                            if (buscaminas.listaSeg.contains(columna + 1, fila + 1)) {
                                                buscaminas.listaSeg.removeNode(columna + 1, fila + 1);

                                            }
                                            arduinoReceiver.enviarSenal("2");
                                            buscaminas.descubrirCasilla(fila, columna);
                                            turno = 1;
                                            if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                                                System.out.println("Jugador ganoooo");
                                                resultadoF = 1;
                                            } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                                                System.out.println("Jugador perdioooo");
                                                resultadoF = 0;
                                            }

                                        } else if (buscaminas.tableroVisible[fila][columna] != 8 && buscaminas.tableroVisible[fila][columna] != 0) {
                                            if (mediaPlayer != null) {
                                                mediaPlayer.stop();
                                            }
                                            mediaPlayer = new MediaPlayer(mediaError);
                                            mediaPlayer.play();
                                        }
                                    
                                
                            }
                        }

                    } else if (cantJug == 3) {

                        
                        if (turno == 1) {
                            if (event.getCode() == KeyCode.K || selecArd==2) {
                                arduinoReceiver.setVariableSelec(0);
                                if (banderas[fila][columna]) {
                                    banderas[fila][columna] = false;
                                } else {
                                    System.out.println("Bandera agregada" + columna + "," + fila);
                                    arduinoReceiver.enviarSenal("1");
                                    // Agregar la casilla al arreglo banderas
                                    banderas[fila][columna] = true;
                                }

                            }
                            if (event.getCode() == KeyCode.UP) {
                                // Lógica para mover hacia arriba
                                actualizarPuntero(fila, columna);
                                arduinoReceiver.setVariableY(arduinoReceiver.variableProperty().get() - 1);
                                filaPuntero--;
                                actualizarPuntero(filaPuntero, columna);

                            }
                            if (event.getCode() == KeyCode.DOWN) {
                                // Lógica para mover hacia abajo
                                actualizarPuntero(fila, columna);
                                arduinoReceiver.setVariableY(arduinoReceiver.variableProperty().get() + 1);
                                filaPuntero++;
                                actualizarPuntero(filaPuntero, columna);

                            }
                            if (event.getCode() == KeyCode.LEFT) {
                                arduinoReceiver.setVariableX(arduinoReceiver.variable2XProperty().get() - 1);
                                actualizarPuntero(fila, columna);
                                columnaPuntero--;
                                actualizarPuntero(fila, columnaPuntero);

                            }
                            if (event.getCode() == KeyCode.RIGHT) {
                                arduinoReceiver.setVariableX(arduinoReceiver.variable2XProperty().get() + 1);
                                actualizarPuntero(fila, columna);
                                columnaPuntero++;
                                actualizarPuntero(fila, columnaPuntero);

                            }
                            if (event.getCode() == KeyCode.SPACE || selecArd == 1) {
                                
                                arduinoReceiver.setVariableSelec(0);
                                if (mediaPlayer != null) {
                                    mediaPlayer.stop();
                                }
                                mediaPlayer = new MediaPlayer(media);
                                mediaPlayer.play();

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
                                    arduinoReceiver.enviarSenal("2");
                                    buscaminas.descubrirCasilla(fila, columna);
                                    turno = 2;
                                    if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                                        System.out.println("Jugador ganoooo");
                                        resultadoF = 1;
                                    } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                                        System.out.println("Jugador perdioooo");
                                        resultadoF = 0;
                                    }
                                } else if (buscaminas.tableroVisible[fila][columna] != 8 && buscaminas.tableroVisible[fila][columna] != 0) {
                                    if (mediaPlayer != null) {
                                        mediaPlayer.stop();
                                    }
                                    mediaPlayer = new MediaPlayer(mediaError);
                                    mediaPlayer.play();
                                }
                            }
                        } else {
                            if (event.getCode() == KeyCode.B || selecArd==2 ) {
                                arduinoReceiver.setVariableSelec(0);
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
                                    arduinoReceiver.enviarSenal("3");
                                    buscaminas.descubrirCasilla(temp.j - 1, temp.i - 1);
                                    if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                                        System.out.println("Bot ganoooo");
                                        resultadoF = 0;
                                    } else {
                                        System.out.println("Bot perdioooo");
                                        resultadoF = 1;
                                    }
                                } else {

                                    Node temp = buscaminas.listaInc.removeNode();

                                    buscaminas.descubrirCasilla(temp.j - 1, temp.i - 1);
                                    buscaminas.listaInc.clear();
                                    if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                                        System.out.println("Bot ganooo");
                                        resultadoF = 0;
                                    } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                                        System.out.println("Bot perdioooo");
                                        resultadoF = 1;
                                    }
                                }

                                turno = 1;

                            }
                        }

                    } else if (cantJug == 2) {

                        if (turno == 1) {
                            if (event.getCode() == KeyCode.K || selecArd==2) {
                                arduinoReceiver.setVariableSelec(0);

                                if (banderas[fila][columna]) {
                                    banderas[fila][columna] = false;
                                } else {
                                    System.out.println("Bandera agregada" + columna + "," + fila);
                                    arduinoReceiver.enviarSenal("1");
                                    // Agregar la casilla al arreglo banderas
                                    banderas[fila][columna] = true;
                                }

                            }
                            if (event.getCode() == KeyCode.UP) {
                                arduinoReceiver.setVariableY(arduinoReceiver.variableProperty().get() - 1);
                                // Lógica para mover hacia arriba
                                actualizarPuntero(fila, columna);
                                filaPuntero--;
                                actualizarPuntero(filaPuntero, columna);

                            }
                            if (event.getCode() == KeyCode.DOWN) {
                                arduinoReceiver.setVariableY(arduinoReceiver.variableProperty().get() + 1);
                                // Lógica para mover hacia abajo
                                actualizarPuntero(fila, columna);
                                filaPuntero++;
                                actualizarPuntero(filaPuntero, columna);

                            }
                            if (event.getCode() == KeyCode.LEFT) {
                                arduinoReceiver.setVariableX(arduinoReceiver.variable2XProperty().get() - 1);
                                actualizarPuntero(fila, columna);
                                columnaPuntero--;
                                actualizarPuntero(fila, columnaPuntero);

                            }
                            if (event.getCode() == KeyCode.RIGHT) {
                                arduinoReceiver.setVariableX(arduinoReceiver.variable2XProperty().get() + 1);
                                actualizarPuntero(fila, columna);
                                columnaPuntero++;
                                actualizarPuntero(fila, columnaPuntero);

                            }

                            if (event.getCode() == KeyCode.SPACE || selecArd == 1) {
                                arduinoReceiver.setVariableSelec(0);
                                if (mediaPlayer != null) {
                                    mediaPlayer.stop();
                                }
                                mediaPlayer = new MediaPlayer(media);
                                mediaPlayer.play();

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
                                    arduinoReceiver.enviarSenal("2");
                                    buscaminas.descubrirCasilla(fila, columna);

                                    turno = 2;
                                    if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                                        System.out.println("Jugador ganooooooo");
                                        labelMinas.setText("Ganaste");
                                        resultadoF = 1;
                                    } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                                        labelMinas.setText("Perdiste");
                                        resultadoF = 0;
                                        System.out.println("Jugador perdioooo");
                                    }
                                } else if (buscaminas.tableroVisible[fila][columna] != 8 && buscaminas.tableroVisible[fila][columna] != 0) {
                                    if (mediaPlayer != null) {
                                        mediaPlayer.stop();
                                    }
                                    mediaPlayer = new MediaPlayer(mediaError);
                                    mediaPlayer.play();
                                }
                            }

                        } else {
                            if (event.getCode() == KeyCode.B || selecArd==2) {
                                arduinoReceiver.setVariableSelec(0);
                                Node temp = buscaminas.listaInc.removeNode();
                                arduinoReceiver.enviarSenal("2");
                                buscaminas.descubrirCasilla(temp.j - 1, temp.i - 1);

                                if (buscaminas.juegoTerminado && buscaminas.haGanado()) {

                                    System.out.println("Bot ganoooooo");
                                    resultadoF = 0;
                                    labelMinas.setText("Perdiste");
                                } else if (buscaminas.juegoTerminado && !buscaminas.haGanado()) {
                                    System.out.println("Bot perdiooooooo");
                                    resultadoF = 1;
                                    labelMinas.setText("Ganaste");
                                }
                                turno = 1;
                            }
                        }

                    }

                    mostrarTablero();
                    contadorBanderas();
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
        SceneInicial = scene;
        primaryStage.setTitle(
                "Buscaminas");
        primaryStage.setScene(scene);

        primaryStage.show();

        buscaminas.colocarMinas();
        primaryStage.setOnCloseRequest(e -> {
            stop();
            Platform.exit();
            System.exit(0);
        });
    }
public void stop() {
    hiloTF = false;
    executorService.shutdown();
    
}

    public void actualizarPuntero(int fila, int columna) {
        puntero.setLayoutX((columna * 40) + 40);
        puntero.setLayoutY((fila * 40) + 63);
    }

    public void contadorBanderas() {
        nBandera = 0;
        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                if (banderas[i][j]) {
                    nBandera += 1;
                }

            }

        }

        labelMinas.setText(String.valueOf(buscaminas.nMinas - nBandera));

    }

    public void iniciarContador() {
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                segundos++;

                Platform.runLater(() -> {
                    if (!pane.getChildren().contains(labelSegundos)) {

                        labelSegundos.setFont(pixelFont);
                        labelSegundos.setStyle("-fx-text-fill: red");
                        labelSegundos.setAlignment(Pos.CENTER_RIGHT);
                        labelSegundos.setPrefWidth(74);
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
                    if (banderas[i][j]) {
                        banderas[i][j] = false;

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
                                casilla.setFont(pixelFontC);
                                casilla.setStyle("-fx-background-color: grey; -fx-text-fill: blue;-fx-border-color: black; -fx-border-width: 1px;");
                                buscaminas.tableroVisible[i][j] = 1;
                                casilla.setEffect(new InnerShadow());
                            }
                            if (buscaminas.tablero[i][j] == 2) {
                                casilla.setFont(pixelFontC);
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
        Label mensajeFinal = new Label("0");
        if (resultadoF == 1) {
            mensajeFinal.setText("GANASTE");
            mensajeFinal.setStyle("-fx-font-weight: bold;-fx-text-fill: LIME;");
        } else if (resultadoF == 0) {
            mensajeFinal.setText("Perdiste");
            mensajeFinal.setStyle("-fx-font-weight: bold;-fx-text-fill: red;");
        }

        mensajeFinal.setFont(pixelFontF);

        Rectangle rectanguloFondo = new Rectangle(400, 50, Color.BLACK);
        Rectangle rectanguloMenu = new Rectangle(50, 50, Color.BLACK);
        rectanguloMenu.setOpacity(0.6);
        rectanguloMenu.setFill(Color.BLACK);
        rectanguloMenu.setEffect(new GaussianBlur(2));
        mensajeFinal.setLayoutX(135);
        mensajeFinal.setLayoutY(140);

        rectanguloFondo.setLayoutY(137);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), new KeyValue(rectanguloFondo.opacityProperty(), 0.65));
        KeyFrame keyFrameText = new KeyFrame(Duration.seconds(1), new KeyValue(mensajeFinal.opacityProperty(), 1));
        timeline.getKeyFrames().addAll(keyFrame, keyFrameText);
        rectanguloFondo.setOpacity(0);
        mensajeFinal.setOpacity(0);
        Button btnOpenMenu = new Button("☰");
        Button btnReiniciar = new Button("🔁");
        btnOpenMenu.setLayoutX(140);
        btnOpenMenu.setLayoutY(200);
        btnReiniciar.setLayoutX(210);
        btnReiniciar.setLayoutY(200);
        btnReiniciar.setPrefSize(50, 50);
        btnOpenMenu.setPrefSize(50, 50);
        pane.getChildren().addAll(rectanguloFondo, mensajeFinal, btnOpenMenu, btnReiniciar);
        timeline.play();
        btnReiniciar.setOnAction(event -> {

            // Obtiene una referencia a la ventana actual
            Stage ventanaActual = (Stage) btnReiniciar.getScene().getWindow();

            // Crea una nueva instancia de la ventana principal
            arduinoReceiver.closePort();

            BuscaminasFX nuevaVentana = new BuscaminasFX(cantJug);

            nuevaVentana.start(new Stage());

            // Cierra la ventana actual
            ventanaActual.close();
        });

        btnOpenMenu.setOnAction(event -> {

            // Crear una instancia de la ventana del menú del juego
            GameMenuDemo gameMenu = new GameMenuDemo();

            // Llamar al método start() para mostrar la ventana
            Platform.runLater(() -> {
                try {
                    Stage stage = new Stage();
                    gameMenu.start(stage);
                    // Cerrar la ventana principal al abrir el GameMenu
                    ((Stage) btnOpenMenu.getScene().getWindow()).close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        GaussianBlur gaussianBlur = new GaussianBlur(0);
        btnOpenMenu.setEffect(gaussianBlur);

        btnOpenMenu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: WHITE; -fx-font-size: 20px;");

        btnOpenMenu.setOnMouseEntered(event -> {
            // Crear una transición de animación para mover el botón en el eje X

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), btnOpenMenu);
            translateTransition.setToX(5);
            translateTransition.play();

            // Cambiar el efecto GaussianBlur del botón
            gaussianBlur.setRadius(2);
        });

// Agregar un efecto cuando el mouse sale del botón
        btnOpenMenu.setOnMouseExited(event -> {
            // Crear una transición de animación para mover el botón de nuevo a su posición original
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), btnOpenMenu);
            translateTransition.setToX(0);
            translateTransition.play();

            // Restablecer el efecto GaussianBlur del botón
            gaussianBlur.setRadius(0);
        });

        btnReiniciar.setEffect(gaussianBlur);

        btnReiniciar.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-text-fill: WHITE; -fx-font-size: 20px;");

        btnReiniciar.setOnMouseEntered(event -> {
            // Crear una transición de animación para mover el botón en el eje X

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), btnReiniciar);
            translateTransition.setToX(5);
            translateTransition.play();

            // Cambiar el efecto GaussianBlur del botón
            gaussianBlur.setRadius(2);
        });

// Agregar un efecto cuando el mouse sale del botón
        btnReiniciar.setOnMouseExited(event -> {
            // Crear una transición de animación para mover el botón de nuevo a su posición original
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), btnReiniciar);
            translateTransition.setToX(0);
            translateTransition.play();

            // Restablecer el efecto GaussianBlur del botón
            gaussianBlur.setRadius(0);
        });

        System.out.println("Juego terminado");
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
