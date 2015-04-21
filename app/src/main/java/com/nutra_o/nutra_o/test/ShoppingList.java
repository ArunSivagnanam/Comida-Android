package com.nutra_o.nutra_o.test;

import com.nutra_o.nutra_o.test.ShoppingListItem;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by ars on 02-04-2015.
 */
public class ShoppingList {

    public int id;
    public int userID;
    public String name;
    public Timestamp creationTime;
    public Timestamp duoDate;
    public Boolean executed;

    // model specific
    public ArrayList<ShoppingListItem> itemList;

    @Override
    public String toString() {
        return "ShoppingList{" +
                "id=" + id +
                ", userID=" + userID +
                ", name='" + name + '\'' +
                ", creationTime=" + creationTime +
                ", duoDate=" + duoDate +
                ", executed=" + executed +
                ", itemList=" + itemList +
                '}';
    }
}
