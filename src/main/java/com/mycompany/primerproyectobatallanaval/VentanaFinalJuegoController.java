package com.mycompany.primerproyectobatallanaval;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author randa
 */
public class VentanaFinalJuegoController implements Initializable {

    @FXML
    private Label mensajeFinalLabel;

    @FXML
    private Label nombreJugadorLabel;
    @FXML
    private Button btnVolverJugar;
    @FXML
    private Button btnVolverInicioDesdeFinJuego;
    @FXML
    private ImageView imagenResultado;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void configurarFinal(String resultado) {
        String nombre = UsuarioData.getNombreUsuario();
        nombreJugadorLabel.setText(nombre);

        String rutaBase = "/Imagenes/";

        switch (resultado) {
            case "ganador":
                mensajeFinalLabel.setText("¡Ganaste!");
                imagenResultado.setImage(new Image(getClass().getResourceAsStream(rutaBase + "ganador2.jpg")));
                break;
            case "perdedor":
                mensajeFinalLabel.setText("Perdiste");
                imagenResultado.setImage(new Image(getClass().getResourceAsStream(rutaBase + "Derrota.jpg")));
                break;
            case "empate":
                mensajeFinalLabel.setText("¡Empate!");
                imagenResultado.setImage(new Image(getClass().getResourceAsStream(rutaBase + "empatee.jpg")));
                break;
        }
    }

    @FXML
    private void volverAVentanaInicio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaInicio.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnVolverInicioDesdeFinJuego.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void volverAVentanaJuego(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaNiveles.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnVolverJugar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.sizeToScene(); 
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
