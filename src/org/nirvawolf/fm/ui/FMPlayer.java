/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nirvawolf.fm.ui;

import java.util.Map;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import org.nirvawolf.douban.api.channel.Channel;
import org.nirvawolf.douban.api.song.Song;
import org.nirvawolf.fm.chain.BootChainNode;
import org.nirvawolf.fm.channels.ChannelManager;
import org.nirvawolf.fm.player.BasicPlayerAdaptor;
import org.nirvawolf.fm.song.SongManager;
import org.nirvawolf.fm.user.UserManager;

/**
 *
 * @author bruce
 */
public class FMPlayer 
extends BootChainNode 
implements BasicPlayerListener{

    private final BasicPlayerAdaptor player = new BasicPlayerAdaptor();
    private Song currentSong;
    private Channel currentChannel;
    private UserManager userManager;
    private ChannelManager channelManager;
    private SongManager songManager;

    public FMPlayer() {
        this.userManager = UserManager.sharedInstance();
        this.channelManager = ChannelManager.sharedInstance();
        this.songManager = SongManager.sharedInstance();
        player.addBasicPlayerListener(this);
    }

    @Override
    public void start() {
        
        this.currentChannel = songManager.getCurrentChannel();
        currentSong = songManager.getASong();
        
        if(currentSong == null){
            songManager.start();
        }
        
        this.player.open(currentSong.songUrl);
        this.player.play();
    }

    @Override
    public void opened(Object o, Map map) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void progress(int i, long l, byte[] bytes, Map map) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stateUpdated(BasicPlayerEvent bpe) {
        System.out.println(bpe.toString());
       
    }

    @Override
    public void setController(BasicController bc) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
