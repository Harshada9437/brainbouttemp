package com.heednow.response.group;

/**
 * Created by System-2 on 4/4/2017.
 */
public class GroupDetail {
    private int id;
    private String desc;
    private String shortDesc;

    public GroupDetail(int id, String desc, String shortDesc) {
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
    public String toString() {
        return "GroupDetail{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                '}';
    }
}
