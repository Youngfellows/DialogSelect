package com.neandroid.dialogui.bean;

import java.io.Serializable;

/**
 * 条目实体
 */
public class TieBean implements Serializable {

    /**
     * 条目ID
     */
    private int id;

    /**
     * 显示的Title
     */
    private String title;

    /**
     * 是否选中
     */
    private boolean isSelect;

    public TieBean(String title) {
        this.title = title;
    }

    public TieBean(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public TieBean(int id, String title, boolean isSelect) {
        this.id = id;
        this.title = title;
        this.isSelect = isSelect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
