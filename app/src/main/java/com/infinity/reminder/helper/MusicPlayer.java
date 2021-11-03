package com.infinity.reminder.helper;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class MusicPlayer {

    private static MediaPlayer mediaPlayer;

    public static void playAudio(final Context context, final String url) {
        try{
            if(mediaPlayer != null){
                killMediaPlayer();
            }
            mediaPlayer = MediaPlayer.create(context, Uri.parse(url));
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    killMediaPlayer();
                }
            });
            mediaPlayer.start();
        }catch (Exception e){

        }

    }

    private static void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
