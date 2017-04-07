package com.heednow.bo;

/**
 * Created by Sumedh on 21-06-2016.
 */
public class LoginRequestBO {

    private String userName;
    private String password;
    private String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginRequestBO that = (LoginRequestBO) o;

        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        return password != null ? password.equals(that.password) : that.password == null;

    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginRequestBO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
