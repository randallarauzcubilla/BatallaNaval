package com.mycompany.primerproyectobatallanaval;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author randa
 */
public class VentanaJuegoController implements Initializable {

    @FXML
    private GridPane gridPaneJugador;
    @FXML
    private GridPane gridPaneComputadora;
    @FXML
    private Button btnOrdenAleatorio;
    @FXML
    private Button btnOrientacion;
    @FXML
    private Button btnDeshacerTablero;
    @FXML
    private Button btnRevelarBarcos;
    @FXML
    private Button btnTerminarJuego;
    @FXML
    private Label IdResultadoDisparo;
    @FXML
    private Label IdMensajePista;
    @FXML
    private Label IdTiempoTurno;
    @FXML
    private ImageView idBarcoSubmarino1;
    @FXML
    private ImageView idBarcoSubmarino2;
    @FXML
    private ImageView idBarcoSubmarino3;
    @FXML
    private ImageView idBarcoSubmarino4;
    @FXML
    private Button btnVolverNiveles;
    @FXML
    private ImageView idBarcoAcorazado;
    @FXML
    private ImageView idBarcoCrucero1;
    @FXML
    private ImageView idBarcoCrucero2;
    @FXML
    private ImageView idBarcoDestructor1;
    @FXML
    private ImageView idBarcoDestructor3;
    @FXML
    private ImageView idBarcoDestructor2;
    @FXML
    private Button btnComenzarBatalla;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void ordenarBarcosAleatoriamente(ActionEvent event) {
    }

    @FXML
    private void OnButtonAlternarOrientacion(ActionEvent event) {
    }

    @FXML
    private void OnButtonDeshacerColocacion(ActionEvent event) {
    }

    @FXML
    private void OnButtonRevelarBarcos(ActionEvent event) {
    }

    @FXML
    private void OnButtonTerminarPartida(ActionEvent event) {
    }

    @FXML
    private void OnBtnComenzarPartida(ActionEvent event) {
    }
    
}
