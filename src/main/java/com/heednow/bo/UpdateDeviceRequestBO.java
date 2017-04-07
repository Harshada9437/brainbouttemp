package com.heednow.bo;

/**
 * Created by System-2 on 1/17/2017.
 */
public class UpdateDeviceRequestBO {
    private int otp;
    private String fingerprint;
    private String androidDeviceId;
    private String installationId;
    private String storeId;
    private String clientId;


    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getAndroidDeviceId() {
        return androidDeviceId;
    }

    public void setAndroidDeviceId(String androidDeviceId) {
        this.androidDeviceId = androidDeviceId;
    }

    public String getInstallationId() {
        return installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
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

        UpdateDeviceRequestBO that = (UpdateDeviceRequestBO) o;

        if (otp != that.otp) return false;
        if (fingerprint != null ? !fingerprint.equals(that.fingerprint) : that.fingerprint != null) return false;
        if (androidDeviceId != null ? !androidDeviceId.equals(that.androidDeviceId) : that.androidDeviceId != null)
            return false;
        if (installationId != null ? !installationId.equals(that.installationId) : that.installationId != null)
            return false;
        if (storeId != null ? !storeId.equals(that.storeId) : that.storeId != null) return false;
        return clientId != null ? clientId.equals(that.clientId) : that.clientId == null;
    }

    @Override
    public int hashCode() {
        int result = otp;
        result = 31 * result + (fingerprint != null ? fingerprint.hashCode() : 0);
        result = 31 * result + (androidDeviceId != null ? androidDeviceId.hashCode() : 0);
        result = 31 * result + (installationId != null ? installationId.hashCode() : 0);
        result = 31 * result + (storeId != null ? storeId.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdateDeviceRequestBO{" +
                "otp=" + otp +
                ", fingerprint='" + fingerprint + '\'' +
                ", androidDeviceId='" + androidDeviceId + '\'' +
                ", installationId='" + installationId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
