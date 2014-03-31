/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nirvawolf.fm.channels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.nirvawolf.douban.api.channel.Channel;
import org.nirvawolf.douban.api.channel.ChannelUpdateResult;
import org.nirvawolf.douban.api.channel.ChannelUpdator;
import org.nirvawolf.douban.api.channel.ChannelUpdatorDelegate;
import org.nirvawolf.douban.util.TimeTool;
import org.nirvawolf.fm.chain.FMBootChainNode;

/**
 *
 * @author bruce
 */
public class ChannelManager
        extends FMBootChainNode
        implements ChannelUpdatorDelegate, Serializable {

    private List<Channel> channels = new ArrayList();
    private final ChannelUpdator updator;
    private Map<String, String> channelCategories;
    private String updatetime;
    private final long updateGap = 1000 * 60 * 60 * 24 * 30;

    protected ChannelManager(ChannelUpdator updator) {
        updatetime = null;
        this.updator = updator;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public Channel getARandomChannel() {
        int index = (int) (Math.random() * this.channels.size());
        return channels.get(index);
    }

    @Override
    public void start() {

        if (!shouldUpdate()) {
            this.notifySubNodesReady();
        } else {
            updator.attemptToUpdate();
        }

    }

    @Override
    public void didRecieveLatestChannelRecords(ChannelUpdateResult result) {

        this.channelCategories = result.getCategory();
        this.channels = result.getChannels();
        this.notifySubNodesReady();

    }

    private boolean shouldUpdate() {

        if (updatetime == null) {
            return true;
        }

        TimeTool tt = new TimeTool();
        String uTime = updatetime;
        String cTime = tt.getCurrentTime();
        long gap = tt.calculateDiscance(uTime, cTime);

        if ((gap >= this.updateGap) || (gap <= 0)) {
            return true;
        } else {
            return false;
        }

    }

    public static void main(String[] args) {
//        ChannelManager mgr = new ChannelManager();
//        mgr.start();
    }
}
