package com.mycompany.primerproyectobatallanaval;
/**
 *
 * @author Randall AC
 */
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class EfectoSonido {

    public static void reproducir(String ruta) {
        try {
            URL mediaUrl = EfectoSonido.class.getResource(ruta);
            if (mediaUrl == null) {
                System.err.println("No se encontró el archivo de sonido: " + ruta);
                return;
            }
            Media sonido = new Media(mediaUrl.toString());
            MediaPlayer player = new MediaPlayer(sonido);
            player.setVolume(1.0);
            player.play();
            player.setOnEndOfMedia(player::dispose);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

