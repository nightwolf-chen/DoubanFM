/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nirvawolf.fm.ui;

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
public class FMPlayer extends BootChainNode {

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

}
