package com.heednow.dto.request;

/**
 * Created by System-2 on 1/17/2017.
 */
public class DeviceDTO {
    private int id;
    private int feedbackId;
    private int clientId;
    private int otp;
    private String feedbackDate;
    private String token;
    private String installationId;
    private String fingerprint;
    private String androidDeviceId;
    private String storeId;
    private String installationDate;
    private String status;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstallationId() {
        return installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFeedbackId() {return feedbackId;}

    public void setFeedbackId(int feedbackId) {this.feedbackId = feedbackId;}

    public String getFeedbackDate() {return feedbackDate;}

    public void setFeedbackDate(String feedbackDate) {this.feedbackDate = feedbackDate;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceDTO deviceDTO = (DeviceDTO) o;

        if (id != deviceDTO.id) return false;
        if (feedbackId != deviceDTO.feedbackId) return false;
        if (clientId != deviceDTO.clientId) return false;
        if (otp != deviceDTO.otp) return false;
        if (feedbackDate != null ? !feedbackDate.equals(deviceDTO.feedbackDate) : deviceDTO.feedbackDate != null)
            return false;
        if (token != null ? !token.equals(deviceDTO.token) : deviceDTO.token != null) return false;
        if (installationId != null ? !installationId.equals(deviceDTO.installationId) : deviceDTO.installationId != null)
            return false;
        if (fingerprint != null ? !fingerprint.equals(deviceDTO.fingerprint) : deviceDTO.fingerprint != null)
            return false;
        if (androidDeviceId != null ? !androidDeviceId.equals(deviceDTO.androidDeviceId) : deviceDTO.androidDeviceId != null)
            return false;
        if (storeId != null ? !storeId.equals(deviceDTO.storeId) : deviceDTO.storeId != null) return false;
        if (installationDate != null ? !installationDate.equals(deviceDTO.installationDate) : deviceDTO.installationDate != null)
            return false;
        return status != null ? status.equals(deviceDTO.status) : deviceDTO.status == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + feedbackId;
        result = 31 * result + clientId;
        result = 31 * result + otp;
        result = 31 * result + (feedbackDate != null ? feedbackDate.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (installationId != null ? installationId.hashCode() : 0);
        result = 31 * result + (fingerprint != null ? fingerprint.hashCode() : 0);
        result = 31 * result + (androidDeviceId != null ? androidDeviceId.hashCode() : 0);
        result = 31 * result + (storeId != null ? storeId.hashCode() : 0);
        result = 31 * result + (installationDate != null ? installationDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }


}
