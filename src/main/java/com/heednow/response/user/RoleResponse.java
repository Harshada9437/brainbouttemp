package com.heednow.response.user;

/**
 * Created by System-3 on 2/8/2017.
 */
public class RoleResponse {
    private int roleId;
    private int isAll;
    private String name;
    private String menuAccess;
    private String outletAccess;

    public RoleResponse(int roleId,int isAll, String name, String menuAccess, String outletAccess) {
        this.roleId = roleId;
        this.isAll = isAll;
        this.name = name;
        this.menuAccess = menuAccess;
        this.outletAccess = outletAccess;
    }

    public int getIsAll() {
        return isAll;
    }

    public void setIsAll(int isAll) {
        this.isAll = isAll;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuAccess() {
        return menuAccess;
    }

    public void setMenuAccess(String menuAccess) {
        this.menuAccess = menuAccess;
    }

    public String getOutletAccess() {
        return outletAccess;
    }

    public void setOutletAccess(String outletAccess) {
        this.outletAccess = outletAccess;
    }

    @Override
    public String toString() {
        return "RoleResponse{" +
                "roleId=" + roleId +
                ", isAll=" + isAll +
                ", name='" + name + '\'' +
                ", menuAccess='" + menuAccess + '\'' +
                ", outletAccess='" + outletAccess + '\'' +
                '}';
    }


}
