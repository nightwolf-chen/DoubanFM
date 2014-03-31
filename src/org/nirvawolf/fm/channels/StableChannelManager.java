/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nirvawolf.fm.channels;

import org.nirvawolf.douban.api.channel.ChannelUpdator;
import org.nirvawolf.douban.api.channel.StableChannelsUpdator;
import static org.nirvawolf.fm.chain.FMBootChainNode.restoreFromFile;

/**
 *
 * @author bruce
 */
public class StableChannelManager extends ChannelManager {

    private static StableChannelManager instance;

    private StableChannelManager(ChannelUpdator updator) {
        super(updator);
    }

    public static synchronized StableChannelManager sharedInstance() {
        if (instance == null) {

            instance = (StableChannelManager) restoreFromFile(StableChannelManager.class);

            if (instance == null) {
                ChannelUpdator updator = new StableChannelsUpdator();
                instance = new StableChannelManager(updator);
                updator.setDelegate(instance);
            }
        }
        return instance;
    }
}
