package com.mycompany.primerproyectobatallanaval;

public class UsuarioData {

    private static String nombreUsuario;
    private static int nivel;

    public static String getNombreUsuario() {
        return nombreUsuario;
    }

    public static void setNombreUsuario(String nombre) {
        nombreUsuario = nombre;
    }

    public static int getNivel() {
        return nivel;
    }

    public static void setNivel(int nivelDificultad) {
        nivel = nivelDificultad;
    }
}