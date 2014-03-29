/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nirvawolf.fm.ui;

import javazoom.jlgui.basicplayer.BasicPlayerListener;

/**
 *
 * @author bruce
 */
public interface FMPlayerListener extends BasicPlayerListener{
    public void didFinishLoadSong();
}
