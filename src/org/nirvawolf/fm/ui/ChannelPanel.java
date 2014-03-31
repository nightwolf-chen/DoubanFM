/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nirvawolf.fm.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import org.nirvawolf.douban.api.channel.Channel;

/**
 *
 * @author bruce
 */
public class ChannelPanel extends JPanel {

    private Channel channel;

    public ChannelPanel(Channel channel) {
        this.channel = channel;

        try {
            initComponents();
        } catch (IOException ex) {
            Logger.getLogger(ChannelPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initComponents() throws MalformedURLException, IOException {

        this.setLayout(new GridLayout(1, 2));

        int width = 40, height = 20;

        if (channel.chineseName != null) {
            
            JButton button = new JButton(channel.chineseName);
            button.setSize(width, height);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setHorizontalTextPosition(SwingConstants.RIGHT);
            
            this.add(button);

            if (channel.coverImgUrl != null) {
                String imageUrl = channel.coverImgUrl;

                BufferedImage image = ImageIO.read(new URL(imageUrl));
                ImageIcon icon = new ImageIcon(image);

//                JLabel label = new JLabel(icon);
//                this.add(label);
//                width = image.getWidth();
                button.setIcon(icon);

            }

        }

        this.setBorder(new BevelBorder(0));
//        
//        if(channel.intro != null){
//            JLabel label = new JLabel();
//            label.setText("简介："+channel.intro);
//            label.setSize(width, height);
//            this.add(label);
//        }

    }
}
