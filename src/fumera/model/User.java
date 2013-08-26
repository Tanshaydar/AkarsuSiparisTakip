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
    
    private String userName;
    private String userPass;

    public User(String userName, String userPass) {
        this.userName = userName;
        this.userPass = userPass;
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
}
