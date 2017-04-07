package com.heednow.dto.request;

/**
 * Created by System-2 on 4/3/2017.
 */
public class Outlets {
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
    public String toString() {
        return "Outlets{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                '}';
    }
}
