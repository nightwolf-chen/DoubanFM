/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nirvawolf.fm.play;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
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
public class BasicPlayerTestor implements RequestDelegate, BasicPlayerListener {

//    private Player audioPlayer = null;//建立一个播放接口  
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

        try {
            List<Song> songs = response.getSongs();
            Song song = songs.get(0);
            
            System.out.println(song.songUrl);
            
            BasicPlayer player = new BasicPlayer();
            BasicController playerController = (BasicController)player;
            player.addBasicPlayerListener(this);
            
            URL url = new URL(song.songUrl);
            playerController.open(url);
            playerController.play();
            playerController.setGain(0.8);
            playerController.setPan(0);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(BasicPlayerTestor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | BasicPlayerException ex) {
            Logger.getLogger(BasicPlayerTestor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws MalformedURLException, Exception {

        BasicPlayerTestor Player = new BasicPlayerTestor();
        Player.startToLoadSong();
    }

    @Override
    public void opened(Object o, Map map) {
       System.out.println("opened:"+map.toString());
    }

    @Override
    public void progress(int i, long l, byte[] bytes, Map map) {
        System.out.println("progress:"+map.toString());
    }

    @Override
    public void stateUpdated(BasicPlayerEvent bpe) {
        System.out.println("stateUpdated:"+bpe.toString());
    }

    @Override
    public void setController(BasicController bc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
