package com.heednow.response.client;

/**
 * Created by System-2 on 3/9/2017.
 */
public class ClientDetail {
    private int id;
    private int noOfOutlets;
    private int noOfDevices;
    private String name;
    private String email;
    private String mobile;
    private String location;
    private String clientId;
    private String status;

    public ClientDetail(int id, String name, String email, String mobile, String location, String clientId, int noOfOutlets, int noOfDevices, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.location = location;
        this.clientId = clientId;
        this.noOfOutlets = noOfOutlets;
        this.noOfDevices = noOfDevices;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLocation() {
        return location;
    }

    public String getClientId() {
        return clientId;
    }

    public String getStatus() {
        return status;
    }

    public int getNoOfOutlets() {
        return noOfOutlets;
    }

    public int getNoOfDevices() {
        return noOfDevices;
    }

    @Override
    public String toString() {
        return "ClientDetail{" +
                "id=" + id +
                ", noOfOutlets=" + noOfOutlets +
                ", noOfDevices=" + noOfDevices +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", location='" + location + '\'' +
                ", clientId='" + clientId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
