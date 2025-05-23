package com.mycompany.primerproyectobatallanaval;

import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import java.util.ArrayList;

public class Tablero {

    private GridPane gridPane; // Representación gráfica del tablero
    private ArrayList<Barco> barcos; // Lista de barcos en el tablero
    private boolean[][] casillasOcupadas; // Marcar si una casilla está ocupada
    private boolean[][] casillasAtacadas; // Marcar si una casilla fue atacada
    private boolean[] partesTocadas;
    private int cantidadDisparos = 0;

    public Tablero(GridPane gridPane) {
        this.gridPane = gridPane;
        this.barcos = new ArrayList<>();
        this.casillasOcupadas = new boolean[10][10];
        this.casillasAtacadas = new boolean[10][10];
    }

    private final int MAX_BARCOS = 10;

    public int getMaxBarcos() {
        return MAX_BARCOS;
    }

    public int getCantidadDisparos() {
        return cantidadDisparos;
    }

    public boolean colocarBarco(int fila, int columna, boolean horizontal, int tamaño) {
        Barco barco = new Barco(tamaño);
        return colocarBarco(barco, fila, columna, horizontal);
    }

    public boolean colocarBarco(Barco barco, int fila, int columna, boolean horizontal) {
        if (barcos.size() >= MAX_BARCOS) {
            System.out.println("No se pueden colocar más barcos en el tablero.");
            return false;
        }
        if (!verificarEspacio(barco.getTamaño(), fila, columna, horizontal)) {
            System.out.println("Espacio no válido para el barco de tamaño " + barco.getTamaño());
            return false;
        }
        for (int i = 0; i < barco.getTamaño(); i++) {
            int nuevaFila = fila + (horizontal ? 0 : i);
            int nuevaColumna = columna + (horizontal ? i : 0);
            casillasOcupadas[nuevaFila][nuevaColumna] = true;
            barco.setPosicion(i, nuevaFila, nuevaColumna);
        }
        barcos.add(barco);
        return true;
    }

    public boolean verificarEspacio(int tamaño, int fila, int columna, boolean horizontal) {
        for (int i = 0; i < tamaño; i++) {
            int nuevaFila = fila + (horizontal ? 0 : i);
            int nuevaColumna = columna + (horizontal ? i : 0);
            if (nuevaFila >= 10 || nuevaColumna >= 10) {
                System.out.println("El barco se sale del límite del tablero.");
                return false;
            }
            if (casillasOcupadas[nuevaFila][nuevaColumna]) {
                System.out.println("La posición está ocupada por otro barco.");
                return false;
            }
        }
        System.out.println("Espacio válido para barco de tamaño " + tamaño);
        return true;
    }

    public String atacarCasilla(int fila, int columna) {
        if (casillasAtacadas[fila][columna]) {
            return "Ya atacaste esta casilla, intenta en otra.";
        }
        casillasAtacadas[fila][columna] = true; // Marcar la casilla como atacada
        cantidadDisparos++;
        for (Barco barco : barcos) {
            for (int i = 0; i < barco.getTamaño(); i++) {
                int[] posicion = barco.getPosiciones()[i];
                if (posicion[0] == fila && posicion[1] == columna) {
                    barco.marcarParteTocada(i);
                    if (barco.estaHundido()) {
                         EfectoSonido.reproducir("/Musica/BarcoDestruido.mp3");
                        return "¡Hundido!";
                    }
                     EfectoSonido.reproducir("/Musica/ImpactoDeBarco.mp3");
                    return "¡Averiado!";
                }
            }
        }
        return "¡AGUA!";
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public ArrayList<Barco> getBarcos() {
        return barcos;
    }

    public boolean[][] getCasillasOcupadas() {
        return casillasOcupadas;
    }

    public boolean[][] getCasillasAtacadas() {
        return casillasAtacadas;
    }

    public void imprimirBarcos() {
        for (Barco barco : barcos) {
            System.out.println("Barco de tamaño " + barco.getTamaño() + " en posiciones:");
            for (int[] posicion : barco.getPosiciones()) {
                System.out.println("Fila: " + posicion[0] + ", Columna: " + posicion[1]);
            }
        }
    }

    public void limpiarTablero() {
        barcos.clear(); // Vaciar la lista de barcos
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                casillasOcupadas[fila][columna] = false;
                casillasAtacadas[fila][columna] = false;
            }
        }
    }

    public boolean esPosicionValida(int fila, int columna, boolean esHorizontal, int tamañoBarco) {
        if (fila < 0 || columna < 0 || fila >= 10 || columna >= 10) {
            return false;
        }
        if ((esHorizontal && columna + tamañoBarco > 10) || (!esHorizontal && fila + tamañoBarco > 10)) {
            return false;
        }

        int filaInicio = Math.max(0, fila - 1);
        int filaFin = Math.min(9, (esHorizontal ? fila : fila + tamañoBarco - 1) + 1);
        int colInicio = Math.max(0, columna - 1);
        int colFin = Math.min(9, (esHorizontal ? columna + tamañoBarco - 1 : columna) + 1);

        for (int f = filaInicio; f <= filaFin; f++) {
            for (int c = colInicio; c <= colFin; c++) {
                if (this.casillasOcupadas[f][c]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean hayEspacioAlrededor(int fila, int columna) {
        for (int i = fila - 1; i <= fila + 1; i++) {
            for (int j = columna - 1; j <= columna + 1; j++) {
                if (estaDentroTablero(i, j) && hayBarcoEn(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean estaDentroTablero(int fila, int columna) {
        return fila >= 0 && fila < 10 && columna >= 0 && columna < 10;
    }

    private boolean hayBarcoEn(int fila, int columna) {
        return casillasOcupadas[fila][columna];
    }
}
