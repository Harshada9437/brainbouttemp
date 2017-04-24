package com.brainbout.dto;

/**
 * Created by System-2 on 3/10/2017.
 */
public class UserDTO {
    private String userName;
    private String email;
    private String mobile;
    private String company;
    private String location;
    private int isParticipated;
    private int score;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setIsParticipated(int isParticipated) {
        this.isParticipated = isParticipated;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public int getIsParticipated() {
        return isParticipated;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (isParticipated != userDTO.isParticipated) return false;
        if (score != userDTO.score) return false;
        if (userName != null ? !userName.equals(userDTO.userName) : userDTO.userName != null) return false;
        if (email != null ? !email.equals(userDTO.email) : userDTO.email != null) return false;
        if (mobile != null ? !mobile.equals(userDTO.mobile) : userDTO.mobile != null) return false;
        if (company != null ? !company.equals(userDTO.company) : userDTO.company != null) return false;
        return location != null ? location.equals(userDTO.location) : userDTO.location == null;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + isParticipated;
        result = 31 * result + score;
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", isParticipated=" + isParticipated +
                ", score=" + score +
                '}';
    }
}
