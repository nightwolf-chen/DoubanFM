/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nirvawolf.fm.chain;

import org.nirvawolf.fm.channels.ChannelManager;
import org.nirvawolf.fm.song.SongManager;
import org.nirvawolf.fm.user.UserManager;

/**
 *
 * @author bruce
 */
public class BootChain {

    public static enum MANAGER_TYEP {
        USER, CHANNEL, SONG, UIPLAYER
    };

    private UserManager userManager;
    private ChannelManager channelManager;
    private SongManager songManager;
    private BootChainNode uiPlayer;

    public BootChain(BootChainNode uiPlayer) {
        this.userManager = UserManager.sharedInstance();
        this.channelManager = ChannelManager.sharedInstance();
        this.songManager = SongManager.sharedInstance();
        this.uiPlayer = uiPlayer;

        userManager.addSubNode(channelManager);
        channelManager.addSubNode(songManager);
        songManager.addSubNode(uiPlayer);
    }

    public void start() {
        this.userManager.start();
    }

    public void start(MANAGER_TYEP type) {

        switch (type) {
            case USER: {
                this.userManager.start();
            }
            break;
                
            case CHANNEL: {
                this.channelManager.start();
            }
            break;
                
            case SONG: {
                this.songManager.start();
            }
            break;
                
            case UIPLAYER: {
                this.uiPlayer.start();
            }
            break;
        }
    }

}
