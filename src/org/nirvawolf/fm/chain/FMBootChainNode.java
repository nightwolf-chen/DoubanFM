/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nirvawolf.fm.chain;

import java.util.ArrayList;
import java.util.List;
import org.nirvawolf.douban.util.SerializatioinHelper;

/**
 *
 * @author bruce
 */
public abstract class FMBootChainNode {
    
    private List<FMBootChainNode> subNodes = new ArrayList<FMBootChainNode>();
    
    public abstract void start();
    
    public void serializeToFile(){
        SerializatioinHelper.serializeToFile(this, this.getClass().getName());
        System.out.println("saving:"+this.getClass().getName());
    }
    
    public static Object restoreFromFile(Class aClass){
        System.out.println("restoring:"+aClass.getName());
        return SerializatioinHelper.restoreObjectFromFile(aClass.getName());
    }
    
    
    public void notifySubNodesReady(){
        for(FMBootChainNode node : this.subNodes){
            node.start();
        }
    }
    
    public void addSubNode(FMBootChainNode node){
        this.subNodes.add(node);
    }
    
    public void removeSubNode(FMBootChainNode node){
        this.subNodes.remove(node);
    }
    
    public void removeAllSubNodes(){
        this.subNodes.clear();
    }
    
}
