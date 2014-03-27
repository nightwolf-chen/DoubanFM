/*
 * To change this license header, choose License Headers ais Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template ais the editor.
 */
package org.nirvawolf.fm.play;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.nirvawolf.douban.api.channel.Channel;
import org.nirvawolf.douban.api.song.Request;
import org.nirvawolf.douban.api.song.RequestDelegate;
import org.nirvawolf.douban.api.song.RequestResponse;
import org.nirvawolf.douban.api.song.Song;
import org.nirvawolf.douban.api.song.SongRequest;
import org.nirvawolf.douban.api.song.SongRequestInfo;

/**
 *
 * @author bruce
 */
public class MusicPlayerTest implements RequestDelegate {

    public static enum PLAYSTATUS {

        PLAYING, PAUSED, STOPED, OPEN, CLOSED
    };

    private SourceDataLine sourceDataline = null;
    private Thread playThread = null;
    private AudioInputStream ais = null;
    private AudioInputStream dais = null;
    private PLAYSTATUS status = PLAYSTATUS.CLOSED;
    private Thread playIOThread = null;

    public void startToLoadSong() {

        SongRequestInfo info = new SongRequestInfo();
        info.type = SongRequestInfo.ActionType.NEW;

        Channel channel = new Channel();
        channel.channel_id = 5;

        info.channel = channel;

        Request songRequest = new SongRequest(this, info);
        songRequest.attemptToRequest();

    }

    @Override
    public void didRecieveResponse(RequestResponse response) {

        List<Song> songs = response.getSongs();
        final Song song = songs.get(0);

        this.setURL(song.songUrl);

        System.out.println("Playing song at:" + song.songUrl);

    }

    public void setURL(String url){
        prepareForURL(url);
    }
    
    public void play() {
        if (this.sourceDataline != null && this.status == PLAYSTATUS.OPEN) {
            
            PlayerIO playerIO = new PlayerIO(sourceDataline, dais);
            this.playIOThread = new Thread(playerIO);
            playIOThread.start();
            
        }else if(this.sourceDataline != null && this.status == PLAYSTATUS.PAUSED){
            this.sourceDataline.start();
            this.playIOThread.notify();
        }
    }

    public void pause() {
        
        if (this.sourceDataline != null) {
            
            try {
                sourceDataline.stop();
                this.playIOThread.wait();
                this.status = PLAYSTATUS.PAUSED;
            } catch (InterruptedException ex) {
                Logger.getLogger(MusicPlayerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    public void stop() {
        if (this.sourceDataline != null) {
            sourceDataline.flush();
            sourceDataline.stop();
            this.status = PLAYSTATUS.STOPED;
        }
    }
    
    public void close(){     
        try {
            
            this.closeDataLine();
            this.closeInputStreams();
            
        } catch (IOException ex) {
            Logger.getLogger(MusicPlayerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void closeDataLine(){
        if(this.sourceDataline != null){
            sourceDataline.close();
            sourceDataline = null;
        }
    }
    
    private void closeInputStreams() throws IOException{
       
        if(this.dais != null){        
                dais.close();
                dais = null;    
        }
        
        if(this.ais != null){
            ais.close();
            ais = null;
        }
    }
    
    public void prepareForURL(String urlStr) {
        try {

            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();

            this.ais = AudioSystem.getAudioInputStream(con.getInputStream());
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodedFormat = this.getDecodedFormate(baseFormat);
            this.dais = AudioSystem.getAudioInputStream(decodedFormat, ais);

            this.sourceDataline = this.createDataLine(decodedFormat);

            this.status = PLAYSTATUS.OPEN;

        } catch (Exception e) {
            //Handle exception.
            System.out.printf(e.getLocalizedMessage());
            this.sourceDataline.stop();
            this.sourceDataline.close();
        }
    }

    private AudioFormat getDecodedFormate(AudioFormat baseFormat) {

        AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false);

        return decodedFormat;

    }

    private SourceDataLine createDataLine(AudioFormat audioFormat) throws LineUnavailableException {
        SourceDataLine res = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        res = (SourceDataLine) AudioSystem.getLine(info);
        res.open(audioFormat);
        return res;
    }

    public static void main(String[] args) throws InterruptedException {

        MusicPlayerTest player = new MusicPlayerTest();

        player.startToLoadSong();
        player.play();
       
        Thread.currentThread().wait();

    }

}

class PlayerIO implements Runnable {

    private final SourceDataLine line;
    private final AudioInputStream din;

    public PlayerIO(SourceDataLine line, AudioInputStream din) {
        this.line = line;
        this.din = din;
    }

    @Override
    public void run() {
        try {
            this.playSourceDataLine();
        } catch (IOException ex) {
            Logger.getLogger(PlayerIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void playSourceDataLine() throws IOException {

        byte[] data = new byte[4096];

        synchronized (din) {

            if (line != null) {

                line.start();
                int nBytesRead = 0, nBytesWritten = 0;
                while (nBytesRead != -1) {
                    nBytesRead = din.read(data, 0, data.length);
                    if (nBytesRead != -1) {
                        nBytesWritten = line.write(data, 0, nBytesRead);
                    }
                }

            }

        }
        
    }
}
