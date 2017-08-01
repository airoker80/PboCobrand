package com.paybyonline.KantipurPay.Fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.paybyonline.KantipurPay.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    FloatingActionMenu profileOptionsTabMenu;
    FloatingActionButton shippingAddress, billingAddress, paymentProfile, accountInfo,userProfileInfo;

    Fragment fragment;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Profile Fragment");
        // to reset menu item
        setHasOptionsMenu(true);

        profileOptionsTabMenu = (FloatingActionMenu)view.findViewById(R.id.profileOptionsTabMenu);
        shippingAddress = (FloatingActionButton) view.findViewById(R.id.shippingAddress);
        billingAddress = (FloatingActionButton) view.findViewById(R.id.billingAddress);
        paymentProfile = (FloatingActionButton) view.findViewById(R.id.paymentProfile);
        accountInfo = (FloatingActionButton) view.findViewById(R.id.accountInfo);
        userProfileInfo = (FloatingActionButton) view.findViewById(R.id.userProfileInfo);

       // profileOptionsTabMenu.setIconAnimationInterpolator();
        shippingAddress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showProfileTabs("ShippingAddress",false);
            }
        });
        billingAddress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showProfileTabs("BillingAddress",false);
            }
        });
        paymentProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showProfileTabs("PaymentProfile",false);
            }
        });

        accountInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showProfileTabs("AccountInfo",false);
            }
        });


        userProfileInfo.setBackgroundDrawable(getResources().getDrawable(R.drawable.fab_btn));

        userProfileInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showProfileTabs("UserProfileInfo",false);
            }
        });

//        showProfileTabs("ShippingAddress");
        showProfileTabs("UserProfileInfo",true);

        createCustomAnimation();

        return view;
    }

    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(profileOptionsTabMenu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(profileOptionsTabMenu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(profileOptionsTabMenu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(profileOptionsTabMenu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                //here 2nd is when button clicked state
                //first when button idle
                profileOptionsTabMenu.getMenuIconView().setImageResource(profileOptionsTabMenu.isOpened()
                        ? R.mipmap.nav_menu : R.mipmap.navi);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        profileOptionsTabMenu.setIconToggleAnimatorSet(set);
        //when button idle
        profileOptionsTabMenu.getMenuIconView().setImageResource( R.mipmap.nav_menu);
    }

    public void showProfileTabs(String toShowView, Boolean isFirstTime){
        // close floating action menu
        if (!isFirstTime){
            profileOptionsTabMenu.toggle(false);
        }

        String profileViewTag = "";
        switch (toShowView){

            case "UserProfileInfo":
                profileViewTag = "UserProfileInfo";
                fragment = new ProfileUserInfoFragment();
                break;

            case "PaymentProfile":
                profileViewTag = "PaymentProfile";
                fragment = new ProfilePaymentProfileFragment();
                break;

            case "AccountInfo":
                profileViewTag = "AccountInfo";
                fragment = new ProfileAccountInfoFragment();
                break;

            case "ShippingAddress":
                profileViewTag = "ShippingAddress";
                fragment = new ProfileShippingAddressFragment();
                break;

            case "BillingAddress":
                profileViewTag = "BillingAddress";
                fragment = new ProfileBillingAddressFragment();
                break;

            default:
                profileViewTag = "UserProfileInfo";
                fragment = new ProfileUserInfoFragment();
                break;
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame_profile, fragment,String.valueOf(profileViewTag)+"tag");
        fragmentTransaction.commit();

    }

}
