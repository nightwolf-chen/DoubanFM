/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nirvawolf.fm.ui;

import java.util.ArrayList;
import java.util.List;
import org.nirvawolf.douban.api.channel.Channel;
import org.nirvawolf.douban.api.song.Song;
import org.nirvawolf.fm.chain.FMBootChainNode;
import org.nirvawolf.fm.channels.ChannelManager;
import org.nirvawolf.fm.channels.StableChannelManager;
import org.nirvawolf.fm.player.BasicPlayerAdaptor;
import org.nirvawolf.fm.song.SongManager;
import org.nirvawolf.fm.user.UserManager;

/**
 *
 * @author bruce
 */
public class FMPlayer extends FMBootChainNode {

    private BasicPlayerAdaptor player;
    private Song currentSong;
    private Channel currentChannel;
    private UserManager userManager;
    private ChannelManager channelManager;
    private SongManager songManager;
    private List<FMPlayerListener> listeners = new ArrayList<FMPlayerListener>();

    public FMPlayer() {
        this.userManager = UserManager.sharedInstance();
        this.channelManager = StableChannelManager.sharedInstance();
        this.songManager = SongManager.sharedInstance();
    }

    @Override
    public void start() {
        this.play();
    }

    public void resume() {
        if (this.player != null) {
            this.player.resume();
        }
    }

    public void pause() {
        if (this.player != null) {
            this.player.pause();
        }
    }

    public void next() {
        this.play();
    }

    public void play() {
        this.currentChannel = songManager.getCurrentChannel();
        currentSong = songManager.getASong();

        if (this.player != null) {
            this.stop();
        }

        this.player = new BasicPlayerAdaptor();
        this.addListenersToPlayer(player);

        if (currentSong == null) {
            System.out.println("Got a null song , try to get a new song.");
            songManager.start();
        } else {
            this.player.open(currentSong.songUrl);
            this.player.play();
            this.notifyListeners();
        }
    }

    public void stop() {
        if (this.player != null) {
            this.player.stop();
            this.player = null;
        }
    }
    
    public void setVolume(double v){
        if(this.player != null){
            this.player.setVolume(v);
        }
    }

    public void setChannel(Channel channel) {
        this.songManager.setCurrentChannel(channel);
        songManager.start();
    }

    public void addListener(FMPlayerListener listener) {
        this.listeners.add(listener);
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public Channel getCurrentChannel() {
        return currentChannel;
    }

    private void notifyListeners() {
        for (FMPlayerListener l : this.listeners) {
            l.didFinishLoadSong();
        }
    }

    private void addListenersToPlayer(BasicPlayerAdaptor player) {
        for (FMPlayerListener listener : this.listeners) {
            this.player.addBasicPlayerListener(listener);
        }
    }
}
