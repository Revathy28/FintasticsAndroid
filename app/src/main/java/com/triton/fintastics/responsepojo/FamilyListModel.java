package com.triton.fintastics.responsepojo;

public class FamilyListModel {

    private String name;
    private int id;
    private boolean select;

    public FamilyListModel(String name, int id, boolean select) {
        this.name = name;
        this.id = id;
        this.select = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
