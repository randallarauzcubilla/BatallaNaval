package com.mycompany.primerproyectobatallanaval;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
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
    private Rectangle idBarcoSubmarino1;
    @FXML
    private Rectangle idBarcoSubmarino2;
    @FXML
    private Rectangle idBarcoSubmarino3;
    @FXML
    private Rectangle idBarcoSubmarino4;
    @FXML
    private Button btnVolverNiveles;
    @FXML
    private Rectangle idBarcoAcorazado;
    @FXML
    private Rectangle idBarcoCrucero1;
    @FXML
    private Rectangle idBarcoCrucero2;
    @FXML
    private Rectangle idBarcoDestructor1;
    @FXML
    private Rectangle idBarcoDestructor3;
    @FXML
    private Rectangle idBarcoDestructor2;
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
    private List<Rectangle> listaBarcosIniciales = new ArrayList<>();
    private List<Rectangle> barcosColocados = new ArrayList<>();

    private Tablero tableroComputadora;
    private Tablero tableroJugador;
    private Button[][] botonesComputadora = new Button[10][10];
    private Button[][] botonesJugador = new Button[10][10];
    @FXML
    private Label IdMensajeUsuario;
    @FXML
    private ToggleButton btnVertical;
    @FXML
    private ToggleButton btnHorizontal;
    private boolean orientacionHorizontal = true;
    private final Map<Rectangle, Integer> mapaBarcos = new HashMap<>();

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
        mapaBarcos.put(idBarcoSubmarino1, 1);
        mapaBarcos.put(idBarcoSubmarino2, 1);
        mapaBarcos.put(idBarcoSubmarino3, 1);
        mapaBarcos.put(idBarcoSubmarino4, 1);
        mapaBarcos.put(idBarcoDestructor1, 2);
        mapaBarcos.put(idBarcoDestructor2, 2);
        mapaBarcos.put(idBarcoDestructor3, 2);
        mapaBarcos.put(idBarcoCrucero1, 3);
        mapaBarcos.put(idBarcoCrucero2, 3);
        mapaBarcos.put(idBarcoAcorazado, 4);

        configurarArrastre(idBarcoSubmarino1, 1);
        configurarArrastre(idBarcoSubmarino2, 1);
        configurarArrastre(idBarcoSubmarino3, 1);
        configurarArrastre(idBarcoSubmarino4, 1);
        configurarArrastre(idBarcoDestructor1, 2);
        configurarArrastre(idBarcoDestructor2, 2);
        configurarArrastre(idBarcoDestructor3, 2);
        configurarArrastre(idBarcoCrucero1, 3);
        configurarArrastre(idBarcoCrucero2, 3);
        configurarArrastre(idBarcoAcorazado, 4);

        ToggleGroup grupoOrientacion = new ToggleGroup();
        btnHorizontal.setToggleGroup(grupoOrientacion);
        btnVertical.setToggleGroup(grupoOrientacion);

        btnHorizontal.setSelected(true);
        configurarTablero();

        grupoOrientacion.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == btnHorizontal) {
                orientacionHorizontal = true;
            } else if (newVal == btnVertical) {
                orientacionHorizontal = false;
            }
        });

        listaBarcosIniciales.addAll(List.of(
                idBarcoSubmarino1, idBarcoSubmarino2, idBarcoSubmarino3, idBarcoSubmarino4,
                idBarcoDestructor1, idBarcoDestructor2, idBarcoDestructor3,
                idBarcoCrucero1, idBarcoCrucero2,
                idBarcoAcorazado
        ));

        btnRevelarBarcos.setVisible(false);
        btnTerminarJuego.setVisible(false);
        IdTiempoTurno.setVisible(false);
        IdResultadoDisparo.setVisible(false);
    }

    @FXML
    private void ordenarBarcosAleatoriamente(ActionEvent event) {
        System.out.println("Colocando barcos aleatoriamente en el tablero del jugador...");

        tableroJugador.limpiarTablero();
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                botonesJugador[fila][columna].setStyle("-fx-background-color: lightblue;");
            }
        }

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
                if (colocado) {
                    String color = obtenerColorPorTamaño(barco.getTamaño());
                    for (int i = 0; i < barco.getTamaño(); i++) {
                        int filaActual = horizontal ? fila : fila + i;
                        int columnaActual = horizontal ? columna + i : columna;
                        botonesJugador[filaActual][columnaActual].setStyle("-fx-background-color: " + color + ";");
                    }
                } else {
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
        IdMensajeUsuario.setText("¡Tus barcos han sido colocados de forma aleatoria!");
    }

    @FXML
    private void OnButtonDeshacerColocacion(ActionEvent event) {
        System.out.println("Deshaciendo la colocación de los barcos...");
        tableroJugador.limpiarTablero();
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                botonesJugador[fila][columna].setStyle("-fx-background-color: lightblue;"); // Restaurar el estilo base
            }
        }
        for (Rectangle barco : barcosColocados) {
            barco.setVisible(true);
            barco.setDisable(false);
            Integer tamaño = mapaBarcos.get(barco);
            if (tamaño != null) {
                configurarArrastre(barco, tamaño);
            } else {
                System.out.println("Advertencia: No se encontró el barco en mapaBarcos.");
            }
        }
        barcosColocados.clear();
        IdMensajeUsuario.setText("¡Tablero reiniciado! Coloca tus barcos nuevamente.");
        System.out.println("Todos los barcos y el tablero han sido reiniciados.");
    }

    @FXML
    private void OnButtonRevelarBarcos(ActionEvent event) {
        System.out.println("Revelando ubicación de los barcos enemigos...");

        for (Barco barco : tableroComputadora.getBarcos()) {
            String color = obtenerColorPorTamaño(barco.getTamaño());
            for (int[] posicion : barco.getPosiciones()) {
                int fila = posicion[0];
                int columna = posicion[1];
                Button boton = botonesComputadora[fila][columna];
                boton.setStyle("-fx-background-color: " + color + ";");
            }
        }
        IdMensajeUsuario.setText("¡Barcos enemigos revelados!");
    }

    @FXML
    private void OnButtonTerminarPartida(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaInicio.fxml"));
            Parent root = loader.load();
            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.setTitle("Battleship - Menú Principal");
            nuevaVentana.setResizable(false);
            nuevaVentana.show();
        } catch (IOException e) {
            System.out.println("Error al cargar VentanaInicio.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    private void OnVolverPrecionado(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaNiveles.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Battleship - Juego");
            stage.show();

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
        tableroComputadora.imprimirBarcos();
        btnVolverNiveles.setVisible(false);
        btnComenzarBatalla.setVisible(false);
        btnOrdenAleatorio.setVisible(false);
        btnDeshacerTablero.setVisible(false);
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
        btnHorizontal.setVisible(false);
        btnVertical.setVisible(false);
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

                actualizarVistaTableroJugador();

                mostrarMensajeTemporal(null, "La computadora dispara: " + resultado, 2);

                if (verificarVictoria(tableroJugador)) {
                    mostrarMensajeTemporal("¡Perdiste! La computadora hundió todos tus barcos.", null, 2);
                }
            }
        }
        mostrarMensajeTemporal("¡Es tu turno!", null, 2);
    }

    private void mostrarMensajeTemporal(String mensajeUsuario, String mensajeComputadora, int duracionSegundos) {
        if (mensajeUsuario != null) {
            IdMensajeUsuario.setText(mensajeUsuario);
        }
        if (mensajeComputadora != null) {
            IdResultadoDisparo.setText(mensajeComputadora);
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(duracionSegundos), event -> {
            IdMensajeUsuario.setText("");
            IdResultadoDisparo.setText("");
        }));
        timeline.setCycleCount(1);
        timeline.play();
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

        String resultado = tableroComputadora.atacarCasilla(fila, columna);

        if (resultado.equals("Ya atacaste esta casilla.")) {
            mostrarMensajeTemporal("¡Intenta en otra posición, esta ya fue atacada!", null, 2);
            return;
        }
        mostrarMensajeTemporal("Tu disparo: " + resultado, null, 2);

        actualizarVistaTableroComputadora();

        if (verificarVictoria(tableroComputadora)) {
            mostrarMensajeTemporal("¡Felicidades, ganaste! Todos los barcos del oponente están hundidos.", null, 2);
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
                    boton.setDisable(true);
                    boton.setStyle("-fx-opacity: 1;");

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
            return false;
        }
        for (Barco barco : tablero.getBarcos()) {
            if (!barco.estaHundido()) {
                return false;
            }
        }
        return true;
    }

    private void configurarArrastre(Rectangle rectBarco, int tamaño) {
        mapaBarcos.put(rectBarco, tamaño);

        rectBarco.setOnDragDetected(event -> {
            Dragboard db = rectBarco.startDragAndDrop(TransferMode.COPY);

            ClipboardContent content = new ClipboardContent();
            content.putString("barco"); // Indicador simple
            db.setContent(content);

            event.consume();
        });
    }

    private void configurarTablero() {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                final int f = fila;
                final int c = columna;

                Button celda = new Button();
                celda.setStyle("-fx-background-color: lightgray;");
                celda.setMinSize(50, 50);

                botonesJugador[fila][columna] = celda;
                gridPaneJugador.add(celda, columna, fila);

                GridPane.setHalignment(celda, HPos.CENTER);
                GridPane.setValignment(celda, VPos.CENTER);

                celda.setOnDragOver(event -> {
                    if (event.getGestureSource() instanceof Rectangle && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.COPY);
                    }
                    event.consume();
                });

                celda.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    if (db.hasString() && db.getString().equals("barco")) {
                        Rectangle barcoOrigen = (Rectangle) event.getGestureSource();
                        Integer tamaño = mapaBarcos.get(barcoOrigen);
                        boolean horizontal = orientacionHorizontal;

                        if (tamaño == null) {
                            mostrarMensajeTemporal("No se reconoce el barco arrastrado", null, 2);
                            event.setDropCompleted(false);
                            event.consume();
                            return;
                        }

                        if (esPosicionValida(f, c, horizontal, tamaño)) {
                            colocarBarco(f, c, horizontal, tamaño, barcoOrigen);
                            actualizarVistaTableroJugador();
                        } else {
                            mostrarMensajeTemporal("Posición inválida", null, 2);
                        }

                        event.setDropCompleted(true);
                    } else {
                        event.setDropCompleted(false);
                    }
                    event.consume();
                });
            }
        }
    }

    private boolean esPosicionValida(int fila, int columna, boolean esHorizontal, int tamañoBarco) {

        if (fila < 0 || columna < 0 || fila >= 10 || columna >= 10) {
            return false; // Fuera de los límites
        }
        if (esHorizontal) {
            if (columna + tamañoBarco > 10) {
                return false; // El barco no cabe horizontalmente
            }
            for (int i = 0; i < tamañoBarco; i++) {
                if (tableroJugador.getCasillasOcupadas()[fila][columna + i]) {
                    return false;
                }
            }
        } else {
            if (fila + tamañoBarco > 10) {
                return false; // El barco no cabe verticalmente
            }
            for (int i = 0; i < tamañoBarco; i++) {
                if (tableroJugador.getCasillasOcupadas()[fila + i][columna]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void colocarBarco(int fila, int columna, boolean horizontal, int tamaño, Rectangle rectBarco) {

        if (rectBarco.getParent() instanceof Pane) {
            ((Pane) rectBarco.getParent()).getChildren().remove(rectBarco);
        }
        Barco barco = new Barco(tamaño);

        boolean colocado = tableroJugador.colocarBarco(barco, fila, columna, horizontal);

        if (!colocado) {
            mostrarMensajeTemporal("No se pudo colocar el barco", null, 2);
            return;
        }
        String color = obtenerColorPorTamaño(tamaño);
        for (int i = 0; i < tamaño; i++) {
            int f = horizontal ? fila : fila + i;
            int c = horizontal ? columna + i : columna;
            botonesJugador[f][c].setStyle("-fx-background-color: " + color + ";");
        }
        barcosColocados.add(rectBarco);
        mapaBarcos.remove(rectBarco);
    }

    private String obtenerColorPorTamaño(int tamaño) {
        if (tamaño == 1) {
            return "green";     // Submarino
        }
        if (tamaño == 2) {
            return "orange";         // Destructor
        }
        if (tamaño == 3) {
            return "purple";   // Crucero
        }
        if (tamaño == 4) {
            return "red";         // Acorazado
        }
        return "gray";
    }

    @FXML
    private void cambiarOrientacion() {
        if (btnHorizontal.isSelected()) {
            orientacionHorizontal = true;
        } else if (btnVertical.isSelected()) {
            orientacionHorizontal = false;
        }
    }
}
