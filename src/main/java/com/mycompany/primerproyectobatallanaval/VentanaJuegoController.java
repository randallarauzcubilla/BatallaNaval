package com.mycompany.primerproyectobatallanaval;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    @FXML
    private Label IdMensajeUsuario;

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

                boton.setOnMouseClicked(event -> {
                    if (boton.isDisable()) {
                        mostrarMensajeTemporal("Casilla atacada: (" + finalFila + ", " + finalColumna + ").", null, 2);
                    } else {
                        dispararEnTableroComputadora(finalFila, finalColumna);
                    }
                });

                GridPane.setHalignment(boton, HPos.CENTER);
                GridPane.setValignment(boton, VPos.CENTER);
                gridPaneComputadora.add(boton, columna, fila);
            }
        }

        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                Button boton = new Button();
                boton.setPrefSize(50, 50);
                botonesJugador[fila][columna] = boton;
                GridPane.setHalignment(boton, HPos.CENTER);
                GridPane.setValignment(boton, VPos.CENTER);

                gridPaneJugador.add(boton, columna, fila);
            }
        }
        btnRevelarBarcos.setVisible(false);
        btnTerminarJuego.setVisible(false);
        IdTiempoTurno.setVisible(false);
        IdResultadoDisparo.setVisible(false);
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
        IdMensajeUsuario.setText("¡Tus barcos han sido colocados de forma aleatoria!");
    }

    @FXML
    private void OnButtonAlternarOrientacion(ActionEvent event) {
    }

    @FXML
    private void OnButtonDeshacerColocacion(ActionEvent event) {
        System.out.println("Deshaciendo la colocación de los barcos...");

        // Limpiar la lógica del tablero
        tableroJugador.limpiarTablero(); // Elimina todos los barcos y reinicia las casillas ocupadas

        // Actualizar la visualización del tablero
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                Button boton = botonesJugador[fila][columna];
                boton.setStyle("-fx-background-color: lightblue;"); // Restaurar estilo base
            }
        }
        IdMensajeUsuario.setText("¡Tablero reiniciado! Coloca tus barcos nuevamente.");

        System.out.println("Todos los barcos y el tablero han sido reiniciados.");
    }

    @FXML
    private void OnButtonRevelarBarcos(ActionEvent event) {
        System.out.println("Revelando ubicación de los barcos enemigos...");

        // Recorrer la lista de barcos del tablero enemigo
        for (Barco barco : tableroComputadora.getBarcos()) {
            for (int[] posicion : barco.getPosiciones()) {
                int fila = posicion[0];
                int columna = posicion[1];
                Button boton = botonesComputadora[fila][columna];
                boton.setStyle("-fx-background-color: purple;");
            }
        }
        IdMensajeUsuario.setText("¡Barcos enemigos revelados!");
    }

    @FXML
    private void OnButtonTerminarPartida(ActionEvent event) {
        try {
            // Obtén el escenario actual de manera más directa
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Cargar la ventana de inicio o menú principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaInicio.fxml"));
            Parent root = loader.load();
            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.setTitle("Battleship - Menú Principal");
            nuevaVentana.setResizable(false); // Evita que el usuario redimensione la ventana
            nuevaVentana.show();
        } catch (IOException e) {
            // Manejo de errores en caso de que el archivo FXML no se cargue correctamente
            System.out.println("Error al cargar VentanaInicio.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    private void OnVolverPrecionado(ActionEvent event) {
        try {
            // Carga el archivo FXML de la siguiente ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaNiveles.fxml"));
            Parent root = loader.load();

            // Crea una nueva escena y la muestra
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Battleship - Juego");
            stage.show();

            // Opcional: cerrar la ventana actual
            Stage currentStage = (Stage) btnVolverNiveles.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        btnVolverNiveles.setVisible(false);
        btnComenzarBatalla.setVisible(false);
        btnOrdenAleatorio.setVisible(false);
        btnDeshacerTablero.setVisible(false);
        btnOrientacion.setVisible(false);
        btnRevelarBarcos.setVisible(true);
        btnTerminarJuego.setVisible(true);
        IdTiempoTurno.setVisible(true);
        IdResultadoDisparo.setVisible(true);
        txtBarcoAcorazado.setVisible(false);
        txtBarcosDestructores.setVisible(false);
        txtBarcosCruceros.setVisible(false);
        txtSubmarinos.setVisible(false);
        idBarcoAcorazado.setVisible(false);
        idBarcoCrucero1.setVisible(false);
        idBarcoCrucero2.setVisible(false);
        idBarcoDestructor1.setVisible(false);
        idBarcoDestructor2.setVisible(false);
        idBarcoDestructor3.setVisible(false);
        idBarcoSubmarino1.setVisible(false);
        idBarcoSubmarino2.setVisible(false);
        idBarcoSubmarino3.setVisible(false);
        idBarcoSubmarino4.setVisible(false);
        IdMensajeUsuario.setText("¡Barcos colocados! Turno del jugador.");
    }

    private void turnoComputadora() {
        System.out.println("Turno de la computadora...");

        boolean disparoValido = false;
        while (!disparoValido) {
            int fila = (int) (Math.random() * 10);
            int columna = (int) (Math.random() * 10);

            if (!tableroJugador.getCasillasAtacadas()[fila][columna]) {
                String resultado = tableroJugador.atacarCasilla(fila, columna);
                disparoValido = true;

                // Actualizar la vista del tablero del jugador
                actualizarVistaTableroJugador();

                // Mostrar el resultado al usuario
                mostrarMensajeTemporal(null, "La computadora dispara: " + resultado, 2);

                // Verificar si la computadora ganó
                if (verificarVictoria(tableroJugador)) {
                    mostrarMensajeTemporal("¡Perdiste! La computadora hundió todos tus barcos.", null, 2);
                }
            }
        }

        // Pasar el turno al usuario
        mostrarMensajeTemporal("¡Es tu turno!", null, 2);
    }

    private void mostrarMensajeTemporal(String mensajeUsuario, String mensajeComputadora, int duracionSegundos) {
        if (mensajeUsuario != null) {
            IdMensajeUsuario.setText(mensajeUsuario); // Mostrar mensaje para el usuario
        }
        if (mensajeComputadora != null) {
            IdResultadoDisparo.setText(mensajeComputadora); // Mostrar mensaje para la computadora
        }

        // Crear un Timeline para limpiar los mensajes después de duracionSegundos
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(duracionSegundos), event -> {
            IdMensajeUsuario.setText(""); // Limpiar mensaje del usuario
            IdResultadoDisparo.setText(""); // Limpiar mensaje de la computadora
        }));
        timeline.setCycleCount(1); // Ejecutar solo una vez
        timeline.play(); // Iniciar el temporizador
    }

    private void deshabilitarTableroComputadora() {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                botonesComputadora[fila][columna].setDisable(true);
            }
        }
    }

    private void dispararEnTableroComputadora(int fila, int columna) {
        System.out.println("Disparo del usuario en fila: " + fila + ", columna: " + columna);

        // Procesar el disparo lógico en el tablero de la computadora
        String resultado = tableroComputadora.atacarCasilla(fila, columna);

        // Validar disparo repetido
        if (resultado.equals("Ya atacaste esta casilla.")) {
            mostrarMensajeTemporal("¡Intenta en otra posición, esta ya fue atacada!", null, 2);
            return; // Permitir que el usuario realice otro intento
        }

        // Mostrar el resultado al usuario
        mostrarMensajeTemporal("Tu disparo: " + resultado, null, 2);

        // Actualizar la vista del tablero de la computadora
        actualizarVistaTableroComputadora();

        // Verificar victoria del usuario
        if (verificarVictoria(tableroComputadora)) {
            mostrarMensajeTemporal("¡Felicidades, ganaste! Todos los barcos del oponente están hundidos.", null, 2);
            deshabilitarTableroComputadora();
            return; // Detener el juego
        }

        // Si el disparo fue válido, pasar turno a la computadora
        turnoComputadora();
    }

    private void actualizarVistaTableroComputadora() {
        boolean[][] ocupadas = tableroComputadora.getCasillasOcupadas();
        boolean[][] atacadas = tableroComputadora.getCasillasAtacadas();

        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                Button boton = botonesComputadora[fila][columna];

                if (atacadas[fila][columna]) {
                    boton.setDisable(true); // Desactivar el botón
                    boton.setStyle("-fx-opacity: 1;");

                    // Agregar un evento para mostrar mensaje al intentar interactuar con casillas deshabilitadas
                    boton.setOnMouseClicked(event -> {
                        if (boton.isDisable()) {
                            mostrarMensajeTemporal("¡Ya atacaste esta casilla, intenta con otra!", null, 2);
                        }
                    });

                    if (ocupadas[fila][columna]) {
                        boolean hundido = false;
                        for (Barco barco : tableroComputadora.getBarcos()) {
                            if (barco.estaHundido()) {
                                for (int[] posicion : barco.getPosiciones()) {
                                    if (posicion[0] == fila && posicion[1] == columna) {
                                        hundido = true;
                                        break;
                                    }
                                }
                            }
                        }
                        boton.setStyle(hundido
                                ? "-fx-background-color: black; -fx-opacity: 1;" // Hundido
                                : "-fx-background-color: yellow; -fx-opacity: 1;"); // Averiado
                    } else {
                        boton.setStyle("-fx-background-color: blue; -fx-opacity: 1;"); // Agua
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
