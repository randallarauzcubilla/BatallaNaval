package com.mycompany.primerproyectobatallanaval;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VentanaNivelesController implements Initializable {

    @FXML
    private Button ButtonVolverInicio;
    @FXML
    private Button ButtonDifícil;
    @FXML
    private Button ButtonNormal;
    @FXML
    private Button ButtonFácil;
    @FXML
    private TextField txtNombreUsuario;

    private String nombreUsuario; // Para almacenar el nombre del usuario

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Crear un binding que es true si el campo de nombre está vacío
        BooleanBinding nombreVacio = txtNombreUsuario.textProperty().isEmpty();

        // Deshabilitar los botones de nivel si el campo está vacío
        ButtonFácil.disableProperty().bind(nombreVacio);
        ButtonNormal.disableProperty().bind(nombreVacio);
        ButtonDifícil.disableProperty().bind(nombreVacio);
    }

    @FXML
    private void OnButtonVolverInicioPrecionado(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de inicio
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaInicio.fxml"));
            Parent root = loader.load();

            // Cambiar la escena al contenido de la ventana de inicio
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Battleship - Pantalla de Inicio");
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al cargar VentanaInicio.fxml:");
            e.printStackTrace();
        }
    }

    private void guardarNombreUsuario() {
        if (txtNombreUsuario.getText().trim().isEmpty()) {
            mostrarAlerta("Debes ingresar tu nombre antes de continuar.");
            return;
        }

        String nombre = txtNombreUsuario.getText().trim();
        UsuarioData.setNombreUsuario(nombre);
        System.out.println("Nombre de usuario guardado: " + nombre);
    }

    private void cambiarAVentanaJuego(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana del juego
            Parent root = FXMLLoader.load(getClass().getResource("VentanaJuego.fxml"));

            // Cambiar la escena al contenido del juego
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Battleship - Juego");
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al cargar VentanaJuego.fxml:");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Falta información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void OnButtonNivelDifícilPrecionado(ActionEvent event) {
        guardarNombreUsuario();
        cambiarAVentanaJuego(event);
    }

    @FXML
    private void OnButtonNivelNormalPrecionado(ActionEvent event) {
        guardarNombreUsuario();
        cambiarAVentanaJuego(event);
    }

    @FXML
    private void OnButtonNivelFácilPrecionado(ActionEvent event) {
        guardarNombreUsuario();
        cambiarAVentanaJuego(event);
    }
}
