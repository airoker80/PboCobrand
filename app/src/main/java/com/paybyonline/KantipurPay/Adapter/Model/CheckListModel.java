package com.paybyonline.KantipurPay.Adapter.Model;


public class CheckListModel {


    private String name;
    private boolean selected;

    public CheckListModel(String name) {
        super();
        this.name = name;
        this.selected = false;
    }
    public CheckListModel(String name, boolean selected) {

        this.name = name;
        this.selected = selected;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public  void toggleChecked() {
        selected = !selected ;
    }

}
