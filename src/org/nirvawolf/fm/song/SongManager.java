/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nirvawolf.fm.song;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import org.nirvawolf.douban.api.channel.Channel;
import org.nirvawolf.douban.api.song.RequestDelegate;
import org.nirvawolf.douban.api.song.RequestResponse;
import org.nirvawolf.douban.api.song.Song;
import org.nirvawolf.douban.api.song.SongRequest;
import org.nirvawolf.douban.api.song.SongRequestInfo;
import org.nirvawolf.douban.api.user.User;
import org.nirvawolf.fm.chain.FMBootChainNode;
import org.nirvawolf.fm.channels.ChannelManager;
import org.nirvawolf.fm.user.UserManager;

/**
 *
 * @author bruce
 */
public class SongManager
        extends FMBootChainNode
        implements RequestDelegate, Serializable {

    private static SongManager instance;

    private Queue<Song> songs = new LinkedList<Song>();
    private Channel currentChannel;

    public static synchronized SongManager sharedInstance() {
        if (instance == null) {
            
            instance = (SongManager) restoreFromFile(SongManager.class);
            if (instance == null) {
                instance = new SongManager();
            }
            
        }
        return instance;
    }

    private SongManager() {

    }

    public Song getASong() {

        return songs.poll();

    }

    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(Channel currentChannel) {
        this.currentChannel = currentChannel;
    }

    private boolean noMoreSongs() {
        if (songs.size() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void start() {

        this.currentChannel = ChannelManager.sharedInstance().getARandomChannel();

        SongRequestInfo info = new SongRequestInfo();

        info.channel = currentChannel;

        User user = UserManager.sharedInstance().getCurrentUser();
        info.user = user;

        if (this.noMoreSongs()) {
            info.type = SongRequestInfo.ActionType.NEW;
        } else {
            info.type = SongRequestInfo.ActionType.PLAYING;
        }

        SongRequest request = new SongRequest(this, info);
        request.attemptToRequest();
    }

    @Override
    public void didRecieveResponse(RequestResponse response) {

        if (response.isSuccess()) {
            this.songs.clear();
            songs.addAll(response.getSongs());
        }

        this.notifySubNodesReady();
    }

}
