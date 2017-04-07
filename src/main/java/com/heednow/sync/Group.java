package com.heednow.sync;

/**
 * Created by System-2 on 1/9/2017.
 */
public class Group {
    private int id;
    private int clientId;
    private String desc;
    private String shortDesc;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (id != group.id) return false;
        if (clientId != group.clientId) return false;
        if (desc != null ? !desc.equals(group.desc) : group.desc != null) return false;
        return shortDesc != null ? shortDesc.equals(group.shortDesc) : group.shortDesc == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + clientId;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (shortDesc != null ? shortDesc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", desc='" + desc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                '}';
    }
}
