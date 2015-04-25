package com.nutra_o.nutra_o.test;

import android.graphics.Color;

import com.nutra_o.nutra_o.models.Category;
import com.nutra_o.nutra_o.models.Task;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ars on 12-04-2015.
 */
public class TestData {

    public static void fillCattegoryList(ArrayList<Category> list){

        Category hjemmeOpgaver = new Category();

        hjemmeOpgaver.color = Color.parseColor("#E9C46A");
        hjemmeOpgaver.id = 1;
        hjemmeOpgaver.name = "Hjemme opgaver";
        hjemmeOpgaver.corlorString = "#E9C46A";

        Category netCompanyOpgaver = new Category();

        netCompanyOpgaver.color = Color.parseColor("#E76F51");
        netCompanyOpgaver.id = 2;
        netCompanyOpgaver.name = "NetCompany Opgaver";
        netCompanyOpgaver.corlorString= "#E76F51";

        Category mad = new Category();
        mad.color = Color.parseColor("#fff45596");
        mad.id = 3;
        mad.name = "Mad";
        mad.corlorString = "#fff45596";

        Category altmuligt = new Category();
        altmuligt.color = Color.parseColor("#ff00f467");
        altmuligt.id = 4;
        altmuligt.name = "Alt muligt";
        altmuligt.corlorString = "#ff00f467";

        list.add(mad);
        list.add(altmuligt);
        list.add(hjemmeOpgaver);
        list.add(netCompanyOpgaver);
        list.add(netCompanyOpgaver);
        list.add(netCompanyOpgaver);
        list.add(netCompanyOpgaver);
        list.add(netCompanyOpgaver);
        list.add(netCompanyOpgaver);
        list.add(netCompanyOpgaver);

    }

    public static void fillTaskList(ArrayList<Task> taskList) {

        Task t1 = new Task();
        t1.id = 1;
        t1.name = "Rydop";
        t1.setDeadLine(new Date());
        t1.setReminder(new Date());
        t1.estimated = true;
        t1.estimateHours = 2;
        t1.estimatedDays = 1;
        t1.estimateMin = 2;
        t1.procentageCompleted = 20;

        t1.category = new Category(1,"Hjemme opgaver",Color.parseColor("#E9C46A"));
        t1.category.corlorString = "#E9C46A";

        Task t2 = new Task();
        t2.id = 2;
        t2.name = "Lav mad";
        t2.category = new Category(2,"NetCompany Opgaver",Color.parseColor("#E76F51"));
        t2.category.corlorString = "#E76F51";

        Task t3 = new Task();
        t3.id = 3;
        t3.name = "Hop i søen";
        t3.category = new Category(2,"NetCompany Opgaver",Color.parseColor("#E76F51"));
        t3.category.corlorString = "#E76F51";

        Task t4 = new Task();
        t4.id = 4;
        t4.name = "Slap af";
        t4.category = null;


        Task t5 = new Task();
        t5.id = 5;
        t5.name = "Lav lektier";
        t5.category = new Category(5,"NetCompany Opgaver",Color.parseColor("#ff009769"));
        t5.category.corlorString = "#ff009769";

        addTaskWithPriorety(taskList, t1);
        addTaskWithPriorety(taskList, t2);
        addTaskWithPriorety(taskList, t3);
        addTaskWithPriorety(taskList, t4);
        addTaskWithPriorety(taskList, t5);


    }

    public static void  addTaskWithPriorety(ArrayList<Task> taskList, Task t){

        int priorety = taskList.size();
        t.priorety = priorety;
        taskList.add(t);
    }
}
