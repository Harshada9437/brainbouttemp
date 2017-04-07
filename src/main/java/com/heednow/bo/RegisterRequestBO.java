package com.heednow.bo;

/**
 * Created by System-2 on 1/19/2017.
 */
public class RegisterRequestBO {
    private String storeId;
    private String installationId;
    private String androidDeviceId;
    private String clientId;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getInstallationId() {
        return installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
    }

    public String getAndroidDeviceId() {
        return androidDeviceId;
    }

    public void setAndroidDeviceId(String androidDeviceId) {
        this.androidDeviceId = androidDeviceId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegisterRequestBO that = (RegisterRequestBO) o;

        if (storeId != null ? !storeId.equals(that.storeId) : that.storeId != null) return false;
        if (installationId != null ? !installationId.equals(that.installationId) : that.installationId != null)
            return false;
        if (androidDeviceId != null ? !androidDeviceId.equals(that.androidDeviceId) : that.androidDeviceId != null)
            return false;
        return clientId != null ? clientId.equals(that.clientId) : that.clientId == null;
    }

    @Override
    public int hashCode() {
        int result = storeId != null ? storeId.hashCode() : 0;
        result = 31 * result + (installationId != null ? installationId.hashCode() : 0);
        result = 31 * result + (androidDeviceId != null ? androidDeviceId.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RegisterRequestBO{" +
                "storeId='" + storeId + '\'' +
                ", installationId='" + installationId + '\'' +
                ", androidDeviceId='" + androidDeviceId + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
