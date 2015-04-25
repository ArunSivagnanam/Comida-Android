package com.nutra_o.nutra_o.subMenu.tasks;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.support.v7.widget.Toolbar;

import com.nutra_o.nutra_o.R;
import com.nutra_o.nutra_o.activitys.TaskActivity;
import com.nutra_o.nutra_o.models.ApplicationImpl;
import com.nutra_o.nutra_o.models.ApplicationModel;
import com.nutra_o.nutra_o.models.Task;
import com.nutra_o.nutra_o.subMenu.categories.CategoriesTaskListRecycleAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v7.widget.RecyclerView.OnScrollListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    ApplicationImpl application;
    public ApplicationModel model;

    RecyclerView cardList;
    LinearLayoutManager layoutManager;
    TasksTaskListRecycleAdapter adapter;

    CircleImageView addButton;

    ArrayList<Task> taskList = new ArrayList<Task>();

    public TasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_task, container, false);

        application = (ApplicationImpl)getActivity().getApplication();
        model = application.getModel();
        taskList = model.getTaskList();

        cardList = (RecyclerView) v.findViewById(R.id.taskCards_RecyclerView);
        addButton = (CircleImageView) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        cardList.setHasFixedSize(true);
        // set adapter
        adapter = new TasksTaskListRecycleAdapter(taskList,this);
        cardList.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setRecycleChildrenOnDetach(true);
        cardList.setLayoutManager(layoutManager);
        cardList.setItemAnimator(null);

        DragSortRecycler dragSortRecycler = new DragSortRecycler();
        dragSortRecycler.setViewHandleId(R.id.corlorPanel);
        dragSortRecycler.setFloatingAlpha(1f);
        dragSortRecycler.setFloatingBgColor(0x999999);
        dragSortRecycler.setAutoScrollSpeed(0.3f);
        dragSortRecycler.setAutoScrollWindow(0.1f);

        dragSortRecycler.setOnItemMovedListener(new DragSortRecycler.OnItemMovedListener() {
            @Override
            public void onItemMoved(int from, int to) {
                Log.d("TAG", "onItemMoved " + from + " to " + to);
                Task task = taskList.remove(from);
                taskList.add(to, task);

                TasksTaskListRecycleAdapter.ViewHolder fromHolder = ((TasksTaskListRecycleAdapter.ViewHolder)cardList.findViewHolderForPosition(from));
                TasksTaskListRecycleAdapter.ViewHolder toHolder = ((TasksTaskListRecycleAdapter.ViewHolder)cardList.findViewHolderForPosition(to));

                // custom hack for at undgå at de expandede vives swapper forkert
                // søger for de colapser lige meget hvad efter drag
                if(fromHolder.mIsViewExpanded == true ){
                    fromHolder.mIsViewExpanded = false;
                    toHolder.expander.getLayoutParams().height = 0;
                    fromHolder.expander.getLayoutParams().height = 0;
                }else if(toHolder.mIsViewExpanded == true ) {
                    toHolder.mIsViewExpanded = true;
                    fromHolder.expander.getLayoutParams().height = 0;
                    toHolder.expander.getLayoutParams().height = 0;
                }

                Task fromTask = taskList.get(from);
                Task toTask = taskList.get(to);
                fromTask.priorety = toTask.priorety; // opdatere den med det samme

                adapter.notifyDataSetChanged();

                new Thread(new Runnable() { // opdatere resten i baggrunden
                    @Override
                    public void run() {
                        for (int i = 0; i<taskList.size(); i++){
                            Task t = taskList.get(i);
                            t.priorety = i;

                        }
                        Looper.prepare();
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });

                    }
                }).start();

            }
        });

        dragSortRecycler.setOnDragStateChangedListener(new DragSortRecycler.OnDragStateChangedListener() {
            @Override
            public void onDragStart() {
                Log.d("TAG", "Drag Start");
            }

            @Override
            public void onDragStop() {

                Log.d("TAG", "Drag Stop");
            }
        });

        cardList.addItemDecoration(dragSortRecycler);
        cardList.addOnItemTouchListener(dragSortRecycler);
        cardList.setOnScrollListener(dragSortRecycler.getScrollListener());

        cardList.setOnScrollListener(new RecyclerView.OnScrollListener() {

            private static final int HIDE_THRESHOLD = 30;
            private int scrolledDistance = 0;
            private boolean controlsVisible = true;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                RelativeLayout toolbar = (RelativeLayout) getActivity().findViewById(R.id.fakeToolBar);
                Toolbar realToolBar = (Toolbar) getActivity().findViewById(R.id.tool_bar);
                int firstVisibleItem =  ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (firstVisibleItem == 0) {
                    if(!controlsVisible) {
                        onShow(toolbar,realToolBar);
                        controlsVisible = true;
                    }
                } else {
                    if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                        onHide(toolbar,realToolBar);
                        controlsVisible = false;
                        scrolledDistance = 0;
                    } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                        onShow(toolbar,realToolBar);
                        controlsVisible = true;
                        scrolledDistance = 0;
                    }
                }

                if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
                    scrolledDistance += dy;
                }
            }

                public void onHide(RelativeLayout toolbar,  Toolbar realToolBar) {

                    int result = 0;
                    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
                    if (resourceId > 0) {
                        result = getResources().getDimensionPixelSize(resourceId);
                    }

                    toolbar.animate().translationY(-(toolbar.getHeight()-result)).setInterpolator(new AccelerateInterpolator(2));
                    realToolBar.animate().translationY(-realToolBar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
                   //
                   // FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
                    /*
                    int fabBottomMargin = lp.bottomMargin;
                    mFabButton.animate().translationY(mFabButton.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
                    */
                }

                public void onShow(RelativeLayout toolbar,  Toolbar realToolBar){
                    toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
                    realToolBar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

                    }

             });

        return v;
    }


    @Override
    public void onClick(View v) {

        if(v == addButton){
            startActivity(new Intent(getActivity(),TaskActivity.class));
        }
    }
}
