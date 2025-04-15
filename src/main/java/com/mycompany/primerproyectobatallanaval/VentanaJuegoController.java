package com.mycompany.primerproyectobatallanaval;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author RANDALL AC
 */
public class VentanaJuegoController implements Initializable {
    private Stage stagePrincipal;
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
    private int ultimoDisparoExitosoFila = -1;
    private int ultimoDisparoExitosoCol = -1;
    private List<int[]> direccionesPendientes = new ArrayList<>();
    private int primerImpactoFila = -1;
    private int primerImpactoCol = -1;
    private boolean intentandoDireccionOpuesta = false;
    private int[] direccionActual = null;
    private boolean direccionConfirmada = false;
    private boolean intentoAmbasDirecciones = false;
    private Timeline temporizadorTurno;
    private int tiempoRestante;
    @FXML
    private Label txtMensajePista;
    private List<Barco> barcosMarcadosComoHundidosComputadora = new ArrayList<>();
    private final List<Barco> barcosMarcadosComoHundidosJugador = new ArrayList<>();

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
                    if (tableroComputadora.getCasillasAtacadas()[finalFila][finalColumna]) {
                        mostrarMensajeTemporal("¡Ya disparaste aquí!", null, 2);
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
        gridPaneComputadora.setDisable(true);
        txtMensajePista.setVisible(false);
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
        int[] tamaños = {4, 3, 2, 1};
        int[] barcosPorTamaño = {1, 2, 3, 4};
        for (int i = 0; i < tamaños.length; i++) {
            int tamaño = tamaños[i];
            int barcosColocados = 0;
            while (barcosColocados < barcosPorTamaño[i]) {
                int fila = (int) (Math.random() * 10);
                int columna = (int) (Math.random() * 10);
                boolean horizontal = Math.random() < 0.5;
                Barco barco = new Barco(tamaño);
                if (tableroJugador.verificarEspacio(barco.getTamaño(), fila, columna, horizontal)) {
                    boolean espacioSeguro = true;
                    for (int j = 0; j < barco.getTamaño(); j++) {
                        int f = horizontal ? fila : fila + j;
                        int c = horizontal ? columna + j : columna;
                        if (!tableroJugador.hayEspacioAlrededor(f, c)) {
                            espacioSeguro = false;
                            break;
                        }
                    }
                    if (espacioSeguro && tableroJugador.colocarBarco(barco, fila, columna, horizontal)) {
                        String color = obtenerColorPorTamaño(barco.getTamaño());
                        for (int j = 0; j < barco.getTamaño(); j++) {
                            int filaActual = horizontal ? fila : fila + j;
                            int columnaActual = horizontal ? columna + j : columna;
                            botonesJugador[filaActual][columnaActual].setStyle("-fx-background-color: " + color + ";");
                        }
                        barcosColocados++;
                    }
                }
            }
        }

        System.out.println("Barcos colocados en el tablero del jugador:");
        tableroJugador.imprimirBarcos();
        IdMensajeUsuario.setText("¡Tus barcos han sido colocados de forma aleatoria!");
        for (Rectangle barco : mapaBarcos.keySet()) {
            barco.setVisible(false);
            barco.setDisable(true);
            if (!barcosColocados.contains(barco)) {
                barcosColocados.add(barco);
            }
        }
        btnOrdenAleatorio.setDisable(true);
    }

    @FXML
    private void OnButtonDeshacerColocacion(ActionEvent event
    ) {
        System.out.println("Deshaciendo la colocación de los barcos...");
        tableroJugador.limpiarTablero();
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                botonesJugador[fila][columna].setStyle("-fx-background-color: lightblue;");
            }
        }
        for (Rectangle barco : mapaBarcos.keySet()) {
            barco.setVisible(true);
            barco.setDisable(false);
            Integer tamaño = mapaBarcos.get(barco);
            if (tamaño != null) {
                configurarArrastre(barco, tamaño);
            }
        }
        barcosColocados.clear();
        IdMensajeUsuario.setText("¡Tablero reiniciado! Coloca tus barcos nuevamente.");
        System.out.println("Todos los barcos y el tablero han sido reiniciados.");
        btnOrdenAleatorio.setDisable(false);
    }

    @FXML
    private void OnButtonRevelarBarcos(ActionEvent event
    ) {
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
    private void OnButtonTerminarPartida(ActionEvent event
    ) {
        try {
            Reproductor.detenerMusicaSiActiva();
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
    private void OnVolverPrecionado(ActionEvent event
    ) {
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
        Reproductor.detenerMusicaSiActiva();
        Reproductor musicaBatalla = Reproductor.getInstance(getClass().getResource("/Musica/BatallaNavalMusic.mp3").toExternalForm(), 1);
        musicaBatalla.PlayOnBucle();
        musicaBatalla.setVolumen(0.4);
        if (tableroJugador.getBarcos().size() != tableroJugador.getMaxBarcos()) {
            IdMensajeUsuario.setText("¡Debes colocar todos tus barcos antes de comenzar!");
            return;
        }
        tableroComputadora.limpiarTablero();
        int[] tamaños = {4, 3, 2, 1}; // Acorazados, Cruceros, Destructores, Submarinos
        int[] barcosPorTamaño = {1, 2, 3, 4}; // Cantidad por tipo de barco
        for (int i = 0; i < tamaños.length; i++) {
            int tamaño = tamaños[i];
            int barcosColocados = 0;
            while (barcosColocados < barcosPorTamaño[i]) {
                int intentos = 0;
                boolean colocado = false;
                while (!colocado && intentos < 100) { // Limitar los intentos
                    intentos++;
                    int fila = (int) (Math.random() * 10);
                    int columna = (int) (Math.random() * 10);
                    boolean horizontal = Math.random() < 0.5;
                    Barco barco = new Barco(tamaño);
                    if (tableroComputadora.esPosicionValida(fila, columna, horizontal, barco.getTamaño())) {
                        colocado = tableroComputadora.colocarBarco(barco, fila, columna, horizontal);
                    }
                }
                if (!colocado) {
                    System.err.println("No hay suficiente espacio para colocar el barco de tamaño " + tamaño);
                    break;
                } else {
                    barcosColocados++;
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
        gridPaneComputadora.setDisable(false);
        IdMensajeUsuario.setText("¡Barcos colocados! Turno del jugador.");
        iniciarTemporizadorTurno();
    }

    private void turnoComputadora() {
        detenerTemporizador();
        System.out.println("Turno de la computadora...");
        IdMensajeUsuario.setText("Turno de la computadora...");
        setTableroComputadoraActivo(false);
        PauseTransition esperaAntesDeDisparo = new PauseTransition(Duration.seconds(1.5));
        esperaAntesDeDisparo.setOnFinished(e -> {
            boolean disparoRealizado = false;
            while (!disparoRealizado) {
                int fila = -1;
                int col = -1;
                if (ultimoDisparoExitosoFila != -1) {
                    if (direccionConfirmada && direccionActual != null) {
                        // Verificar que la siguiente casilla en dirección actual no haya sido atacada
                        int siguienteFila = ultimoDisparoExitosoFila + direccionActual[0];
                        int siguienteCol = ultimoDisparoExitosoCol + direccionActual[1];
                        if (esPosicionValida(siguienteFila, siguienteCol)) {
                            if (!tableroJugador.getCasillasAtacadas()[siguienteFila][siguienteCol]) {
                                fila = siguienteFila;
                                col = siguienteCol;
                            } else {
                                // Si ya fue atacada, volver al primer impacto e intentar dirección opuesta
                                if (!intentandoDireccionOpuesta && primerImpactoFila != -1) {
                                    ultimoDisparoExitosoFila = primerImpactoFila;
                                    ultimoDisparoExitosoCol = primerImpactoCol;
                                    direccionActual = new int[]{-direccionActual[0], -direccionActual[1]};
                                    direccionConfirmada = true;
                                    intentandoDireccionOpuesta = true;
                                    continue;
                                } else {
                                    // Si ya se intentó dirección opuesta, reiniciar
                                    reiniciarEstadoDisparo();
                                    continue;
                                }
                            }
                        } else {
                            // Fuera de los límites del tablero, intentar dirección opuesta
                            if (!intentandoDireccionOpuesta && primerImpactoFila != -1) {
                                ultimoDisparoExitosoFila = primerImpactoFila;
                                ultimoDisparoExitosoCol = primerImpactoCol;
                                direccionActual = new int[]{-direccionActual[0], -direccionActual[1]};
                                direccionConfirmada = true;
                                intentandoDireccionOpuesta = true;
                                continue;
                            } else {
                                reiniciarEstadoDisparo();
                                continue;
                            }
                        }
                    } else if (!direccionesPendientes.isEmpty()) {
                        int[] dir = direccionesPendientes.remove(0);
                        int nuevaFila = ultimoDisparoExitosoFila + dir[0];
                        int nuevaCol = ultimoDisparoExitosoCol + dir[1];

                        if (esPosicionValida(nuevaFila, nuevaCol)
                                && !tableroJugador.getCasillasAtacadas()[nuevaFila][nuevaCol]) {
                            fila = nuevaFila;
                            col = nuevaCol;
                            direccionActual = dir;
                        }
                    } else {
                        // Volver al primer impacto e intentar dirección opuesta
                        if (direccionActual != null && primerImpactoFila != -1) {
                            fila = primerImpactoFila - direccionActual[0];
                            col = primerImpactoCol - direccionActual[1];
                            direccionActual = new int[]{-direccionActual[0], -direccionActual[1]};
                            direccionConfirmada = true;
                            intentandoDireccionOpuesta = true;
                            ultimoDisparoExitosoFila = primerImpactoFila;
                            ultimoDisparoExitosoCol = primerImpactoCol;
                        } else {
                            reiniciarEstadoDisparo();
                            fila = (int) (Math.random() * 10);
                            col = (int) (Math.random() * 10);
                        }
                    }
                } else {
                    // Disparo aleatorio
                    fila = (int) (Math.random() * 10);
                    col = (int) (Math.random() * 10);
                }
                if (esPosicionValida(fila, col) && !tableroJugador.getCasillasAtacadas()[fila][col]) {
                    String resultado = tableroJugador.atacarCasilla(fila, col);
                    disparoRealizado = true;
                    if (resultado.equals("¡Averiado!")) {
                        if (ultimoDisparoExitosoFila == -1) {
                            // Primer impacto
                            primerImpactoFila = fila;
                            primerImpactoCol = col;
                            direccionesPendientes.clear();
                            direccionesPendientes.add(new int[]{-1, 0}); // arriba
                            direccionesPendientes.add(new int[]{1, 0});  // abajo
                            direccionesPendientes.add(new int[]{0, -1}); // izquierda
                            direccionesPendientes.add(new int[]{0, 1});  // derecha
                            direccionActual = null;
                            direccionConfirmada = false;
                            intentandoDireccionOpuesta = false;
                        } else if (!direccionConfirmada && direccionActual != null) {
                            // Segundo impacto, confirmar dirección
                            direccionConfirmada = true;
                            intentandoDireccionOpuesta = false;
                        }
                        ultimoDisparoExitosoFila = fila;
                        ultimoDisparoExitosoCol = col;
                    } else if (resultado.equals("¡Hundido!")) {
                        for (Barco barco : tableroComputadora.getBarcos()) {
                            if (barco.estaHundido() && !barcosMarcadosComoHundidosComputadora.contains(barco)) {
                                marcarAguaAlrededorDelBarco(barco, false);
                                barcosMarcadosComoHundidosComputadora.add(barco);
                            }
                        }
                        for (Barco barco : tableroJugador.getBarcos()) {
                            if (barco.estaHundido() && !barcosMarcadosComoHundidosJugador.contains(barco)) {
                                marcarAguaAlrededorDelBarco(barco, true);
                                barcosMarcadosComoHundidosJugador.add(barco);
                            }
                        }
                        reiniciarEstadoDisparo();
                    } else {
                        // Agua
                        if (direccionConfirmada && primerImpactoFila != -1 && !intentandoDireccionOpuesta) {
                            ultimoDisparoExitosoFila = primerImpactoFila;
                            ultimoDisparoExitosoCol = primerImpactoCol;
                            direccionActual = new int[]{-direccionActual[0], -direccionActual[1]};
                            direccionConfirmada = true;
                            intentandoDireccionOpuesta = true;
                        } else if (direccionConfirmada && intentandoDireccionOpuesta) {
                            reiniciarEstadoDisparo();
                        } else {
                            direccionActual = null;
                        }
                    }
                    actualizarVistaTableroJugador();
                    IdResultadoDisparo.setText("La computadora dispara: " + resultado);
                    if (evaluarFinDelJuego()) {
                        return;
                    }
                    PauseTransition esperaAntesDeJugador = new PauseTransition(Duration.seconds(1.5));
                    esperaAntesDeJugador.setOnFinished(ev -> {
                        IdResultadoDisparo.setText("");
                        IdMensajeUsuario.setText("¡Es tu turno!");
                        setTableroComputadoraActivo(true);
                        iniciarTemporizadorTurno();
                        IdTiempoTurno.setVisible(true);
                    });
                    esperaAntesDeJugador.play();
                }
            }
        });
        esperaAntesDeDisparo.play();
    }

    private boolean esPosicionValida(int fila, int col) {
        return fila >= 0 && fila < 10 && col >= 0 && col < 10;
    }

    private void reiniciarEstadoDisparo() {
        ultimoDisparoExitosoFila = -1;
        ultimoDisparoExitosoCol = -1;
        direccionActual = null;
        direccionConfirmada = false;
        primerImpactoFila = -1;
        primerImpactoCol = -1;
        intentandoDireccionOpuesta = false;
        direccionesPendientes.clear();
    }

    private void setTableroComputadoraActivo(boolean activo) {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                botonesComputadora[fila][columna].setDisable(!activo);
            }
        }
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

    private void activarMensajesPista() {
        System.out.println("Mensajes de pistas activados para el nivel FÁCIL.");
        txtMensajePista.setVisible(true);
    }

    private void iniciarTemporizadorTurno() {
        if (temporizadorTurno != null) {
            temporizadorTurno.stop();
        }
        int nivel = UsuarioData.getNivel();
        tiempoRestante = switch (nivel) {
            case 1 -> {
                activarMensajesPista();
                yield 15; //Devuelve el valor 15 para el tiempo restante 
            }
            case 2 ->
                10;
            case 3 ->
                5;
            default ->
                10;
        };
        IdTiempoTurno.setText("Tiempo: " + tiempoRestante);
        IdTiempoTurno.setVisible(true);
        temporizadorTurno = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            tiempoRestante--;
            IdTiempoTurno.setText("Tiempo: " + tiempoRestante);
            if (tiempoRestante <= 0) {
                temporizadorTurno.stop();
                disparoAleatorioJugador();
            }
        }));
        temporizadorTurno.setCycleCount(Timeline.INDEFINITE);
        temporizadorTurno.play();
    }

    private void detenerTemporizador() {
        if (temporizadorTurno != null) {
            temporizadorTurno.stop();
        }
        IdTiempoTurno.setVisible(false);
    }

    private void dispararEnTableroComputadora(int fila, int columna) {
        System.out.println("Disparo del usuario en fila: " + fila + ", columna: " + columna);
        String resultado = tableroComputadora.atacarCasilla(fila, columna);
        if (resultado.contains("Ya atacaste")) {
            mostrarMensajeTemporal("¡Intenta en otra posición, esta ya fue atacada!", null, 2);
            return;
        }
        if (resultado.equals("¡Hundido!")) {
            for (Barco barco : tableroComputadora.getBarcos()) {
                if (barco.estaHundido() && !barcosMarcadosComoHundidosComputadora.contains(barco)) {
                    marcarAguaAlrededorDelBarco(barco, false);
                    barcosMarcadosComoHundidosComputadora.add(barco);
                }
            }
        }
        IdResultadoDisparo.setText("Tú disparo es: " + resultado);
        int distancia = calcularDistanciaMinima(fila, columna);
        mostrarMensajePista(distancia);
        actualizarVistaTableroComputadora();
        evaluarFinDelJuego();
        if (temporizadorTurno != null) {
            temporizadorTurno.stop();
            IdTiempoTurno.setVisible(false);
        }
        turnoComputadora();
    }

    private int calcularDistanciaMinima(int fila, int columna) {
        int distanciaMinima = Integer.MAX_VALUE;
        for (Barco barco : tableroComputadora.getBarcos()) {
            for (int[] posicion : barco.getPosiciones()) {
                int distanciaFila = Math.abs(posicion[0] - fila);
                int distanciaColumna = Math.abs(posicion[1] - columna);
                int distancia = distanciaFila + distanciaColumna;
                distanciaMinima = Math.min(distanciaMinima, distancia);
            }
        }
        return distanciaMinima;
    }

    private void disparoAleatorioJugador() {
        Random random = new Random();
        int fila, columna;
        do {
            fila = random.nextInt(10);
            columna = random.nextInt(10);
        } while (tableroComputadora.getCasillasAtacadas()[fila][columna]);

        System.out.println("Disparo automático del jugador en (" + fila + ", " + columna + ")");
        dispararEnTableroComputadora(fila, columna);
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
                    boton.setDisable(false);
                    boton.setStyle("-fx-background-color: lightgray;"); // Casilla no atacada
                }
                int finalFila = fila;
                int finalColumna = columna;
                boton.setOnMouseClicked(event -> {
                    if (boton.isDisable()) {
                        mostrarMensajeTemporal("¡Ya atacaste esta casilla, intenta con otra!", null, 2);
                    } else {
                        dispararEnTableroComputadora(finalFila, finalColumna);
                    }
                });
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

    private void marcarAguaAlrededorDelBarco(Barco barco, boolean esJugador) {
        int nivel = UsuarioData.getNivel();
        if ((nivel == 1 && !esJugador) || (nivel == 3 && esJugador) || (nivel == 2)) {
            boolean[][] casillasAtacadas = esJugador
                    ? tableroJugador.getCasillasAtacadas()
                    : tableroComputadora.getCasillasAtacadas();
            int[][] posiciones = barco.getPosiciones();
            for (int[] posicion : posiciones) {
                int fila = posicion[0];
                int columna = posicion[1];
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int nuevaFila = fila + i;
                        int nuevaColumna = columna + j;
                        if (nuevaFila >= 0 && nuevaFila < 10 && nuevaColumna >= 0 && nuevaColumna < 10) {
                            boolean esParteDelBarco = false;
                            for (int[] parte : posiciones) {
                                if (parte[0] == nuevaFila && parte[1] == nuevaColumna) {
                                    esParteDelBarco = true;
                                    break;
                                }
                            }
                            if (!esParteDelBarco) {
                                casillasAtacadas[nuevaFila][nuevaColumna] = true;
                            }
                        }
                    }
                }
            }
        }
    }

    private void mostrarMensajePista(int distancia) {
        if (distancia == 0) {
            txtMensajePista.setText(""); // Sin mensaje si acierta
        } else if (distancia == 1) {
            txtMensajePista.setText("¡Muy cerca!"); // Directo
        } else if (distancia <= 3) {
            txtMensajePista.setText("¡Cerca!"); // Rango de 2-3 celdas
        } else {
            txtMensajePista.setText("¡Lejos!"); // Más de 3 celdas
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            txtMensajePista.setText("");
        }));
        timeline.setCycleCount(1);
        timeline.play();
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
            content.putString("barco");
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
            return false;
        }
        if ((esHorizontal && columna + tamañoBarco > 10) || (!esHorizontal && fila + tamañoBarco > 10)) {
            return false;
        }
        int filaInicio = Math.max(0, fila - 1);
        int filaFin = Math.min(9, (esHorizontal ? fila : fila + tamañoBarco - 1) + 1);
        int colInicio = Math.max(0, columna - 1);
        int colFin = Math.min(9, (esHorizontal ? columna + tamañoBarco - 1 : columna) + 1);

        for (int f = filaInicio; f <= filaFin; f++) {
            for (int c = colInicio; c <= colFin; c++) {
                if (tableroJugador.getCasillasOcupadas()[f][c]) {
                    return false; // Hay un barco o está muy cerca
                }
            }
        }
        return true;
    }

    private void colocarBarco(int fila, int columna, boolean horizontal, int tamaño, Rectangle rectBarco) {
        rectBarco.setVisible(false);
        rectBarco.setDisable(true);
        barcosColocados.add(rectBarco);
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
    }

    private String obtenerColorPorTamaño(int tamaño) {
        if (tamaño == 1) {
            return "green";  // Submarino
        }
        if (tamaño == 2) {
            return "orange"; // Destructor
        }
        if (tamaño == 3) {
            return "purple"; // Crucero
        }
        if (tamaño == 4) {
            return "red";    // Acorazado
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

    public enum ResultadoJuego {
        VICTORIA_JUGADOR, VICTORIA_COMPUTADORA, EMPATE, CONTINUAR
    }

    private ResultadoJuego verificarEstadoFinal() {
        boolean jugadorGana = verificarVictoria(tableroComputadora);
        boolean computadoraGana = verificarVictoria(tableroJugador);
        int tirosJugador = tableroComputadora.getCantidadDisparos();
        int tirosComputadora = tableroJugador.getCantidadDisparos();
        if (jugadorGana && computadoraGana) {
            return ResultadoJuego.EMPATE;
        }
        if (jugadorGana) {
            if (tirosComputadora < tirosJugador) {
                return ResultadoJuego.CONTINUAR;
            }
            return ResultadoJuego.VICTORIA_JUGADOR;
        }
        if (computadoraGana) {
            if (tirosJugador < tirosComputadora) {
                return ResultadoJuego.CONTINUAR;
            }
            return ResultadoJuego.VICTORIA_COMPUTADORA;
        }
        return ResultadoJuego.CONTINUAR;
    }

    private boolean evaluarFinDelJuego() {
        ResultadoJuego resultado = verificarEstadoFinal();
        switch (resultado) {
            case VICTORIA_JUGADOR:
                Reproductor.detenerMusicaSiActiva();
                cambiarAEscenaFinal("ganador");
                return true;
            case VICTORIA_COMPUTADORA:
                Reproductor.detenerMusicaSiActiva();
                cambiarAEscenaFinal("perdedor");
                return true;
            case EMPATE:
                Reproductor.detenerMusicaSiActiva();
                cambiarAEscenaFinal("empate");
                return true;
            case CONTINUAR:
            default:
                return false;
        }
    }

    private void cambiarAEscenaFinal(String resultado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaFinalJuego.fxml"));
            Parent root = loader.load();
            VentanaFinalJuegoController controlador = loader.getController();
            controlador.configurarFinal(resultado);
            Stage stage = stagePrincipal;
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStagePrincipal(Stage stage) {
        this.stagePrincipal = stage;
    }
}
