/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nirvawolf.fm.chain;

import java.io.Serializable;

/**
 *
 * @author bruce
 */
public class BootChainNodeTest extends FMBootChainNode implements Serializable{

    @Override
    public void start() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "BootChainNodeTest{" + '}';
    }
    
    
    public static void main(String[] args){
      BootChainNodeTest test = new BootChainNodeTest();
      test.serializeToFile();
      FMBootChainNode.restoreFromFile(BootChainNodeTest.class);
    }
    
}
