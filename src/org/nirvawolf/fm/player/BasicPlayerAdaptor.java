/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nirvawolf.fm.player;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

/**
 *
 * @author bruce
 */
public class BasicPlayerAdaptor {

    private BasicPlayer player;
    private BasicController controller;

    public BasicPlayerAdaptor() {
        this.createPlayer();
    }

    private void createPlayer() {
        try {
            this.player = new BasicPlayer();
            this.controller = (BasicController) player;
            this.controller.setGain(0.5);
            this.controller.setPan(0);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(BasicPlayerAdaptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void open(String url) {
        try {
            this.controller.open(new URL(url));
        } catch (BasicPlayerException ex) {
            Logger.getLogger(BasicPlayerAdaptor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(BasicPlayerAdaptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void play() {
        try {
            this.controller.play();
        } catch (BasicPlayerException ex) {
            Logger.getLogger(BasicPlayerAdaptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pause() {
        try {
            this.controller.pause();
        } catch (BasicPlayerException ex) {
            Logger.getLogger(BasicPlayerAdaptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stop() {
        try {
            this.controller.stop();
        } catch (BasicPlayerException ex) {
            Logger.getLogger(BasicPlayerAdaptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void resume() {
        try {
            this.controller.resume();
        } catch (BasicPlayerException ex) {
            Logger.getLogger(BasicPlayerAdaptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void seek(long l) {
        try {
            this.controller.seek(l);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(BasicPlayerAdaptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addBasicPlayerListener(BasicPlayerListener listener) {
        this.player.addBasicPlayerListener(listener);
    }

    public void setVolume(double v) {
        try {
            this.controller.setGain(v);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(BasicPlayerAdaptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPan(double p) {
        try {
            this.controller.setPan(p);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(BasicPlayerAdaptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
