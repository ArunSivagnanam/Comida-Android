package com.nutra_o.nutra_o.subMenu.tasks;


import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.RippleDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.nutra_o.nutra_o.R;
import com.nutra_o.nutra_o.models.Category;
import com.nutra_o.nutra_o.models.Task;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class TasksTaskListRecycleAdapter extends RecyclerView.Adapter<TasksTaskListRecycleAdapter.ViewHolder>{




    // VIEV HOLDER
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TasksFragment currentFragment;
        TextView taskName;
        RelativeLayout layout;
        LinearLayout expander;
        RelativeLayout colorPanel;
        TextView priorety;
        LinearLayout reminder;
        TextView reminderText;
        LinearLayout deadline;
        TextView deadlineText;
        LinearLayout category;
        TextView categoryText;
        LinearLayout progress;
        TextView estimationText;
        TextView procentageCompleted;
        SeekBar seekBar;

        int mOriginalHeight = 0;
        boolean mIsViewExpanded = false;
        double expandingHeight = 3;



        public ViewHolder(View itemView, TasksFragment currentFragment) {
            super(itemView);
            this.currentFragment = currentFragment;

            taskName = (TextView) itemView.findViewById(R.id.taskName);
            layout = (RelativeLayout) itemView.findViewById(R.id.handle);
            expander = (LinearLayout) itemView.findViewById(R.id.expander);
            priorety = (TextView) itemView.findViewById(R.id.priorety);
            layout.setOnClickListener(this);
            colorPanel = (RelativeLayout) itemView.findViewById(R.id.corlorPanel);
            reminder = (LinearLayout) itemView.findViewById(R.id.reminderLayout);
            deadline = (LinearLayout) itemView.findViewById(R.id.deadlineLayout);
            deadlineText = (TextView) itemView.findViewById(R.id.deadlineText);
            category = (LinearLayout) itemView.findViewById(R.id.categoryLayout);
            categoryText = (TextView) itemView.findViewById(R.id.categoryText);
            reminderText = (TextView) itemView.findViewById(R.id.remindertext);
            progress = (LinearLayout) itemView.findViewById(R.id.progressLayout);
            estimationText = (TextView) itemView.findViewById(R.id.estimationText);
            procentageCompleted = (TextView) itemView.findViewById(R.id.procentageCompleted);
            seekBar = (SeekBar) itemView.findViewById(R.id.seekBar2);

        }


        @Override
        public void onClick(final View view) {

           // currentFragment.getActivity().startActivity(new Intent(currentFragment.getActivity(),test.class));
            System.out.println("yayyyyyyyyyyyyyyyyyyyyyyyyyyyy");


            if (mOriginalHeight == 0) {
                mOriginalHeight = view.getHeight();

            }
            ValueAnimator valueAnimator;
            if (!mIsViewExpanded) {
                mIsViewExpanded = true;
                valueAnimator = ValueAnimator.ofInt(mOriginalHeight, mOriginalHeight + (int) (mOriginalHeight * expandingHeight));
            } else {
                mIsViewExpanded = false;
                valueAnimator = ValueAnimator.ofInt(mOriginalHeight + (int) (mOriginalHeight * expandingHeight), mOriginalHeight);
            }
            valueAnimator.setDuration(200);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    Integer value = (Integer) animation.getAnimatedValue();
                    expander.getLayoutParams().height = value.intValue();
                    expander.requestLayout();
                }
            });
            valueAnimator.start();

        }
    }

    private ArrayList<Task> taskLists;
    TasksFragment currentFragment;


    public TasksTaskListRecycleAdapter(ArrayList<Task> taskList, TasksFragment fragment){

        currentFragment = fragment;
        this.taskLists = taskList;

    }

    @Override
    public TasksTaskListRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(currentFragment.getActivity()).inflate(R.layout.task_card, parent, false);

        return new ViewHolder(itemView,currentFragment);

    }

    // ligger indjoebslist model oplysninger i viewholder dh
    @Override
    public void onBindViewHolder(final TasksTaskListRecycleAdapter.ViewHolder holder, int position) {

        final Task t = taskLists.get(position);
        holder.taskName.setText(t.name);


        // logic for dynamic view setup for task

        if(t.category != null){
            holder.colorPanel.setBackgroundColor(Color.parseColor(t.category.corlorString));
            setUpCategoryPicter(holder.categoryText,holder.category,t,holder.colorPanel);

        }else{ // standard category color
            holder.colorPanel.setBackgroundColor(Color.parseColor("#ffe2e2e2"));

        }

        if(t.deadline == null){

            holder.deadline.setVisibility(View.GONE);
        }else{
            holder.deadline.setVisibility(View.VISIBLE);
            holder.deadlineText.setText(t.deadline.toString().substring(0, 16));
            setUpDeadLinePickerDate(holder.deadlineText, holder.deadline, t);
        }

        if(t.reminder == null){
            holder.reminder.setVisibility(View.GONE);
        }else{
            holder.reminder.setVisibility(View.VISIBLE);
            holder.reminderText.setText(t.reminder.toString().substring(0,16));
            setUpReminderPickerDate(holder.reminderText,holder.reminder,t);
        }

        if(t.category == null){
            holder.category.setVisibility(View.GONE);
        }else{
            holder.category.setVisibility(View.VISIBLE);
            holder.categoryText.setText(t.category.name);
        }

        if(!t.estimated){
            holder.progress.setVisibility(View.GONE);
        }else{
            holder.progress.setVisibility(View.VISIBLE);
            holder.estimationText.setText(t.estimatedDays+"d : "+t.estimateHours+"h : "+t.estimateMin+"m");
            //set progresbar
            holder.seekBar.setProgress(t.procentageCompleted);

            holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    t.procentageCompleted = progress;
                    holder.procentageCompleted.setText(progress+"%");
                    System.out.println("PROGRESS "+progress);
                    notifyDataSetChanged();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }

        holder.priorety.setText("#"+t.priorety);

    }


    // TODO flyt alle picker objecterne in i view holder r h

    public void setUpCategoryPicter(final TextView dialogView, LinearLayout dialogLinearLayout, final Task t, final RelativeLayout colorPanel){

        // custom dialog
        final Dialog dialog = new Dialog(dialogView.getContext());
        dialog.setContentView(R.layout.category_dialog);
        dialog.setTitle("Choose category");


        final TextView text = (TextView) dialog.findViewById(R.id.categoryText);
        text.setText("Choose category");

        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.categoryContainer);
        final Category chosenCategory = new Category();

        for(int i = 0; i<currentFragment.model.categories.size(); i++){
            String color = currentFragment.model.categories.get(i).corlorString;
            final int finalI = i;

            RippleDrawable drawable = (RippleDrawable) currentFragment.getResources().getDrawable(R.drawable.fab);
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
                    text.setText(currentFragment.model.categories.get(finalI).name);
                    chosenCategory.copyOver(currentFragment.model.categories.get(finalI));
                }
            });
        }


        Button dialogButton = (Button) dialog.findViewById(R.id.testButton);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t.category.copyOver(chosenCategory);
                colorPanel.setBackgroundColor(Color.parseColor(t.category.corlorString));
                dialogView.setText(t.category.name);
                notifyDataSetChanged();
                dialog.dismiss();
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

    // TODO flyt alle picker objecterne in i view holder

    public void setUpDeadLinePickerDate(final TextView deadLineView, LinearLayout linearLayout, final Task t){

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
                        t.deadline = Timestamp.valueOf(dateFormatter.format(newDate.getTime()));

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

    // TODO flyt alle picker objecterne in i view holder

    public void setUpReminderPickerDate(final TextView reminderView, LinearLayout linearLayout, final Task t){

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
                        t.reminder = Timestamp.valueOf(dateFormatter.format(newDate.getTime()));

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


    @Override
    public int getItemCount() {
        return taskLists.size();
    }



}
