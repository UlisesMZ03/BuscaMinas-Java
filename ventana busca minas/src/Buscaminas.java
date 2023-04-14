
import java.util.Random;


public class Buscaminas {

    private final int nFilas = 8;
    private final int nColumnas = 8;
    private final int nMinas = 3;

    int[][] tablero = new int[nFilas][nColumnas];
    boolean[][] visible = new boolean[nFilas][nColumnas];
    int[][] tableroVisible = new int[nFilas][nColumnas];

    boolean juegoTerminado = false;
    
    minaList listaMina;
    segList listaSeg;
    incList listaInc;

    public Buscaminas() {
        inicializarTablero();
        colocarMinas();
        listaMina = new minaList();
        listaSeg = new segList();
        listaInc = new incList();
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

    void mostrarTablero() {
        System.out.print("  ");
        for (int j = 0; j < nColumnas; j++) {
            //System.out.print(j + " ");
        }
        System.out.println();

        for (int i = 0; i < nFilas; i++) {
            //System.out.print(i + " ");
            for (int j = 0; j < nColumnas; j++) {
                if (visible[i][j]) {
                    if (tablero[i][j] == 0) {

                        //tableroVisible[i][j]=0;
                        //System.out.print(tableroVisible[i][j]);
                        System.out.print(tableroVisible[i][j]);
                    } else if (tablero[i][j] == 9) {
                        System.out.print("X");
                    } else {
                        //tableroVisible[i][j] = contarMinasAdyacentes(i,j);
                        //System.out.print(tablero[i][j]);
                        System.out.print(tableroVisible[i][j]);

                    }
                } else {
                    tableroVisible[i][j] = 8;
                    System.out.print(tableroVisible[i][j]);
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

        } else if (tablero[fila][columna] == 0) {
            tableroVisible[fila][columna] = 0;

            descubrirCasillasAdyacentes(fila, columna);

        }

        if (!juegoTerminado && haGanado()) {
            juegoTerminado = true;

        }
    }

    void casillaSeg(int fila, int columna) {
        for (fila = 0; fila < nFilas; fila++) {

            for (columna = 0; columna < nFilas; columna++) {

                if ((nMinas(fila, columna) == tableroVisible[fila][columna])) {

                    for (int i = Math.max(fila - 1, 0); i <= Math.min(fila + 1, nFilas - 1); i++) {

                        for (int j = Math.max(columna - 1, 0); j <= Math.min(columna + 1, nColumnas - 1); j++) {
                            if (!listaMina.contains(j + 1, i + 1) && tableroVisible[i][j] == 8) {
                                listaSeg.addNode(j + 1, i + 1);
                                listaSeg.printList1();
                            }
                            if (listaSeg.contains(j + 1, i + 1) && tableroVisible[i][j] < 8) {
                                listaSeg.removeNode(j + 1, i + 1);
                            }

                        }

                    }
                }
            }
        }
    }

    int cantMina(int fila, int columna) {
        int cantMina = 0;

        if (casillasVacias(fila, columna) == tableroVisible[fila][columna]) {
            if (tableroVisible[fila][columna] != 8 && tableroVisible[fila][columna] > 0) {

                for (int i = Math.max(fila - 1, 0); i <= Math.min(fila + 1, nFilas - 1); i++) {
                    for (int j = Math.max(columna - 1, 0); j <= Math.min(columna + 1, nColumnas - 1); j++) {

                        if (tableroVisible[i][j] == 8) {
                            cantMina++;

                        }
                    }
                }
            }
        }

        return cantMina;

    }

    int nMinas(int fila, int columna) {
        int nMinas = 0;
        if (tableroVisible[fila][columna] != 8 && tableroVisible[fila][columna] > 0) {
            for (int i = Math.max(fila - 1, 0); i <= Math.min(fila + 1, nFilas - 1); i++) {
                for (int j = Math.max(columna - 1, 0); j <= Math.min(columna + 1, nColumnas - 1); j++) {
                    if (tableroVisible[i][j] == 8 && listaMina.contains(j + 1, i + 1)) {
                        nMinas++;
                        //System.out.println(nMinas);
                    }
                }
            }
            return nMinas;

        }
        return 0;
    }

    void agregarListInc(int fila, int columna) {
        for (fila = 0; fila < nFilas; fila++) {

            for (columna = 0; columna < nFilas; columna++) {
                if (tableroVisible[fila][columna] == 8) {
                    listaInc.addNode(columna + 1, fila + 1);
                    listaInc.printList1();
                }
            }
        }
    }

    int casillasVacias(int fila, int columna) {
        int cantVacias = 0;
        if (tableroVisible[fila][columna] != 8 && tableroVisible[fila][columna] > 0) {
            for (int i = Math.max(fila - 1, 0); i <= Math.min(fila + 1, nFilas - 1); i++) {
                for (int j = Math.max(columna - 1, 0); j <= Math.min(columna + 1, nColumnas - 1); j++) {
                    if (tableroVisible[i][j] == 8) {
                        listaInc.addNode(j + 1, i + 1);

                        cantVacias++;
                    }
                }
            }
            return cantVacias;
        }
        return 0;
    }

    void esMina() {

    }

    void casillaMina(int fila, int columna) {

        for (fila = 0; fila < nFilas; fila++) {

            for (columna = 0; columna < nFilas; columna++) {
                if (casillasVacias(fila, columna) == tableroVisible[fila][columna]) {
                    if (tableroVisible[fila][columna] != 8 && tableroVisible[fila][columna] > 0) {
                        //System.out.println(tableroVisible[fila][columna]);
                        for (int i = Math.max(fila - 1, 0); i <= Math.min(fila + 1, nFilas - 1); i++) {
                            for (int j = Math.max(columna - 1, 0); j <= Math.min(columna + 1, nColumnas - 1); j++) {

                                if (tableroVisible[i][j] == 8) {

                                    listaMina.addNode(j + 1, i + 1);
                                    listaMina.printList();

                                }
                            }

                        }
                    }

                }
            }
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

    boolean haGanado() {
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
