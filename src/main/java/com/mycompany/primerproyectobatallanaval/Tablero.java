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

    public Tablero(GridPane gridPane) {
        this.gridPane = gridPane;
        this.barcos = new ArrayList<>();
        this.casillasOcupadas = new boolean[10][10]; // Tablero de 10x10
        this.casillasAtacadas = new boolean[10][10];
    }

    private final int MAX_BARCOS = 10; // Número máximo de barcos permitidos

    public int getMaxBarcos() {
        return MAX_BARCOS;
    }

    public boolean colocarBarco(int fila, int columna, boolean horizontal, int tamaño) {
        Barco barco = new Barco(tamaño); // Asegúrate de que este constructor existe
        return colocarBarco(barco, fila, columna, horizontal); // Llama al método que ya tienes
    }

    public boolean colocarBarco(Barco barco, int fila, int columna, boolean horizontal) {
        if (barcos.size() >= MAX_BARCOS) {
            System.out.println("No se pueden colocar más barcos en el tablero.");
            return false; // Límite de barcos alcanzado
        }
        if (!verificarEspacio(barco.getTamaño(), fila, columna, horizontal)) {
            System.out.println("Espacio no válido para el barco.");
            return false; // Posición inválida
        }
        for (int i = 0; i < barco.getTamaño(); i++) {
            int nuevaFila = fila + (horizontal ? 0 : i);
            int nuevaColumna = columna + (horizontal ? i : 0);
            casillasOcupadas[nuevaFila][nuevaColumna] = true;
            barco.setPosicion(i, nuevaFila, nuevaColumna);
        }
        barcos.add(barco);
        System.out.println("Barco agregado: tamaño " + barco.getTamaño() + ", posición inicial (" + fila + "," + columna + ")");
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
        for (Barco barco : barcos) {
            for (int i = 0; i < barco.getTamaño(); i++) {
                int[] posicion = barco.getPosiciones()[i];
                if (posicion[0] == fila && posicion[1] == columna) {
                    barco.marcarParteTocada(i);
                    if (barco.estaHundido()) {
                        return "¡Hundido!";
                    }
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
}
