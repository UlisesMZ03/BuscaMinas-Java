
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class BuscaminasFX extends Application {

    private final int nFilas = 8;
    private final int nColumnas = 8;

    Rectangle rect = new Rectangle(40, 40);
    private Button[][] casillas = new Button[nFilas][nColumnas];
    private Buscaminas buscaminas = new Buscaminas();

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                Button casilla = new Button();
                casilla.setMinWidth(40);
                casilla.setMinHeight(40);
                casilla.setOnAction(event -> {
                    int fila = GridPane.getRowIndex(casilla);
                    int columna = GridPane.getColumnIndex(casilla);
                    buscaminas.descubrirCasilla(fila, columna);

                    try {
                        mostrarTablero(0);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BuscaminasFX.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    buscaminas.mostrarTablero();
                    //System.out.println(buscaminas.casillasVacias(fila,columna));
                    buscaminas.casillaMina(fila, columna);
                    buscaminas.casillaSeg(fila, columna);
                    buscaminas.agregarListInc(fila, columna);
                    Node temp = buscaminas.listaInc.removeNode();

                    buscaminas.descubrirCasilla(temp.i - 1, temp.j - 1);
                    try {
                        mostrarTablero(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BuscaminasFX.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    buscaminas.listaInc.clear();
                    //System.out.println("Minasssss:   "+buscaminas.nMinas(fila, columna));
                    turnoBot();
                    if (buscaminas.juegoTerminado) {
                        mostrarMensajeFinal();
                    }
                });
                casilla.setShape(rect);
                casilla.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
                root.add(casilla, j, i);
                casillas[i][j] = casilla;
            }
        }
        Scene scene = new Scene(root, 320, 320);
        primaryStage.setTitle("Buscaminas");
        primaryStage.setScene(scene);
        primaryStage.show();
        buscaminas.colocarMinas();
    }

    void turnoBot() {
        Random random = new Random();

        int fila = random.nextInt(nFilas);
        int columna = random.nextInt(nColumnas);

        while (buscaminas.tableroVisible[fila][columna] == 8) {

            System.out.println("La coordenada generada es: (" + (columna + 1) + ", " + (fila + 1) + ")");
            break;
        }

    }

    private void mostrarTablero(int jugador) throws InterruptedException {
        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                Button casilla = casillas[i][j];
                if (jugador == 0) {

                    if (buscaminas.visible[i][j]) {
                        Thread.sleep(10);
                        if (buscaminas.tablero[i][j] == 0) {

                            casilla.setStyle("-fx-background-color: 	DARKGRAY;-fx-border-color: black; -fx-border-width: 1px;");
                        } else if (buscaminas.tablero[i][j] == 9) {
                            casilla.setText("X");
                        } else {
                            casilla.setText(Integer.toString(buscaminas.tablero[i][j]));
                            if (buscaminas.tablero[i][j] > 0) {
                                if (buscaminas.tablero[i][j] == 1) {
                                    casilla.setStyle("-fx-background-color: grey; -fx-text-fill: blue;-fx-border-color: black; -fx-border-width: 1px;");
                                    buscaminas.tableroVisible[i][j] = 1;
                                }
                                if (buscaminas.tablero[i][j] == 2) {
                                    casilla.setStyle("-fx-background-color: grey; -fx-text-fill: LIMEGREEN;-fx-border-color: black; -fx-border-width: 1px;");
                                    buscaminas.tableroVisible[i][j] = 2;

                                }
                                if (buscaminas.tablero[i][j] == 3) {
                                    casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                    buscaminas.tableroVisible[i][j] = 3;
                                }
                                if (buscaminas.tablero[i][j] == 4) {
                                    casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                    buscaminas.tableroVisible[i][j] = 4;
                                }
                                if (buscaminas.tablero[i][j] == 5) {
                                    casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                    buscaminas.tableroVisible[i][j] = 5;
                                }
                                if (buscaminas.tablero[i][j] == 6) {
                                    casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                    buscaminas.tableroVisible[i][j] = 5;
                                }
                                if (buscaminas.tablero[i][j] == 7) {
                                    casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                    buscaminas.tableroVisible[i][j] = 7;
                                }
                                if (buscaminas.tablero[i][j] == 8) {
                                    casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                    buscaminas.tableroVisible[i][j] = 8;
                                }

                            }

                        }
                    } else {
                        casilla.setText("");
                    }
                    if (jugador == 1) {
                        
                        if (buscaminas.visible[i][j]) {
                            Thread.sleep(30);
                            if (buscaminas.tablero[i][j] == 0) {

                                casilla.setStyle("-fx-background-color: 	DARKGRAY;-fx-border-color: black; -fx-border-width: 1px;");
                            } else if (buscaminas.tablero[i][j] == 9) {
                                casilla.setText("X");
                            } else {
                                casilla.setText(Integer.toString(buscaminas.tablero[i][j]));
                                if (buscaminas.tablero[i][j] > 0) {
                                    if (buscaminas.tablero[i][j] == 1) {
                                        casilla.setStyle("-fx-background-color: grey; -fx-text-fill: blue;-fx-border-color: black; -fx-border-width: 1px;");
                                        buscaminas.tableroVisible[i][j] = 1;
                                    }
                                    if (buscaminas.tablero[i][j] == 2) {
                                        casilla.setStyle("-fx-background-color: grey; -fx-text-fill: LIMEGREEN;-fx-border-color: black; -fx-border-width: 1px;");
                                        buscaminas.tableroVisible[i][j] = 2;

                                    }
                                    if (buscaminas.tablero[i][j] == 3) {
                                        casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                        buscaminas.tableroVisible[i][j] = 3;
                                    }
                                    if (buscaminas.tablero[i][j] == 4) {
                                        casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                        buscaminas.tableroVisible[i][j] = 4;
                                    }
                                    if (buscaminas.tablero[i][j] == 5) {
                                        casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                        buscaminas.tableroVisible[i][j] = 5;
                                    }
                                    if (buscaminas.tablero[i][j] == 6) {
                                        casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                        buscaminas.tableroVisible[i][j] = 5;
                                    }
                                    if (buscaminas.tablero[i][j] == 7) {
                                        casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                        buscaminas.tableroVisible[i][j] = 7;
                                    }
                                    if (buscaminas.tablero[i][j] == 8) {
                                        casilla.setStyle("-fx-background-color: grey; -fx-text-fill: red;-fx-border-color: black; -fx-border-width: 1px;");
                                        buscaminas.tableroVisible[i][j] = 8;
                                    }

                                }

                            }
                        } else {

                            casilla.setText("");

                        }
                    }
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
