package com.mycompany.primerproyectobatallanaval;

public class Barco {

    private int tamaño;
    private int[][] posiciones; // Coordenadas del barco en el tablero (fila y columna)
    private boolean[] partesTocadas; // Marcar qué partes del barco han sido atacadas

    public Barco(int tamaño) {
        this.tamaño = tamaño;
        this.posiciones = new int[tamaño][2]; // [fila, columna]
        this.partesTocadas = new boolean[tamaño];
    }

    public void setPosicion(int indice, int fila, int columna) {
        if (indice >= 0 && indice < tamaño) {
            posiciones[indice][0] = fila;
            posiciones[indice][1] = columna;
        } else {
            throw new IllegalArgumentException("Índice fuera del rango del tamaño del barco.");
        }
    }

    public void marcarParteTocada(int indice) {
        if (indice >= 0 && indice < tamaño) {
            partesTocadas[indice] = true;
        } else {
            throw new IllegalArgumentException("Índice fuera del rango del tamaño del barco.");
        }
    }

    public boolean estaHundido() {
        for (boolean tocada : partesTocadas) {
            if (!tocada) {
                return false;
            }
        }
        return true; // Todas las partes están tocadas
    }

    public int getTamaño() {
        return tamaño;
    }

    public int[][] getPosiciones() {
        return posiciones;
    }
}