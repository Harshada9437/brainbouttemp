package com.heednow.dto;

/**
 * Created by System-3 on 4/5/2017.
 */
public class AndroidDTO {
    private int versionCode;
    private String versionName;
    private int clientId;


    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
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

        AndroidDTO that = (AndroidDTO) o;

        if (versionCode != that.versionCode) return false;
        if (clientId != that.clientId) return false;
        return versionName.equals(that.versionName);
    }

    @Override
    public int hashCode() {
        int result = versionCode;
        result = 31 * result + versionName.hashCode();
        result = 31 * result + clientId;
        return result;
    }

    @Override
    public String toString() {
        return "AndroidDTO{" +
                "versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}
