
import java.util.Random;


public class Buscaminas {

    private final int nFilas = 8;
    private final int nColumnas = 8;
    private final int nMinas = 3;

    int[][] tablero = new int[nFilas][nColumnas];
    boolean[][] visible = new boolean[nFilas][nColumnas];

    boolean juegoTerminado = false;
    

    public Buscaminas() {
        inicializarTablero();
        colocarMinas();
    }

    private void inicializarTablero() {
        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                tablero[i][j] = 0;
                visible[i][j] = false;
                System.out.println(tablero[i][j]);
            }
        }
    }

    void colocarMinas() {
        Random random = new Random();
        int minasColocadas = 0;
        while (minasColocadas < nMinas) {
            int fila = random.nextInt(nFilas);
            int columna = random.nextInt(nColumnas);
            if (tablero[fila][columna] != 9) {
                tablero[fila][columna] = 9;
                minasColocadas++;
            }
        }
        calcularNumeros();
    }

    private void calcularNumeros() {
        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                if (tablero[i][j] != 9) {
                    int nMinasAdyacentes = contarMinasAdyacentes(i, j);
                    tablero[i][j] = nMinasAdyacentes;
                }
            }
        }
    }

    private int contarMinasAdyacentes(int fila, int columna) {
        int nMinas = 0;
        for (int i = Math.max(fila - 1, 0); i <= Math.min(fila + 1, nFilas - 1); i++) {
            for (int j = Math.max(columna - 1, 0); j <= Math.min(columna + 1, nColumnas - 1); j++) {
                if (tablero[i][j] == 9) {
                    nMinas++;
                }
            }
        }
        return nMinas;
    }

    private void mostrarTablero() {
        System.out.print("  ");
        for (int j = 0; j < nColumnas; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        for (int i = 0; i < nFilas; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < nColumnas; j++) {
                if (visible[i][j]) {
                    if (tablero[i][j] == 0) {
                        System.out.print(" ");

                    } else if (tablero[i][j] == 9) {
                        System.out.print("X");
                    } else {
                        System.out.print(tablero[i][j]);

                    }
                } else {
                    System.out.print("_");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    void descubrirCasilla(int fila, int columna) {
        if (visible[fila][columna]) {
            return;
        }

        visible[fila][columna] = true;
        if (tablero[fila][columna] == 9) {
            juegoTerminado = true;
            System.out.println("¡BOOM! ¡Has perdido!");
        } else if (tablero[fila][columna] == 0) {
            descubrirCasillasAdyacentes(fila, columna);
        }

        if (!juegoTerminado && haGanado()) {
            juegoTerminado = true;
            System.out.println("¡Felicidades! ¡Has ganado!");
        }
    }

    private void descubrirCasillasAdyacentes(int fila, int columna) {
        for (int i = Math.max(fila - 1, 0); i <= Math.min(fila + 1, nFilas - 1); i++) {
            for (int j = Math.max(columna - 1, 0); j <= Math.min(columna + 1, nColumnas - 1); j++) {
                if (i != fila || j != columna) {
                    descubrirCasilla(i, j);
                }
            }
        }
    }

    private boolean haGanado() {
        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                if (!visible[i][j] && tablero[i][j] != 9) {
                    return false;
                }
            }
        }
        return true;
    }

}
