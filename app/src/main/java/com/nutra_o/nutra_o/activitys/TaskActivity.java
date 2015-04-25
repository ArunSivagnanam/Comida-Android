package com.nutra_o.nutra_o.activitys;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.RippleDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nutra_o.nutra_o.R;
import com.nutra_o.nutra_o.models.ApplicationImpl;
import com.nutra_o.nutra_o.models.ApplicationModel;
import com.nutra_o.nutra_o.models.Category;
import com.nutra_o.nutra_o.models.Task;
import com.nutra_o.nutra_o.test.ShoppingList;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class TaskActivity extends ActionBarActivity {

    // task settings
    Task task = null;

    Category category = null;

    ApplicationImpl application;
    ApplicationModel model;

    Dialog dialog;

    Category chosenCategory = new Category(); // just place holder

    private Toolbar toolbar;

    boolean updateMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        task = new Task();

        application = (ApplicationImpl) getApplicationContext();
        model = application.getModel();

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("SUB_MENU", 1);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        setUpCategoryPicter();
        setUpDeadLinePickerDate();
        setUpReminderPickerDate();
        setStartDatePickerDate();
        setUpEstimate();

        updateMode = false;

        if(getIntent().getExtras()!= null){ // can specify witch menu fragment is shown when activity is called from another activity
            Bundle bundle = getIntent().getExtras();
            String jsonString = (String) bundle.getString("TASK");

            Gson gson = new GsonBuilder().create();
            Task recivedTask = gson.fromJson(jsonString, Task.class);
            this.task = recivedTask;
            System.out.println("bobobbobob /n"+this.task);
            updateGuiFromRecievdeTask();
            updateMode = true;
            // update gui elements
            // update boolean to update state
            // if update state, then find task and update instead of add to model
        }

    }

    public void updateGuiFromRecievdeTask(){

        // TITLE
        EditText title = ((EditText) findViewById(R.id.navnoverskrift));
        title.setText(task.name);
        // CATEGORY
        if(task.category != null){
            final TextView text = (TextView) findViewById(R.id.categoryText);
            text.setText(task.category.name);
            final LinearLayout colorPanel = (LinearLayout) findViewById(R.id.blueBox);
            colorPanel.setBackgroundColor(Color.parseColor(task.category.corlorString));
            CheckBox categoryCheckBox = (CheckBox) findViewById(R.id.categoryCheckBox);
            categoryCheckBox.setChecked(true);
            categoryCheckBox.setClickable(true);
        }

        // PROJECT NOT YET MADE

        // DEADLINE
        if(task.deadline != null){
            final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            TextView deadLineView = (TextView) findViewById(R.id.deadlineText);
            deadLineView.setText((dateFormatter.format(task.deadline.getTime())).substring(0,16));
            CheckBox deadLineCheckBox = (CheckBox) findViewById(R.id.deadlineCheckBox);
            deadLineCheckBox.setChecked(true);
            deadLineCheckBox.setClickable(true);
        }

        // REMINDER
        if(task.reminder != null){
            final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            TextView reminderView = (TextView) findViewById(R.id.reminderText);
            reminderView.setText((dateFormatter.format(task.reminder.getTime())).substring(0,16));
            CheckBox reminderCheckBox = (CheckBox) findViewById(R.id.reminderCheckBox);
            reminderCheckBox.setChecked(true);
            reminderCheckBox.setClickable(true);
        }

        // START DATE
        if(task.startDateTime != null){
            final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            TextView startDateView = (TextView) findViewById(R.id.startDate);
            startDateView.setText((dateFormatter.format(task.startDateTime.getTime())).substring(0,16));
            CheckBox startDateCheckBox = (CheckBox) findViewById(R.id.startDateCheckBox);
            startDateCheckBox.setChecked(true);
            startDateCheckBox.setClickable(true);
        }

        // ESTIMATE
        if(task.estimated){
            TextView estimateText = (TextView) findViewById(R.id.estimateText);
            LinearLayout estimateLayot = (LinearLayout) findViewById(R.id.estimateInput);
            CheckBox estimateCheckBox = (CheckBox) findViewById(R.id.estimateCheckBox);
            estimateText.setVisibility(View.GONE);
            estimateLayot.setVisibility(View.VISIBLE);
            estimateCheckBox.setChecked(true);
            estimateCheckBox.setClickable(true);


            EditText stimateDays = ((EditText) findViewById(R.id.days));
            stimateDays.setText(""+task.estimatedDays);
            EditText estimateHours = ((EditText) findViewById(R.id.hours));
            estimateHours.setText(""+task.estimateHours);
            EditText estimateMin = ((EditText) findViewById(R.id.min));
            estimateMin.setText(""+task.estimateMin);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setUpCategoryPicter(){

        // custom dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.category_dialog);
        dialog.setTitle("Choose category");

        LinearLayout dialogLinearLayout = (LinearLayout) findViewById(R.id.categoryLayout);

        final TextView text = (TextView) findViewById(R.id.categoryText);

        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.categoryContainer);
        final LinearLayout colorPanel = (LinearLayout) findViewById(R.id.blueBox);


        for(int i = 0; i<model.categories.size(); i++){
            String color = model.categories.get(i).corlorString;
            final int finalI = i;

            RippleDrawable drawable = (RippleDrawable) getResources().getDrawable(R.drawable.fab);
            drawable.getDrawable(0).setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP);

            CircleImageView c = new CircleImageView(dialog.getContext());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(150,150);
            p.setMargins(10,0,10,0);
            c.setLayoutParams(p);
            c.setBackground(drawable);
            c.setClickable(true);
            linearLayout.addView(c);

            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text.setText(model.categories.get(finalI).name);
                    chosenCategory.copyOver(model.categories.get(finalI));
                }
            });
        }

        Button dialogButton = (Button) dialog.findViewById(R.id.testButton);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.category = new Category();
                task.category.copyOver(chosenCategory);
                colorPanel.setBackgroundColor(Color.parseColor(task.category.corlorString));
                text.setText(task.category.name);
                dialog.dismiss();
                CheckBox checkBox = (CheckBox) findViewById(R.id.categoryCheckBox);
                checkBox.setChecked(true);
                checkBox.setClickable(true);
            }
        });

        dialogLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Whooooooooooooooooooooop");
                dialog.show();
            }
        });
    }

    public void setUpDeadLinePickerDate(){

        final TextView deadLineView = (TextView) findViewById(R.id.deadlineText);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.deadlineLayout);
        final CheckBox checkBox = (CheckBox) findViewById(R.id.deadlineCheckBox);

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        deadLineView.requestFocus();

        final Calendar newCalendar = Calendar.getInstance();

        final DatePickerDialog datePickerDialog = new DatePickerDialog(deadLineView.getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),new TimePickerDialog.OnTimeSetListener(){

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth,hourOfDay,minute);

                        // saetter dato og tid
                        deadLineView.setText((dateFormatter.format(newDate.getTime())).substring(0,16));
                        task.deadline = Timestamp.valueOf(dateFormatter.format(newDate.getTime()));
                        checkBox.setChecked(true);
                        checkBox.setClickable(true);

                    }

                },0,0,true );
                timePickerDialog.show();

            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    public void setUpReminderPickerDate(){

        final TextView reminderView = (TextView) findViewById(R.id.reminderText);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.reminderLayout);
        final CheckBox checkBox = (CheckBox) findViewById(R.id.reminderCheckBox);

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        reminderView.requestFocus();

        final Calendar newCalendar = Calendar.getInstance();

        final DatePickerDialog datePickerDialog = new DatePickerDialog(reminderView.getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),new TimePickerDialog.OnTimeSetListener(){

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth,hourOfDay,minute);

                        // saetter dato og tid
                        reminderView.setText((dateFormatter.format(newDate.getTime())).substring(0,16));
                        task.reminder = Timestamp.valueOf(dateFormatter.format(newDate.getTime()));
                        checkBox.setChecked(true);
                        checkBox.setClickable(true);

                    }

                },0,0,true );
                timePickerDialog.show();

            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    public void setStartDatePickerDate(){

        final TextView startDateView = (TextView) findViewById(R.id.startDate);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.startDateLayout);
        final CheckBox checkBox = (CheckBox) findViewById(R.id.startDateCheckBox);

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        startDateView.requestFocus();

        final Calendar newCalendar = Calendar.getInstance();

        final DatePickerDialog datePickerDialog = new DatePickerDialog(startDateView.getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),new TimePickerDialog.OnTimeSetListener(){

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth,hourOfDay,minute);

                        // saetter dato og tid
                        startDateView.setText((dateFormatter.format(newDate.getTime())).substring(0, 16));
                        task.startDateTime = Timestamp.valueOf(dateFormatter.format(newDate.getTime()));
                        checkBox.setChecked(true);
                        checkBox.setClickable(true);

                    }

                },0,0,true );
                timePickerDialog.show();

            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    public void setUpEstimate(){

        LinearLayout layout = (LinearLayout) findViewById(R.id.estimateLayout);

        final LinearLayout estmateInput = (LinearLayout) findViewById(R.id.estimateInput);
        final TextView estimateText = (TextView) findViewById(R.id.estimateText);
        final CheckBox estimateCheckBox = (CheckBox) findViewById(R.id.estimateCheckBox);


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estimateText.setVisibility(View.GONE);
                estmateInput.setVisibility(View.VISIBLE);
                estimateCheckBox.setChecked(true);
                estimateCheckBox.setClickable(true);
            }
        });

    }




    // onclick metods

    public void categorySetting(View v){
        CheckBox checkBox = (CheckBox) v;
        TextView categoryText = (TextView) findViewById(R.id.categoryText);
        LinearLayout colorPanel = (LinearLayout) findViewById(R.id.blueBox);

        if(!checkBox.isActivated()){ // category stetting enabled
            categoryText.setText("Category");
            task.category = null;
            checkBox.setClickable(false);
            colorPanel.setBackgroundColor(Color.parseColor("#ff264653"));

        }
    }

    public void deadLineSetting(View v){
        CheckBox checkBox = (CheckBox) v;
        TextView deadLineText = (TextView) findViewById(R.id.deadlineText);

        if(!checkBox.isActivated()){ // category stetting enabled
            deadLineText.setText("Deadline");
            task.deadline = null;
            checkBox.setClickable(false);
        }
    }

    public void reminderSetting(View v){
        CheckBox checkBox = (CheckBox) v;
        TextView reminderText = (TextView) findViewById(R.id.reminderText);

        if(!checkBox.isActivated()){ // category stetting enabled
            reminderText.setText("Reminder");
            task.reminder = null;
            checkBox.setClickable(false);
        }
    }

    public void startDateSetting(View v){
        CheckBox checkBox = (CheckBox) v;
        TextView startDateText = (TextView) findViewById(R.id.startDate);

        if(!checkBox.isActivated()){ // category stetting enabled
            startDateText.setText("Start date");
            task.startDateTime = null;
            checkBox.setClickable(false);
        }
    }

    public void estimateSetting(View v){
        CheckBox checkBox = (CheckBox) v;
        TextView estimateText = (TextView) findViewById(R.id.estimateText);
        final LinearLayout estmateInput = (LinearLayout) findViewById(R.id.estimateInput);
        final EditText days = (EditText) findViewById(R.id.days);
        final EditText hours = (EditText) findViewById(R.id.hours);
        final EditText min = (EditText) findViewById(R.id.min);


        if(!checkBox.isActivated()){ // category stetting enabled
            estimateText.setVisibility(View.VISIBLE);
            estmateInput.setVisibility(View.GONE);
            task.startDateTime = null;
            checkBox.setClickable(false);
            days.setText("");
            hours.setText("");
            min.setText("");
        }
    }


    // TASK creation method

    public void createTask(View view){

        String stimateDays;
        String estimateHours;
        String estimateMin;
        Integer days = 0;
        Integer hours = 0;
        Integer min = 0;
        String name = ((EditText) findViewById(R.id.navnoverskrift)).getText().toString();
        CheckBox estimated = (CheckBox) findViewById(R.id.estimateCheckBox);

        if(estimated.isChecked()){

            System.out.println("HAALllolololololol");

            stimateDays = ((EditText) findViewById(R.id.days)).getText().toString();
            estimateHours = ((EditText) findViewById(R.id.hours)).getText().toString();
            estimateMin = ((EditText) findViewById(R.id.min)).getText().toString();

            if(!stimateDays.isEmpty()){
                days = Integer.parseInt(stimateDays);
            }
            if(!estimateHours.isEmpty()){
                hours = Integer.parseInt(estimateHours);
            }
            if(!estimateMin.isEmpty()){
                min = Integer.parseInt(estimateMin);
            }

            task.estimated = true;
            task.estimatedDays = days;
            task.estimateHours = hours;
            task.estimateMin = min;
        }

        // valide check
        if(!name.isEmpty()){
            // check om der skal opdateres eller laves en ny

            if(updateMode){

                task.name = name;
                for(Task t : model.taskList){
                    if(t.id == this.task.id){
                        t.copyOver(this.task);
                    }
                }

                Intent i = new Intent(getBaseContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("SUB_MENU", 1);
                i.putExtras(bundle);
                startActivity(i);

            }else{

                task.name = name;
                model.addTaskWithPriorety(task);
                System.out.println("TASK ADDED");
                System.out.println(task);

                // vend tilbage.
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("SUB_MENU", 1);
                i.putExtras(bundle);
                startActivity(i);
            }

        }else {
            // lav toast
            Toast.makeText(getApplicationContext(), "Give youre task a name",
                    Toast.LENGTH_LONG).show();
        }

    }



}
