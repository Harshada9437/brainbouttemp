package com.heednow.bo;

/**
 * Created by System-2 on 4/4/2017.
 */
public class GroupRequestBO {
    private String desc;
    private String shortDesc;

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

        GroupRequestBO that = (GroupRequestBO) o;

        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        return shortDesc != null ? shortDesc.equals(that.shortDesc) : that.shortDesc == null;
    }

    @Override
    public int hashCode() {
        int result = desc != null ? desc.hashCode() : 0;
        result = 31 * result + (shortDesc != null ? shortDesc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GroupRequestBO{" +
                "desc='" + desc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                '}';
    }
}
