package com.mycompany.primerproyectobatallanaval;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Randall AC
 */
public class Reproductor {

    private double volumen = 1.0;
    private static Reproductor instance;
    private static MediaPlayer mediaPlayer;

    // Constructor privado para evitar instancias directas
    private Reproductor(String audioPath, int type) {
        if (mediaPlayer == null) {
            Media media = new Media(audioPath);
            mediaPlayer = new MediaPlayer(media);
            if (type == 1) {
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            }
        }
    }

    public static Reproductor getInstance(String audioPath, int type) {
        if (audioPath == null || audioPath.isEmpty()) {
            return null;
        }
        if (instance == null) {
            instance = new Reproductor(audioPath, type);
        }
        return instance;
    }

    public void PlayOnBucle() {
        if (mediaPlayer != null && mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            mediaPlayer.play();
        }
    }

    public void Stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
            instance = null; // Resetear la instancia si se detiene
        }
    }

    public void Pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volumen);
        }
    }

    public static void detenerMusicaSiActiva() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
            instance = null;
        }
    }

    public void PlayUnaVez() {
        if (mediaPlayer != null && mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            mediaPlayer.setCycleCount(1);
            mediaPlayer.play();
        }
    }
}
