
import java.util.Random;

/**
 *
 * La clase Buscaminas representa el juego Buscaminas, donde el objetivo es
 * descubrir todas las casillas del tablero sin hacer clic en una mina. El
 * tablero del juego está representado por un arreglo bidimensional de enteros
 * llamado "tablero", donde los valores de 0 a 8 indican el número de minas
 * adyacentes a esa casilla, mientras que 9 indica la presencia de una mina en
 * esa casilla. La visibilidad de cada casilla del tablero está representada por
 * un arreglo bidimensional de booleanos llamado "visible", donde "true" indica
 * que la casilla es visible y "false" indica que la casilla no es visible. El
 * arreglo bidimensional "tableroVisible" se utiliza para imprimir el estado
 * actual del tablero del juego, donde los valores de 0 a 8 se imprimen como el
 * número correspondiente y 9 se imprime como "X". La clase también contiene un
 * objeto "stack" de la clase Stack para almacenar sugerencias para el jugador.
 */
public class Buscaminas {

    private final int nFilas = 8;
    private final int nColumnas = 8;
    public final int nMinas = 8;

    int[][] tablero = new int[nFilas][nColumnas];
    boolean[][] visible = new boolean[nFilas][nColumnas];
    int[][] tableroVisible = new int[nFilas][nColumnas];

    boolean juegoTerminado = false;

    minaList listaMina;
    segList listaSeg;
    incList listaInc;
    segList listaSeg2;
    ProbList listaProb;
    Stack stack;

    /**
     * Constructor para la clase Buscaminas. Inicializa el tablero del juego y
     * coloca las minas. También inicializa los objetos de las clases minaList,
     * segList, incList, segList, ProbList y Stack.
     */
    public Buscaminas() {
        inicializarTablero();
        colocarMinas();
        listaMina = new minaList();
        listaSeg = new segList();
        listaInc = new incList();
        listaSeg2 = new segList();
        listaProb = new ProbList();
        stack = new Stack();
    }

    /**
     * Inicializa el tablero del juego y los arreglos de visibilidad de
     * casillas.
     */
    private void inicializarTablero() {
        for (int i = 0; i < nFilas; i++) {
            for (int j = 0; j < nColumnas; j++) {
                tablero[i][j] = 0;
                visible[i][j] = false;
                //System.out.println(tablero[i][j]);
            }
        }
    }

    /**
     * Coloca las minas en el tablero del juego de manera aleatoria. Las minas
     * se colocan en casillas que no tienen una mina previamente colocada.
     * Después de colocar las minas, se llama al método "calcularNumeros" para
     * calcular el número de minas adyacentes a cada casilla del tablero.
     */
    void colocarMinas() {
        Random random = new Random();

        int minasColocadas = 0;
        while (minasColocadas < nMinas / 2) {
            int fila = random.nextInt(nFilas);
            int columna = random.nextInt(nColumnas);
            if (tablero[fila][columna] != 9) {
                tablero[fila][columna] = 9;
                minasColocadas++;
            }
        }
        calcularNumeros();
    }

    /**
     *
     * Método que calcula los números en el tablero que indican cuántas minas
     * hay en las casillas adyacentes. Los números son almacenados en la matriz
     * tablero.
     */
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

    /**
     *
     * Método que cuenta el número de minas adyacentes a una casilla determinada
     * en la matriz tablero.
     *
     * @param fila la fila en la que se encuentra la casilla
     * @param columna la columna en la que se encuentra la casilla
     * @return el número de minas adyacentes a la casilla dada
     */
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

    /**
     *
     * Método que muestra el tablero visible en la consola. Los elementos en la
     * matriz tableroVisible son mostrados, y los elementos vacíos son
     * representados con ceros. Los elementos no visibles son representados con
     * un número 8.
     */
    void mostrarTablero() {
        //System.out.print("  ");
        for (int j = 0; j < nColumnas; j++) {
            //System.out.print(j + " ");
        }
        //System.out.println();

        for (int i = 0; i < nFilas; i++) {
            //System.out.print(i + " ");
            for (int j = 0; j < nColumnas; j++) {
                if (visible[i][j]) {
                    if (tablero[i][j] == 0) {

                        tableroVisible[i][j] = 0;
                        //System.out.print(tableroVisible[i][j]);
                        //System.out.print(tableroVisible[i][j]);
                    } else if (tablero[i][j] == 9) {
                        //System.out.print("X");
                    } else {
                        //tableroVisible[i][j] = contarMinasAdyacentes(i,j);
                        //System.out.print(tablero[i][j]);
                        //System.out.print(tableroVisible[i][j]);

                    }
                } else {
                    tableroVisible[i][j] = 8;
                    //System.out.print(tableroVisible[i][j]);
                }
                //System.out.print(" ");
            }
            //System.out.println();
        }
    }

    /**
     *
     * Método que descubre una casilla en el tablero. Si la casilla es una mina,
     * el juego termina. Si la casilla es vacía, las casillas adyacentes también
     * son descubiertas. Si se descubren todas las casillas que no son minas, el
     * juego termina.
     *
     * @param fila la fila en la que se encuentra la casilla que se va a
     * descubrir
     * @param columna la columna en la que se encuentra la casilla que se va a
     * descubrir
     */
    void descubrirCasilla(int fila, int columna) {
        if (visible[fila][columna]) {
            return;
        }

        visible[fila][columna] = true;
        if (tablero[fila][columna] == 9) {
            juegoTerminado = true;

        } else if (tablero[fila][columna] == 0) {
            tableroVisible[fila][columna] = 10;

            descubrirCasillasAdyacentes(fila, columna);

        }

        if (!juegoTerminado && haGanado()) {
            juegoTerminado = true;

        }
    }

    /**
     *
     * Método que agrega nodos al stack de sugerencias. Los nodos representan
     * casillas vacías que aún no han sido descubiertas.
     *
     * @param fila la fila en la que se encuentra la casilla que se va a agregar
     * al stack
     * @param columna la columna en la que se encuentra la casilla que se va a
     * agregar al stack
     */
    void pilaSugerencias(int fila, int columna) {
        boolean agregado = false; // variable para verificar si ya se ha agregado un elemento al stack

        for (fila = 0; fila < nFilas && !agregado; fila++) {
            for (columna = 0; columna < nFilas && !agregado; columna++) {
                if (tablero[fila][columna] != 9 && !visible[fila][columna] && !stack.contains(fila, columna)) {
                    stack.push(fila, columna);
                    System.out.println("Nodo agregado al stack: " + fila + " ," + columna);
                    agregado = true; // establece la variable a verdadero para detener el bucle
                }
            }
        }
    }

    /**
     *
     * Método para generar una lista de probabilidad de que hay mina en una
     * casilla en base a las casillas visibles y la presencia de minas en el
     * tablero de buscaminas.
     *
     * @param fila la fila de la casilla seleccionada.
     *
     * @param columna la columna de la casilla seleccionada.
     */
    void casillaProb(int fila, int columna) {
        listaProb.clear();
        for (fila = 0; fila < nFilas; fila++) {

            for (columna = 0; columna < nFilas; columna++) {

                if ((nMinas(fila, columna) <= tableroVisible[fila][columna]) && tableroVisible[fila][columna] != 8) {

                    for (int i = Math.max(fila - 1, 0); i <= Math.min(fila + 1, nFilas - 1); i++) {

                        for (int j = Math.max(columna - 1, 0); j <= Math.min(columna + 1, nColumnas - 1); j++) {
                            if (!listaMina.contains(j + 1, i + 1) && tableroVisible[i][j] == 8) {
                                if (listaSeg.contains(j + 1, i + 1)) {
                                    double prob = (double) 0;
                                    //System.out.println("Incertidumbre: " + (prob) + "en: " + (j + 1) + "," + (i + 1));
                                    listaProb.addNode(j + 1, i + 1, prob);

                                } else {
                                    int nume = tableroVisible[fila][columna] - nMinas(fila, columna);
                                    int deno = casillasVacias(fila, columna);
                                    double prob = (double) nume / deno;

                                    listaProb.addNode(j + 1, i + 1, prob);

                                    //System.out.println("Incertidumbre: " + (prob) + "en: " + (j + 1) + "," + (i + 1));
                                }

                            }
                            if (listaMina.contains(j + 1, i + 1) && tableroVisible[i][j] == 8) {
                                System.out.println("Aqui hay mina");
                                double prob = (double) 1;
                                //System.out.println("Incertidumbre: " + (prob) + "en: " + (j + 1) + "," + (i + 1));
                                listaProb.addNode(j + 1, i + 1, prob);

                            }

                        }

                    }
                }
            }

        }
        listaProb.updateMaxLabels();
        listaProb.printList1();
    }

    /**
     *
     * Método que encuentra una casilla segura que puede ser descubierta sin
     * correr el riesgo de encontrar una mina. Se utiliza una lista de
     * probabilidad para determinar la casilla más segura.
     *
     * @param fila la fila en la que se encuentra la casilla que se va a
     * analizar
     * @param columna la columna en la que se encuentra la casilla que se va a
     * analizar
     */
    void casillaSeg(int fila, int columna) {
        for (fila = 0; fila < nFilas; fila++) {

            for (columna = 0; columna < nFilas; columna++) {

                if ((nMinas(fila, columna) == tableroVisible[fila][columna])) {

                    for (int i = Math.max(fila - 1, 0); i <= Math.min(fila + 1, nFilas - 1); i++) {

                        for (int j = Math.max(columna - 1, 0); j <= Math.min(columna + 1, nColumnas - 1); j++) {
                            if (!listaMina.contains(j + 1, i + 1) && tableroVisible[i][j] == 8) {
                                listaSeg.addNode(j + 1, i + 1);

                                //listaSeg.printList1();
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

    /**
     *
     * Calcula el número de minas adyacentes a una casilla.
     *
     * @param fila la fila de la casilla.
     *
     * @param columna la columna de la casilla.
     *
     * @return el número de minas adyacentes a la casilla.
     */
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

    /**
     *
     * Agrega las casillas que vacias a una lista enlazada.
     *
     * @param fila la fila de la casilla.
     * @param columna la columna de la casilla.
     */
    void agregarListInc(int fila, int columna) {
        for (fila = 0; fila < nFilas; fila++) {

            for (columna = 0; columna < nFilas; columna++) {
                if (tableroVisible[fila][columna] == 8) {
                    listaInc.addNode(columna + 1, fila + 1);
                    //listaInc.printList1();

                }
//                if (listaInc.contains(columna+1, fila+1) && tableroVisible[columna][fila] < 8) {
//                                listaInc.removeNode2(columna+1, fila+1);}
//                                listaInc.printList1();
            }
        }
    }

    /**
     *
     * Cuenta el número de casillas vacías adyacentes a una casilla.
     *
     * @param fila la fila de la casilla.
     * @param columna la columna de la casilla.
     * @return el número de casillas vacías adyacentes a la casilla.
     */
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

    /**
     *
     * Agrega las casillas con minas adyacentes a una lista enlazada.
     *
     * @param fila la fila de la casilla.
     * @param columna la columna de la casilla.
     */
    void casillaMina(int fila, int columna) {

        for (fila = 0; fila < nFilas; fila++) {

            for (columna = 0; columna < nFilas; columna++) {
                if (casillasVacias(fila, columna) == tableroVisible[fila][columna]) {
                    if (tableroVisible[fila][columna] != 8 && tableroVisible[fila][columna] > 0) {
                        for (int i = Math.max(fila - 1, 0); i <= Math.min(fila + 1, nFilas - 1); i++) {
                            for (int j = Math.max(columna - 1, 0); j <= Math.min(columna + 1, nColumnas - 1); j++) {

                                if (tableroVisible[i][j] == 8) {

                                    listaMina.addNode(j + 1, i + 1);
                                    //listaMina.printList();

                                }
                            }

                        }
                    }

                }
            }
        }

    }

    /**
     *
     * Descubre las casillas adyacentes a la casilla dada.
     *
     * @param fila La fila de la casilla dada.
     * @param columna La columna de la casilla dada.
     */
    private void descubrirCasillasAdyacentes(int fila, int columna) {
        for (int i = Math.max(fila - 1, 0); i <= Math.min(fila + 1, nFilas - 1); i++) {
            for (int j = Math.max(columna - 1, 0); j <= Math.min(columna + 1, nColumnas - 1); j++) {
                if (i != fila || j != columna) {
                    descubrirCasilla(i, j);
                }
            }
        }
    }

    /**
     *
     * Verifica si el jugador ha ganado.
     *
     * @return true si el jugador ha descubierto todas las casillas que no son
     * minas, false en caso contrario.
     */
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
