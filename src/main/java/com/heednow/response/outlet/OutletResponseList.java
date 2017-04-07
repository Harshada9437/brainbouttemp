package com.heednow.response.outlet;

import com.heednow.response.template.QueResponse;
import com.heednow.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-2 on 12/20/2016.
 */
public class OutletResponseList implements GenericResponse
{
    private int id;
    private String outletDesc;
    private List<QueResponse> questions;
    private int mobileNoLength;
    private String bannerUrl;
    private String shortDesc;
    private int clusterId;
    private String clusterName;
    private int regionId;
    private String regionName;
    private int companyId;
    private String companyName;
    private String tableNoRange;
    private int  groupId;
    private String  groupName;
    private String  message;
    private String  messageType;
    private String  templateName;
    private String posStoreId;
    private int templateId;

    public int getMobileNoLength() {
        return mobileNoLength;
    }

    public void setMobileNoLength(int mobileNoLength) {
        this.mobileNoLength = mobileNoLength;
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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setTemplateId(int templateId) {this.templateId = templateId;}

    public int getTemplateId() {return templateId;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOutletDesc() {
        return outletDesc;
    }

    public void setOutletDesc(String outletDesc) {
        this.outletDesc = outletDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getPosStoreId() {
        return posStoreId;
    }

    public void setPosStoreId(String posStoreId) {
        this.posStoreId = posStoreId;
    }

    public String getClusterName() {return clusterName;}

    public void setClusterName(String clusterName) {this.clusterName = clusterName;}

    public String getRegionName() {return regionName;}

    public void setRegionName(String regionName) {this.regionName = regionName;}

    public String getCompanyName() {return companyName;}

    public void setCompanyName(String companyName) {this.companyName = companyName;}

    public String getGroupName() {return groupName;}

    public void setGroupName(String groupName) {this.groupName = groupName;}

    public List<QueResponse> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QueResponse> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "OutletResponseList{" +
                "id=" + id +
                ", outletDesc='" + outletDesc + '\'' +
                ", questions=" + questions +
                ", mobileNoLength=" + mobileNoLength +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", clusterId=" + clusterId +
                ", clusterName='" + clusterName + '\'' +
                ", regionId=" + regionId +
                ", regionName='" + regionName + '\'' +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", tableNoRange='" + tableNoRange + '\'' +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                ", templateName='" + templateName + '\'' +
                ", posStoreId='" + posStoreId + '\'' +
                ", templateId=" + templateId +
                '}';
    }
}
