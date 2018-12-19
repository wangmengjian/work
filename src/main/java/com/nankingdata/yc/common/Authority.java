package com.nankingdata.yc.common;

import java.io.Serializable;

public class Authority implements Serializable {

    private static final long serialVersionUID = 4123336758372084309L;
    //菜单id
    private Integer menuId;
    //菜单url
    private String menuUrl;
    //菜单名
    private String menuName;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
