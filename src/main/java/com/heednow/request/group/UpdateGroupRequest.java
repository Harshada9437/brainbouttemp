package com.heednow.request.group;

/**
 * Created by System-2 on 4/4/2017.
 */
public class UpdateGroupRequest {
    private int id;
    private String desc;
    private String shortDesc;

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

        UpdateGroupRequest group = (UpdateGroupRequest) o;

        if (id != group.id) return false;
        if (desc != null ? !desc.equals(group.desc) : group.desc != null) return false;
        return shortDesc != null ? shortDesc.equals(group.shortDesc) : group.shortDesc == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (shortDesc != null ? shortDesc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdateGroupRequest{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                '}';
    }
}

