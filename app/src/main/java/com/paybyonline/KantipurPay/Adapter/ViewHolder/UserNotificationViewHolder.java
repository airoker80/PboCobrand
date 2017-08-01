package com.paybyonline.KantipurPay.Adapter.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.paybyonline.KantipurPay.Adapter.Model.UserNotification;
import com.paybyonline.KantipurPay.R;
import com.squareup.picasso.Picasso;

/**
 * Created by mefriend24 on 10/20/16.
 */
public class UserNotificationViewHolder  extends RecyclerView.ViewHolder {


    ImageView notificationLogo;
    TextView notificationCategory;
    TextView notificationMessage;
    TextView notificationDate;

    Context mContext;
    public View holdItemView;

    public UserNotificationViewHolder(View itemView) {
        super(itemView);
        holdItemView=itemView;
        notificationLogo = (ImageView) itemView.findViewById(R.id.notificationLogo);
        notificationCategory = (TextView) itemView.findViewById(R.id.notificationCategory);
        notificationMessage = (TextView) itemView.findViewById(R.id.notificationMessage);
        notificationDate = (TextView) itemView.findViewById(R.id.notificationDate);

    }
    public void bind(final UserNotification model, Context context) {
        notificationCategory.setText(model.getCategory());
        notificationDate.setText(model.getCreatedDate());
        notificationMessage.setText(Html.fromHtml(model.getMessage()));
        Picasso.with(holdItemView.getContext())
                .load(model.getLogo())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(notificationLogo);
    }


}
