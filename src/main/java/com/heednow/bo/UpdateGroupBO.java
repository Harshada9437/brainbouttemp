package com.heednow.bo;

/**
 * Created by System-2 on 4/4/2017.
 */
public class UpdateGroupBO {
    private int id;
    private String desc;
    private String shortDesc;

    public UpdateGroupBO(int id, String desc, String shortDesc) {
        this.id = id;
        this.desc = desc;
        this.shortDesc = shortDesc;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateGroupBO that = (UpdateGroupBO) o;

        if (id != that.id) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        return shortDesc != null ? shortDesc.equals(that.shortDesc) : that.shortDesc == null;
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
        return "UpdateGroupBO{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                '}';
    }
}
