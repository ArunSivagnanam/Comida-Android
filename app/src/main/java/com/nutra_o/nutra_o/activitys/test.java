package com.nutra_o.nutra_o.activitys;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.RippleDrawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.nutra_o.nutra_o.R;
import com.nutra_o.nutra_o.models.ApplicationImpl;
import com.nutra_o.nutra_o.models.ApplicationModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class test extends ActionBarActivity {

    private Toolbar toolbar;

    ApplicationImpl application;
    ApplicationModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        application = (ApplicationImpl) getApplicationContext();
        model = application.getModel();

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
    }

    public void testClick(View v){

        ColorPickerDialogBuilder
                .with(v.getContext())
                .setTitle("Choose color")
                .initialColor(Color.parseColor("#E76F51"))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        //toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        //changeBackgroundColor(selectedColor);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }


    public void dialogClick(View v){

        // custom dialog
        final Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.category_dialog);
        dialog.setTitle("Choose category");

        // set the custom dialog components - text, image and button
       final TextView text = (TextView) dialog.findViewById(R.id.categoryText);
       text.setText("Choose category");

        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.categoryContainer);

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
                }
            });
        }


        Button dialogButton = (Button) dialog.findViewById(R.id.testButton);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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

    
}
