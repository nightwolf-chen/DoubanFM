/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nirvawolf.fm.channels;

import org.nirvawolf.douban.api.channel.ChannelUpdator;
import org.nirvawolf.douban.api.channel.DynamicChannelsUpdator;

/**
 *
 * @author bruce
 */
public class DynamicChannelManager extends ChannelManager {

    private static DynamicChannelManager instance;

    private DynamicChannelManager(ChannelUpdator updator) {
        super(updator);
    }

    public static synchronized DynamicChannelManager sharedInstance() {
        if (instance == null) {

            instance = (DynamicChannelManager) restoreFromFile(DynamicChannelManager.class);

            if (instance == null) {
                ChannelUpdator updator = new DynamicChannelsUpdator();
                instance = new DynamicChannelManager(updator);
                updator.setDelegate(instance);
            }
        }
        return instance;
    }
}
