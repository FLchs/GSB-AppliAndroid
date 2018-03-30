package fr.cned.emdsgil.suividevosfrais;

/**
 * Created by francois on 3/29/18.
 */



public class User {

    private String login;
    private String password;

    public void setLoginPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public User() {
    }

    public String getLogin() {
        return this.login;
    }
    public String getPassword() {
        return this.password;
    }

}