package com.heednow.dto.client;

/**
 * Created by System-2 on 3/10/2017.
 */
public class ClientDTO {
    private int id;
    private int noOfOutlets;
    private int noOfDevices;
    private String name;
    private String email;
    private String mobile;
    private String location;
    private String clientId;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNoOfOutlets() {
        return noOfOutlets;
    }

    public void setNoOfOutlets(int noOfOutlets) {
        this.noOfOutlets = noOfOutlets;
    }

    public int getNoOfDevices() {
        return noOfDevices;
    }

    public void setNoOfDevices(int noOfDevices) {
        this.noOfDevices = noOfDevices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientDTO clientDTO = (ClientDTO) o;

        if (id != clientDTO.id) return false;
        if (noOfOutlets != clientDTO.noOfOutlets) return false;
        if (noOfDevices != clientDTO.noOfDevices) return false;
        if (name != null ? !name.equals(clientDTO.name) : clientDTO.name != null) return false;
        if (email != null ? !email.equals(clientDTO.email) : clientDTO.email != null) return false;
        if (mobile != null ? !mobile.equals(clientDTO.mobile) : clientDTO.mobile != null) return false;
        if (location != null ? !location.equals(clientDTO.location) : clientDTO.location != null) return false;
        if (clientId != null ? !clientId.equals(clientDTO.clientId) : clientDTO.clientId != null) return false;
        return status != null ? status.equals(clientDTO.status) : clientDTO.status == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + noOfOutlets;
        result = 31 * result + noOfDevices;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
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
