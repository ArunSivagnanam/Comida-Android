package com.nutra_o.nutra_o.test;

import java.sql.Timestamp;

/**
 * Created by ars on 02-04-2015.
 */
public class ShoppingListItem {

    public int ID;
    public int ShoppingListID;
    public int ItemID;
    public double Amount;
    public String Unit;
    public Timestamp ExpireDay;
    public Boolean Basic;
    public int  Foodinfo;
    public String FoodName;


    @Override
    public String toString() {
        return "ShoppingListItem{" +
                "ID=" + ID +
                ", ShoppingListID=" + ShoppingListID +
                ", ItemID=" + ItemID +
                ", Amount=" + Amount +
                ", Unit='" + Unit + '\'' +
                ", ExpireDay=" + ExpireDay +
                ", Basic=" + Basic +
                ", Foodinfo=" + Foodinfo +
                ", FoodName='" + FoodName + '\'' +
                '}';
    }
}
