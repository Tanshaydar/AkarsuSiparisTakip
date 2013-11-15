/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fumera.model;

/**
 *
 * @author Tansel
 */
public class User {
    
    private int userID;
    private String userName;
    private String userPass;
    private String userLevel;
    private int firmaID;

    public User(int userID, String userName, String userPass, String level, int firmaID) {
        this.userID = userID;
        this.userName = userName;
        this.userPass = userPass;
        this.userLevel = level;
        this.firmaID = firmaID;
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
    
    public void setUserLevel( String level){
        this.userLevel = level;
    }
}
