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
 * @author RANDALL AC
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
    @FXML
    private Label txtSubmarinos;
    @FXML
    private Label txtBarcosCruceros;
    @FXML
    private Label txtBarcoAcorazado;
    @FXML
    private Label txtBarcosDestructores;

    private Tablero tableroComputadora; // Declarar el tablero de la computadora
    private Tablero tableroJugador;
    private Button[][] botonesComputadora = new Button[10][10];
    private Button[][] botonesJugador = new Button[10][10];

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableroComputadora = new Tablero(gridPaneComputadora);
        tableroJugador = new Tablero(gridPaneJugador);

        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                Button boton = new Button();
                boton.setPrefSize(50, 50);
                botonesComputadora[fila][columna] = boton;

                final int finalFila = fila;
                final int finalColumna = columna;

                boton.setOnAction(event -> {
                    dispararEnTableroComputadora(finalFila, finalColumna);
                });
                gridPaneComputadora.add(boton, columna, fila); 
            }
        }

        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                Button boton = new Button();
                boton.setPrefSize(50, 50); 
                botonesJugador[fila][columna] = boton; 
                gridPaneJugador.add(boton, columna, fila); 
            }
        }
    }

    @FXML
    private void ordenarBarcosAleatoriamente(ActionEvent event) {

        tableroJugador.limpiarTablero();
        
        int submarinosColocados = 0;
        int destructoresColocados = 0;
        int crucerosColocados = 0;
        int acorazadosColocados = 0;

        while (submarinosColocados < 4 || destructoresColocados < 3 || crucerosColocados < 2 || acorazadosColocados < 1) {
            boolean colocado = false;
            int fila = (int) (Math.random() * 10);
            int columna = (int) (Math.random() * 10);
            boolean horizontal = Math.random() < 0.5;

            Barco barco = null;

            if (submarinosColocados < 4) {
                barco = new Barco(1); // Submarino ocupa 1 celda
                submarinosColocados++;
            } else if (destructoresColocados < 3) {
                barco = new Barco(2); // Destructor ocupa 2 celdas
                destructoresColocados++;
            } else if (crucerosColocados < 2) {
                barco = new Barco(3); // Crucero ocupa 3 celdas
                crucerosColocados++;
            } else if (acorazadosColocados < 1) {
                barco = new Barco(4); // Acorazado ocupa 4 celdas
                acorazadosColocados++;
            }

            if (barco != null) {
                colocado = tableroJugador.colocarBarco(barco, fila, columna, horizontal);
                if (!colocado) {
                    switch (barco.getTamaño()) {
                        case 1:
                            submarinosColocados--;
                            break;
                        case 2:
                            destructoresColocados--;
                            break;
                        case 3:
                            crucerosColocados--;
                            break;
                        case 4:
                            acorazadosColocados--;
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        System.out.println("Barcos colocados en el tablero del jugador:");
        tableroJugador.imprimirBarcos(); // Depuración
        mostrarBarcosColocadosJugador(); // Muestra visualmente los barcos en el tablero
        IdMensajePista.setText("¡Tus barcos han sido colocados de forma aleatoria!");
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

        int submarinosColocados = 0;
        int destructoresColocados = 0;
        int crucerosColocados = 0;
        int acorazadosColocados = 0;

        while (submarinosColocados < 4 || destructoresColocados < 3 || crucerosColocados < 2 || acorazadosColocados < 1) {
            boolean colocado = false;
            int fila = (int) (Math.random() * 10);
            int columna = (int) (Math.random() * 10);
            boolean horizontal = Math.random() < 0.5;

            Barco barco = null;

            // Definir el tipo de barco a colocar
            if (submarinosColocados < 4) {
                barco = new Barco(1); // Submarino ocupa 1 celda
                submarinosColocados++;
            } else if (destructoresColocados < 3) {
                barco = new Barco(2); // Destructor ocupa 2 celdas
                destructoresColocados++;
            } else if (crucerosColocados < 2) {
                barco = new Barco(3); // Crucero ocupa 3 celdas
                crucerosColocados++;
            } else if (acorazadosColocados < 1) {
                barco = new Barco(4); // Acorazado ocupa 4 celdas
                acorazadosColocados++;
            }

            // Intentar colocar el barco
            if (barco != null) {
                colocado = tableroComputadora.colocarBarco(barco, fila, columna, horizontal);
                if (!colocado) {
                    switch (barco.getTamaño()) {
                        case 1:
                            submarinosColocados--;
                            break;
                        case 2:
                            destructoresColocados--;
                            break;
                        case 3:
                            crucerosColocados--;
                            break;
                        case 4:
                            acorazadosColocados--;
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        System.out.println("Barcos colocados en el tablero:");
        tableroComputadora.imprimirBarcos(); // Para depuración
        mostrarBarcosColocadosJugador();

        IdMensajePista.setText("¡Barcos colocados! Turno del jugador.");
    }

    private void turnoComputadora() {
        boolean disparoValido = false;
        while (!disparoValido) {
            int fila = (int) (Math.random() * 10);
            int columna = (int) (Math.random() * 10);

            if (!tableroJugador.getCasillasAtacadas()[fila][columna]) {
                String resultado = tableroJugador.atacarCasilla(fila, columna); // Disparo
                disparoValido = true;
                actualizarVistaTableroJugador(); // Refleja los cambios en el tablero
                IdResultadoDisparo.setText("La computadora dispara: " + resultado);
            }
        }
        if (verificarVictoria(tableroJugador)) {
            IdMensajePista.setText("¡Perdiste! La computadora hundió todos tus barcos.");
            return; // Finaliza el juego
        }
        IdMensajePista.setText("¡Es tu turno!");
    }

    private void deshabilitarTableroComputadora() {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                botonesComputadora[fila][columna].setDisable(true); 
            }
        }
    }

    @FXML
    private void dispararEnTableroComputadora(int fila, int columna) {
        String resultado = tableroComputadora.atacarCasilla(fila, columna); // Procesa el disparo
        IdResultadoDisparo.setText(resultado); 
        actualizarVistaTableroComputadora(); // Refleja los cambios en el tablero
        if (verificarVictoria(tableroComputadora)) {
            IdMensajePista.setText("¡Felicidades, ganaste! Todos los barcos del oponente están hundidos.");
            deshabilitarTableroComputadora();
            return;
        }
        turnoComputadora();
    }

    private void actualizarVistaTableroComputadora() {
        boolean[][] ocupadas = tableroComputadora.getCasillasOcupadas();
        boolean[][] atacadas = tableroComputadora.getCasillasAtacadas();

        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                Button boton = botonesComputadora[fila][columna];

                if (atacadas[fila][columna]) {
                    if (ocupadas[fila][columna]) {
                        // Verificar si el barco está completamente hundido
                        boolean hundido = false;
                        for (Barco barco : tableroComputadora.getBarcos()) {
                            if (barco.estaHundido()) { // Si está hundido
                                for (int[] posicion : barco.getPosiciones()) {
                                    if (posicion[0] == fila && posicion[1] == columna) {
                                        hundido = true;
                                        break;
                                    }
                                }
                            }
                        }
                        boton.setStyle(hundido ? "-fx-background-color: black;" // Hundido
                                : "-fx-background-color: yellow;"); // Averiado
                    } else {
                        boton.setStyle("-fx-background-color: blue;"); // Agua
                    }
                } else {
                    boton.setStyle("-fx-background-color: lightgray;"); // Casilla no atacada
                }
            }
        }
    }

    private void actualizarVistaTableroJugador() {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                Button boton = botonesJugador[fila][columna];

                boolean ocupada = tableroJugador.getCasillasOcupadas()[fila][columna];
                boolean atacada = tableroJugador.getCasillasAtacadas()[fila][columna];

                if (atacada) {
                    if (ocupada) {
                        boolean hundido = false;

                        for (Barco barco : tableroJugador.getBarcos()) {
                            if (barco.estaHundido()) {
                                for (int[] posicion : barco.getPosiciones()) {
                                    if (posicion[0] == fila && posicion[1] == columna) {
                                        hundido = true;
                                        break;
                                    }
                                }
                            }
                        }
                        boton.setStyle(hundido ? "-fx-background-color: black;" // Hundido
                                : "-fx-background-color: yellow;"); // Tocado
                    } else {
                        boton.setStyle("-fx-background-color: blue;"); // Agua
                    }
                }
            }
        }
    }

    private boolean verificarVictoria(Tablero tablero) {
        if (tablero.getBarcos().isEmpty()) {
            return false; // Si no hay barcos, no se puede declarar victoria
        }
        for (Barco barco : tablero.getBarcos()) {
            if (!barco.estaHundido()) {
                return false; // Si al menos un barco no está hundido, no hay victoria
            }
        }
        return true; // Todos los barcos están hundidos
    }

    private void mostrarBarcosColocadosJugador() {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                Button boton = botonesJugador[fila][columna];

                boolean ocupada = tableroJugador.getCasillasOcupadas()[fila][columna];

                if (ocupada) {
                    boton.setStyle("-fx-background-color: green;");
                } else {
                    boton.setStyle("-fx-background-color: lightgray;");
                }
            }
        }
    }
}
