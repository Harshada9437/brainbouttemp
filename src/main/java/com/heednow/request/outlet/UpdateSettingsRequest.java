package com.heednow.request.outlet;

/**
 * Created by System-2 on 12/27/2016.
 */
public class UpdateSettingsRequest {
    private int mobileNoLength;
    private int smsGatewayId;
    private String bannerUrl;
    private String tableNoRange;
    private String pocName;
    private String pocMobile;
    private String pocEmail;
    private String mgrName;
    private String mgrMobile;
    private String mgrEmail;

    public int getMobileNoLength() {
        return mobileNoLength;
    }

    public void setMobileNoLength(int mobileNoLength) {
        this.mobileNoLength = mobileNoLength;
    }

    public int getSmsGatewayId() {
        return smsGatewayId;
    }

    public void setSmsGatewayId(int smsGatewayId) {
        this.smsGatewayId = smsGatewayId;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getTableNoRange() {
        return tableNoRange;
    }

    public void setTableNoRange(String tableNoRange) {
        this.tableNoRange = tableNoRange;
    }

    public String getPocName() {
        return pocName;
    }

    public void setPocName(String pocName) {
        this.pocName = pocName;
    }

    public String getPocMobile() {
        return pocMobile;
    }

    public void setPocMobile(String pocMobile) {
        this.pocMobile = pocMobile;
    }

    public String getPocEmail() {
        return pocEmail;
    }

    public void setPocEmail(String pocEmail) {
        this.pocEmail = pocEmail;
    }

    public String getMgrName() {
        return mgrName;
    }

    public void setMgrName(String mgrName) {
        this.mgrName = mgrName;
    }

    public String getMgrMobile() {
        return mgrMobile;
    }

    public void setMgrMobile(String mgrMobile) {
        this.mgrMobile = mgrMobile;
    }

    public String getMgrEmail() {
        return mgrEmail;
    }

    public void setMgrEmail(String mgrEmail) {
        this.mgrEmail = mgrEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateSettingsRequest that = (UpdateSettingsRequest) o;

        if (mobileNoLength != that.mobileNoLength) return false;
        if (smsGatewayId != that.smsGatewayId) return false;
        if (!bannerUrl.equals(that.bannerUrl)) return false;
        if (!tableNoRange.equals(that.tableNoRange)) return false;
        if (!pocName.equals(that.pocName)) return false;
        if (!pocMobile.equals(that.pocMobile)) return false;
        if (!pocEmail.equals(that.pocEmail)) return false;
        if (!mgrName.equals(that.mgrName)) return false;
        if (!mgrMobile.equals(that.mgrMobile)) return false;
        return mgrEmail.equals(that.mgrEmail);
    }

    @Override
    public int hashCode() {
        int result = mobileNoLength;
        result = 31 * result + smsGatewayId;
        result = 31 * result + bannerUrl.hashCode();
        result = 31 * result + tableNoRange.hashCode();
        result = 31 * result + pocName.hashCode();
        result = 31 * result + pocMobile.hashCode();
        result = 31 * result + pocEmail.hashCode();
        result = 31 * result + mgrName.hashCode();
        result = 31 * result + mgrMobile.hashCode();
        result = 31 * result + mgrEmail.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UpdateSettingsRequest{" +
                "mobileNoLength=" + mobileNoLength +
                ", smsGatewayId=" + smsGatewayId +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", tableNoRange='" + tableNoRange + '\'' +
                ", pocName='" + pocName + '\'' +
                ", pocMobile='" + pocMobile + '\'' +
                ", pocEmail='" + pocEmail + '\'' +
                ", mgrName='" + mgrName + '\'' +
                ", mgrMobile='" + mgrMobile + '\'' +
                ", mgrEmail='" + mgrEmail + '\'' +
                '}';
    }
}
