package com.paybyonline.KantipurPay.Activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.paybyonline.KantipurPay.Fragment.BusinessInfoProfileFragment;
import com.paybyonline.KantipurPay.Fragment.ShippingAddressFragment;
import com.paybyonline.KantipurPay.R;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {


    FloatingActionButton fab;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;
    FloatingActionButton fab4;
    FloatingActionButton fab5;


    //Save the FAB's active status
    //false -> fab = close
    //true -> fab = open
    private boolean FAB_Status = false;

    //Animations
    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;
    Animation show_fab_4;
    Animation hide_fab_4;
    Animation show_fab_5;
    Animation hide_fab_5;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("User Profile");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        //Floating Action Buttons
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab_1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab_2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab_3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab_4);
        fab5 = (FloatingActionButton) findViewById(R.id.fab_5);

        //Animations
        show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        show_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        hide_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);
        show_fab_4 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab4_show);
        hide_fab_4 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab4_hide);
        show_fab_5 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        hide_fab_5 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);


      //ClickListener

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FAB_Status == false) {
                    //Display FAB menu
                    expandFAB();
                    FAB_Status = true;
                } else {
                    //Close FAB menu
                    hideFAB();
                    FAB_Status = false;
                }

            }
        });

        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);
        fab5.setOnClickListener(this);


    }

    private void expandFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);
       // fab1.setOnClickListener(this);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin += (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin += (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(show_fab_3);
        fab3.setClickable(true);

        //Floating Action Button 4
        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) fab4.getLayoutParams();
        layoutParams4.rightMargin += (int) (fab4.getWidth() * 0.25);
        layoutParams4.bottomMargin += (int) (fab4.getHeight() * 1.7);
        fab4.setLayoutParams(layoutParams4);
        fab4.startAnimation(show_fab_4);
        fab4.setClickable(true);

        //Floating Action Button 5
        FrameLayout.LayoutParams layoutParams5 = (FrameLayout.LayoutParams) fab5.getLayoutParams();
        layoutParams5.rightMargin += (int) (fab5.getWidth() * 0.25);
        layoutParams5.bottomMargin += (int) (fab5.getHeight() * 1.7);
        fab5.setLayoutParams(layoutParams5);
        fab5.startAnimation(show_fab_5);
        fab5.setClickable(true);
    }
    private void hideFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(hide_fab_3);
        fab3.setClickable(false);

        //Floating Action Button 4
        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams4.rightMargin -= (int) (fab4.getWidth() * 0.25);
        layoutParams4.bottomMargin -= (int) (fab4.getHeight() * 1.7);
        fab4.setLayoutParams(layoutParams4);
        fab4.startAnimation(hide_fab_4);
        fab4.setClickable(false);

        //Floating Action Button 5
        FrameLayout.LayoutParams layoutParams5 = (FrameLayout.LayoutParams) fab5.getLayoutParams();
        layoutParams5.rightMargin -= (int) (fab5.getWidth() * 0.25);
        layoutParams5.bottomMargin -= (int) (fab5.getHeight() * 1.7);
        fab5.setLayoutParams(layoutParams5);
        fab5.startAnimation(hide_fab_5);
        fab5.setClickable(false);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment=null;
        switch(v.getId()){

            case R.id.fab_1:
                fragment=new ShippingAddressFragment();
                break;

            case R.id.fab_2:
                fragment=new BusinessInfoProfileFragment();
                break;

            case R.id.fab_3:
                fragment=new ShippingAddressFragment();
                break;

            case R.id.fab_4:
                fragment=new ShippingAddressFragment();
                break;

            case R.id.fab_5:
                fragment=new BusinessInfoProfileFragment();
                break;


        }
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.content_frame, fragment);

        fragmentTransaction.commit();
    }
}
