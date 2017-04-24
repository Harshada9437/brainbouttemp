package com.brainbout.response.Answer;

/**
 * Created by System-2 on 12/19/2016.
 */
public class UserResponseList
{
    private String userName;
    private String email;
    private String mobile;
    private String company;
    private String location;
    private int isParticipated;
    private int score;

    public UserResponseList(String userName, String email, String mobile, String company, String location, int isParticipated, int score) {
        this.userName = userName;
        this.email = email;
        this.mobile = mobile;
        this.company = company;
        this.location = location;
        this.isParticipated = isParticipated;
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
    public String toString() {
        return "UserResponseList{" +
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
