package com.nutra_o.nutra_o.activitys;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import com.nutra_o.nutra_o.R;
import com.nutra_o.nutra_o.activitys.MainActivity;
import com.nutra_o.nutra_o.adapters.ItemAutoCompleteAdapter;
import com.nutra_o.nutra_o.models.FoodInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class IndkoebsListEditActivity extends ActionBarActivity implements View.OnClickListener {

    private Toolbar toolbar;

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText dateField;
    private AutoCompleteTextView addItems;

    private Integer chosenFoodInfoID = null;
    private String chosenFoodInfoName = null;
    private FoodInfo chosenFoodItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_edit);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("MenuItem",2);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        setUpDatePicker();

        setUpAddItemsAutocomplete();


    }

    // called when a sugested list item is pressed
    public void setChosenFoodItem(FoodInfo foodItem){
        this.chosenFoodItem = foodItem;
    }

    // called when the add to shoppinglist button is clicked
    public void addFoodItemToShoppingList(View v){
        if(chosenFoodInfoID == null){
            // lav toast og sig der skal vaelges fra listen
            addItems.setText("");

        }else{
            // tilfoej vare til listen af FoodInfo items
            addItems.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_list_edit, menu);
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

    public void setUpDatePicker(){
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        dateField = (EditText) findViewById(R.id.deadline);
        dateField.setInputType(InputType.TYPE_NULL);
        dateField.requestFocus();
        dateField.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateField.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setUpAddItemsAutocomplete() {

        addItems = (AutoCompleteTextView) findViewById(R.id.add_item);

        ItemAutoCompleteAdapter adapter = new ItemAutoCompleteAdapter(this,addItems.getText().toString());
        addItems.setAdapter(adapter);
        addItems.setThreshold(1);

        addItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodInfo item =  ((ItemAutoCompleteAdapter)addItems.getAdapter()).foodInfoSuggestions.get(position);
                chosenFoodItem = item;
                System.out.println("chosen food is : "+chosenFoodItem.DanName);

            }
        });

    }

    @Override
    public void onClick(View v) {

        if(v == dateField) {
            fromDatePickerDialog.show();
        }

    }




}
