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
import org.nirvawolf.douban.api.channel.DynamicChannelsUpdator;
import org.nirvawolf.douban.api.channel.StableChannelsUpdator;
import org.nirvawolf.douban.util.TimeTool;
import org.nirvawolf.fm.chain.FMBootChainNode;

/**
 *
 * @author bruce
 */
public class ChannelManager 
extends FMBootChainNode
implements ChannelUpdatorDelegate,Serializable
{

    private static ChannelManager instance;
    
    private final List<Channel> channels = new ArrayList();
    private final List<ChannelUpdator> updators = new ArrayList<ChannelUpdator>();
    private Map<String,String> channelCategories;
    
    private int currentUpdatorIndex;
    private String updatetime;
    private final long updateGap = 1000 * 60 * 60 * 24 * 7;
    
    public static synchronized ChannelManager sharedInstance(){
        
        if(instance == null){
            
            instance = (ChannelManager) restoreFromFile(ChannelManager.class);
           
            if(instance == null){
                instance = new ChannelManager();
            }
        }
        
        return instance;
    }
    
    private ChannelManager(){
        updatetime = null;
        updators.add(new StableChannelsUpdator(this));
        updators.add(new DynamicChannelsUpdator(this));
        currentUpdatorIndex = 0;
    }

    public List<Channel> getChannels() {
        return channels;
    }
    
    public Channel getARandomChannel(){
        int index = (int)(Math.random() * this.channels.size());
        return channels.get(index);
    }
    
    
    
    @Override
    public void start() {
        
        if(!shouldUpdate()){
            this.notifySubNodesReady();
            return ;
        }
        
        if(this.updators.size() > 0 && currentUpdatorIndex < this.updators.size()){
            updators.get(currentUpdatorIndex).attemptToUpdate();
        }
    }

    @Override
    public void didRecieveLatestChannelRecords(ChannelUpdateResult result) {
       
       currentUpdatorIndex++;
       
       
       this.channelCategories = result.getCategory();
       this.channels.addAll(result.getChannels());
       
       if(currentUpdatorIndex >= this.updators.size()){
           updatetime = new TimeTool().getCurrentTime();
           this.notifySubNodesReady();
//           System.out.print(this.channels.toString());
       }else{
           this.start();
       }
       
    }
    
    
    private boolean shouldUpdate(){
        
        if(updatetime == null){
            return true;
        }
        
        TimeTool tt = new TimeTool();
        long gap = tt.calculateDiscance(updatetime, tt.getCurrentTime());
        
        if(gap > 0 && gap < this.updateGap){
            return false;
        }
        
        return true;
    }
    
    public static void main(String[] args){
        ChannelManager mgr = new ChannelManager();
        mgr.start();
    }
}
