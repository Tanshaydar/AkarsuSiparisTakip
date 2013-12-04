/*
 * Fumera Ar-Ge Yazılım Müh. İml. San. ve Tic. Ltd. Şti. | Copyright 2012-2013
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions 
 * are met: 
 * 
 * 1. Redistributions of source code must retain the above copyright 
 * notice, this list of conditions, and the following disclaimer. 
 * 
 * 2. Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions, and the following disclaimer in the 
 * documentation and/or other materials provided with the distribution. 
 * 
 * 3. The name of the author may not be used to endorse or promote products 
 * derived from this software without specific prior written permission. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES 
 * OF TITLE, NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */

package fumera.model;

/**
 *
 * @author Tansel
 */
public class User {
    
    private int userID;
    private String userRealName;
    private String userName;
    private String userPass;
    private String userLevel;
    private int firmaID;
    private int siparisCount;

    public User(int userID, String userRealName, String userName, String userPass, String level, int firmaID) {
        this.userRealName = userRealName;
        this.userID = userID;
        this.userName = userName;
        this.userPass = userPass;
        this.userLevel = level;
        this.firmaID = firmaID;
    }
    
    public User(int userID, String userRealName, String userName, String userPass, int level, int firmaID) {
        this.userRealName = userRealName;
        this.userID = userID;
        this.userName = userName;
        this.userPass = userPass;
        switch( level){
            case 0:
                userLevel = "admin";
                break;
            case 1:
                userLevel = "mod";
                break;
            case 2:
                userLevel = "user";
                break;
            case 3:
                userLevel = "watcher";
                break;
            case 4:
                userLevel = "demo";
                break;
        }
        this.firmaID = firmaID;
    }
    
    public String getUserRealName(){
        return userRealName;
    }
    
    public void setUserRealName(String userRealName){
        this.userRealName = userRealName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
    
    public String getUserLevel(){
        return userLevel;
    }
    
    public int getUserLevelInt(){
        switch(userLevel){
            case "admin":
                return 1;
            case "mod":
                return 2;
            case "user":
                return 3;
            case "watcher":
                return 4;
            case "demo":
                return 5;
            default:
                return 0;
        }
    }
    
    public void setUserLevel( int level){
        switch( level){
            case 0:
                userLevel = "admin";
                break;
            case 1:
                userLevel = "mod";
                break;
            case 2:
                userLevel = "user";
                break;
            case 3:
                userLevel = "watcher";
                break;
            case 4:
                userLevel = "demo";
                break;
        }
    }
    
    public int getUserID(){
        return userID;
    }
    
    public int getSiparisCount(){
        return siparisCount;
    }
}
