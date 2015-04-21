package com.nutra_o.nutra_o.activitys;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nutra_o.nutra_o.R;
import com.nutra_o.nutra_o.test.ItemAutoCompleteAdapter;
import com.nutra_o.nutra_o.models.ApplicationImpl;
import com.nutra_o.nutra_o.models.ApplicationModel;
import com.nutra_o.nutra_o.test.FoodInfo;
import com.nutra_o.nutra_o.test.ShoppingList;
import com.nutra_o.nutra_o.test.ShoppingListItem;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class IndkoebsListEditActivity extends ActionBarActivity implements View.OnClickListener {

    private Toolbar toolbar;

    // TODO allerede oprettet liste skal have id den i forvejen har

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText dateField;
    private AutoCompleteTextView addItems;

    private FoodInfo chosenFoodItem = null;
    private ArrayList<ShoppingListItem> itemList= new ArrayList<ShoppingListItem>(); // den genererede item liste
    private ListView itemListView;
    private List<String> ietmListViewElements = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private EditText amountData;
    private Spinner sp;
    private CheckBox basal;

    ApplicationImpl application;
    ApplicationModel model;


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
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("MenuItem", 2);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        application = (ApplicationImpl) getApplicationContext();
        model = application.getModel();

        amountData = (EditText) findViewById(R.id.amountData);
        basal = (CheckBox) findViewById(R.id.checkBox);

        setUpDatePicker();

        setUpAddItemsAutocomplete();

        setUpSpinner();

        itemListView = (ListView) findViewById(R.id.itemList);



       adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, android.R.id.text1, ietmListViewElements);

        itemListView.setAdapter(adapter);


    }


    private void setUpSpinner() {
        List<String> list = new ArrayList<String>();
        list.add("kg");
        list.add("g");
        list.add("l");
        list.add("cl");
        list.add("ml");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);

       sp = (Spinner) findViewById(R.id.spinner);
        sp.setAdapter(dataAdapter);

        // Spinner item selection Listener
        //addListenerOnSpinnerItemSelection();

        // Button click Listener
        //addListenerOnButton();
    }

    public void clearEditText(View v ){
        addItems.setText("");
        addItems.setEnabled(true);

    }


    // called when the add to shoppinglist button is clicked
    public void addFoodItemToShoppingList(View v){
        if(chosenFoodItem == null){
            Toast.makeText(getApplicationContext(), "Vælg en vare fra listen",
                    Toast.LENGTH_LONG).show();
            addItems.setText("");
            addItems.setEnabled(true);

        }else if(amountData.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Vælg mængde",
                    Toast.LENGTH_LONG).show();
        }else{
            // tilfoej vare til listen af FoodInfo items
            addItems.setText("");


            ShoppingListItem i = new ShoppingListItem();

            i.Amount = Integer.parseInt(amountData.getText().toString());
            i.Foodinfo = chosenFoodItem.FoodId;
            i.FoodName = chosenFoodItem.DanName;
            i.Basic = basal.isActivated();

            itemList.add(i);
            ietmListViewElements.add(i.Amount+" "+sp.getSelectedItem().toString()+" , "+chosenFoodItem.DanName);
            amountData.setText("");

            chosenFoodItem = null;
            addItems.setEnabled(true);
            // update listView
            adapter.notifyDataSetChanged();
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
        if(id == R.id.save){

            // check if list is ok, and then upload it to db so it gets correct id, the download it and show in lists
            EditText titel = (EditText) findViewById(R.id.navnoverskrift);
            if(titel.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Vælg en tittel",
                        Toast.LENGTH_LONG).show();
            }else{

                final ShoppingList l = new ShoppingList();
                l.name = titel.getText().toString();
                l.itemList = itemList;
                l.executed = false;
                l.userID = model.currentUser.ID;
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = dateFormat.parse(dateField.getText().toString());
                    l.duoDate =  new Timestamp(date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                l.creationTime = new Timestamp(new Date().getTime());

                System.out.println("-------------->");
                System.out.println(l);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean value = application.shoppingListAccesor.postNewShoppingList(l);
                        System.out.println("Succes? : "+value);

                    }
                }).start();
                // TODO nooooo fix post metode!!

            }


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setUpDatePicker(){
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
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
                addItems.setEnabled(false);
                addItems.setTextColor(Color.BLACK);

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
