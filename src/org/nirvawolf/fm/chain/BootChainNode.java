/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nirvawolf.fm.chain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bruce
 */
public abstract class BootChainNode {
    
    private List<BootChainNode> subNodes = new ArrayList<BootChainNode>();
    
    public abstract void start();
    
    public void notifySubNodesReady(){
        for(BootChainNode node : this.subNodes){
            node.start();
        }
    }
    
    public void addSubNode(BootChainNode node){
        this.subNodes.add(node);
    }
    
    public void removeSubNode(BootChainNode node){
        this.subNodes.remove(node);
    }
}
