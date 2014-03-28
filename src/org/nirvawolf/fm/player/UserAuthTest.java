package org.nirvawolf.fm.player;


import org.nirvawolf.douban.api.channel.ChannelUpdateResult;
import org.nirvawolf.douban.api.channel.ChannelUpdator;
import org.nirvawolf.douban.api.channel.ChannelUpdatorDelegate;
import org.nirvawolf.douban.api.channel.StableChannelsUpdator;
import org.nirvawolf.douban.api.user.AuthResponse;
import org.nirvawolf.douban.api.user.User;
import org.nirvawolf.douban.api.user.UserAuthentication;
import org.nirvawolf.douban.api.user.UserAuthenticationDelegate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author bruce
 */
public class UserAuthTest implements UserAuthenticationDelegate,ChannelUpdatorDelegate {

    @Override
    public void didRecieveLoginResponse(AuthResponse authResp) {
        if (authResp.isSuccessLogin()) {
            System.out.println("Successfully login:" + authResp.getUser().email+ "|" + authResp.getUser().username);
        } else {
            System.out.println("Login failed with:" + authResp.getErrorDescription());
        }
    }

    public void doTestAuth() {

        User user = new User();
        user.email = "466202783@qq.com";
        user.password = "";

        UserAuthentication auth = new UserAuthentication(user, this);
        auth.attemptToAuth();
    }

    @Override
    public void didRecieveLatestChannelRecords(ChannelUpdateResult result) {
  
        System.out.print("Great!"+result.getChannels());
    }
    
    public static void main(String[] args) {

       
        UserAuthTest test = new UserAuthTest();
        
        ChannelUpdator updator = new StableChannelsUpdator(test);
        updator.attemptToUpdate();
    }

    
}
