package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paybyonline.KantipurPay.Fragment.BookFlightFragment;
import com.paybyonline.KantipurPay.Fragment.NewBookFlightFragment;
import com.paybyonline.KantipurPay.Fragment.RechargeBuyFragment;
import com.paybyonline.KantipurPay.Fragment.SendMoneyFormFragment;
import com.paybyonline.KantipurPay.Fragment.WalletFragment;
import com.paybyonline.KantipurPay.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sameer on 4/17/2017.
 */

public class DashboardGridAdapter extends RecyclerView.Adapter<DashboardGridAdapter.ViewHolder> {
    Context context;
    private final String[] web;
    private final int[] Imageid;

    public DashboardGridAdapter(Context context, String[] web, int[] imageid) {
        this.context = context;
        this.web = web;
        Imageid = imageid;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_grid,parent,false);
        DashboardGridAdapter.ViewHolder viewHolder = new DashboardGridAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.circle_view_icons.setColorFilter(R.color.primary, PorterDuff.Mode.OVERLAY);
        holder.circle_view_icons.setImageResource(Imageid[position]);
        holder.dashboard_text.setText(web[position]);
        holder.circle_view_icons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                AppCompatActivity activity=(AppCompatActivity) context;
                if (position==0){
                    fragment = new WalletFragment();
                    FragmentManager fragmentManager =activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    fragmentTransaction.commit();
                }
                if (position==1){
                    fragment = new SendMoneyFormFragment();
                    FragmentManager fragmentManager =activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    fragmentTransaction.commit();
                }
                if (position==2){
                    fragment = new NewBookFlightFragment();
                    FragmentManager fragmentManager =activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    fragmentTransaction.commit();
                }
                if (position==3){
                    fragment = new RechargeBuyFragment();
                    FragmentManager fragmentManager =activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    fragmentTransaction.commit();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return web.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circle_view_icons;
        TextView dashboard_text;
        public ViewHolder(View itemView) {

            super(itemView);
             circle_view_icons=(CircleImageView)itemView.findViewById(R.id.circle_view_icons);
             dashboard_text=(TextView)itemView.findViewById(R.id.dashboard_text);
        }
    }
}
