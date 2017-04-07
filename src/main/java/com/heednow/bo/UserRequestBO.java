package com.heednow.bo;

/**
 * Created by System-3 on 2/9/2017.
 */
public class UserRequestBO {
    private String userName;
    private String name;
    private String email;
    private String password;
    private int roleId;
    private int clientId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRequestBO that = (UserRequestBO) o;

        if (roleId != that.roleId) return false;
        if (clientId != that.clientId) return false;
        if (!userName.equals(that.userName)) return false;
        if (!name.equals(that.name)) return false;
        if (!email.equals(that.email)) return false;
        return password.equals(that.password);
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + roleId;
        result = 31 * result + clientId;
        return result;
    }

    @Override
    public String toString() {
        return "UserRequestBO{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                ", clientId=" + clientId +
                '}';
    }
}



