package com.mycompany.primerproyectobatallanaval;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VentanaInicioController implements Initializable {

    @FXML
    private Button ButtonJugar;
    @FXML
    private Button ButtonAcercaDe;
    @FXML
    private Button ButtonSalir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Reproductor.getInstance(getClass().getResource("/Musica/Song15.mp3").toExternalForm(), 1).PlayOnBucle();
    }

    @FXML
    private void OnJugarPrecionado(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("VentanaNiveles.fxml"));
            Stage stage = (Stage) ButtonJugar.getScene().getWindow();

            Scene nuevaEscena = new Scene(root);
            stage.setScene(nuevaEscena);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show(); 

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void OnAcercaDePrecionado(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de");
        alert.setHeaderText("Información del Juego: \n* Nombre del Curso: Programación II.\n* Estudiante: Randall Anel Arauz Cubilla.\n* Ciclo: III.\n* Año: 2025.");
        alert.setContentText("========== INSTRUCCIONES ==========\n"
                + "1. Posiciona tus barcos en el tablero propio.\n"
                + "2. Ataca al tablero enemigo seleccionando casillas.\n"
                + "3. Gana el juego hundiendo todos los barcos enemigos antes de que el rival hunda los tuyos.\n"
                + "================================");
        alert.showAndWait();
    }

    @FXML
    private void OnSalirPrecionado(ActionEvent event) {
        Reproductor instancia = Reproductor.getInstance(null, 0);
        if (instancia != null) {
            instancia.Stop();
        }
        Platform.exit();
    }
}
