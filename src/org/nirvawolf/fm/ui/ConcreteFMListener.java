/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nirvawolf.fm.ui;

import java.util.Map;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import org.nirvawolf.fm.chain.FMBootChain;

/**
 *
 * @author bruce
 */
public class ConcreteFMListener implements FMPlayerListener{

    @Override
    public void didFinishLoadSong() {
        FMBootChain.sharedInstance().serialize();
    }

    @Override
    public void opened(Object o, Map map) {
    }

    @Override
    public void progress(int i, long l, byte[] bytes, Map map) {
    }

    @Override
    public void stateUpdated(BasicPlayerEvent bpe) {
        if(bpe.getCode() == BasicPlayerEvent.STOPPED){
            
        }
    }

    @Override
    public void setController(BasicController bc) {
    }
    
}
