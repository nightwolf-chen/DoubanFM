/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nirvawolf.fm.chain;

import org.nirvawolf.fm.channels.ChannelManager;
import org.nirvawolf.fm.channels.DynamicChannelManager;
import org.nirvawolf.fm.channels.StableChannelManager;
import org.nirvawolf.fm.song.SongManager;
import org.nirvawolf.fm.ui.FMPlayer;
import org.nirvawolf.fm.user.UserManager;

/**
 *
 * @author bruce
 */
public class FMBootChain {

    private static FMBootChain instance;

    public static enum MANAGER_TYEP {

        USER, CHANNEL, SONG, UIPLAYER
    };

    private UserManager userManager;
    private ChannelManager stableChannelManager;
    private ChannelManager dynamicChannelManager;
    private SongManager songManager;
    private FMPlayer player;

    private FMBootChain() {

        this.userManager = UserManager.sharedInstance();
        this.stableChannelManager = StableChannelManager.sharedInstance();
        this.dynamicChannelManager = DynamicChannelManager.sharedInstance();
        this.songManager = SongManager.sharedInstance();

        this.player = new FMPlayer();

        userManager.removeAllSubNodes();
        stableChannelManager.removeAllSubNodes();
        dynamicChannelManager.removeAllSubNodes();
        songManager.removeAllSubNodes();

        userManager.addSubNode(stableChannelManager);
        stableChannelManager.addSubNode(songManager);
//        dynamicChannelManager.addSubNode(songManager);
        songManager.addSubNode(player);
    }

    public static synchronized FMBootChain sharedInstance() {
        if (instance == null) {
            instance = new FMBootChain();
        }
        return instance;
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
                this.stableChannelManager.start();
            }
            break;

            case SONG: {
                this.songManager.start();
            }
            break;

            case UIPLAYER: {
                this.player.start();
            }
            break;
        }
    }

    public void serialize() {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("saving managers...");
                userManager.serializeToFile();
                stableChannelManager.serializeToFile();
                dynamicChannelManager.serializeToFile();
                songManager.serializeToFile();
            }
        });
        
        thread.start();

    }

    public FMPlayer getPlayer() {
        return player;
    }

}
