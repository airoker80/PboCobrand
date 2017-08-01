package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.paybyonline.KantipurPay.R;
import com.paybyonline.KantipurPay.imageLoader.ImageLoader;
import com.squareup.picasso.Picasso;


/**
 * Created by Anish on 5/23/2016.
 */
public class ServiceTypeAdapter extends BaseAdapter {

        private Context mContext;
        private final String[] servCat;
        private final String[] Imageid;
        ImageLoader imageloader;


        public ServiceTypeAdapter(Context c, String[] webCat, String[] Imageid) {

            mContext = c;
            this.Imageid = Imageid;
            this.servCat = webCat;
            imageloader = new ImageLoader(c);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return servCat.length;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        public class ViewHolder{

          //  TextView servCatView ;
            ImageView servCatTypeLogo;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View grid;
            ViewHolder holder=new ViewHolder();

            if (convertView == null) {  // if it's not recycled, initialize some attributes

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
                grid = inflater.inflate(R.layout.service_type_layout, parent, false);

            } else {

                grid = (View) convertView;

            }



           // holder.servCatView= (TextView) grid.findViewById(R.id.grid_text1);

            holder.servCatTypeLogo= (ImageView)grid.findViewById(R.id.grid_image);

          //  holder.servCatView.setText(Html.fromHtml(servCat[position]));

            Log.i("Imageid", "" + Imageid[position]);

            if(Imageid[position].length()>0){
                Picasso.with(mContext)
                        .load(Imageid[position])
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
//                    .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
//                .centerInside()
//                .tag(holdItemView.getContext())
                        .into(holder.servCatTypeLogo);
            }


//            imageloader.DisplayImage(Imageid[position], holder.servCatTypeLogo);


            return grid;
        }

    }




