/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nirvawolf.fm.user;

import java.io.Serializable;
import org.nirvawolf.douban.api.user.AuthResponse;
import org.nirvawolf.douban.api.user.User;
import org.nirvawolf.douban.api.user.UserAuthentication;
import org.nirvawolf.douban.api.user.UserAuthenticationDelegate;
import org.nirvawolf.fm.chain.BootChainNode;

/**
 *
 * @author bruce
 */
public class UserManager
extends BootChainNode
implements UserAuthenticationDelegate,Serializable{
    
    private static UserManager instance;
    
    private boolean isLogin;
    private User currentUser;
    
    private UserManager(){
        isLogin = false;
        currentUser = null;
    }
    
    public static synchronized UserManager sharedInstance(){
        
        if(instance == null){
            instance = new UserManager();
        }
        
        return instance;
    }
    
    public void setUser(User user){
        this.currentUser = user;
        this.isLogin = false;
    }

    public boolean getIsLogin() {
        return isLogin;
    }

    public User getCurrentUser() {
        if(this.isLogin){
            return currentUser;
        }else{
            return null;
        }
    }
    
    @Override
    public void start() {
        if(this.currentUser != null && !isLogin){
            new UserAuthentication(currentUser,this).attemptToAuth();
        }else{
            this.notifySubNodesReady();
        }
    }

    @Override
    public void didRecieveLoginResponse(AuthResponse authResp) {
      
        if(authResp.isSuccessLogin()){
            this.currentUser = authResp.getUser();
            this.isLogin = true;
        }else{
            this.currentUser = null;
            this.isLogin = false;
        }
        
        this.notifySubNodesReady();
    }
}
