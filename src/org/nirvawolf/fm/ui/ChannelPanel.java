/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nirvawolf.fm.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class ChannelPanel extends JPanel implements ActionListener {

    private Channel channel;
    private final FMPlayer player;

    public ChannelPanel(Channel channel,FMPlayer player) {
       
        this.channel = channel;
        this.player = player;
        
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
            button.addActionListener(this);
            
            this.add(button);

            if (channel.coverImgUrl != null) {
                String imageUrl = channel.coverImgUrl;

                BufferedImage image = ImageIO.read(new URL(imageUrl));
                ImageIcon icon = new ImageIcon(image);
                button.setIcon(icon);

            }

        }

        this.setBorder(new BevelBorder(0));
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.player.stop();
        this.player.setChannel(channel);
    }
}
