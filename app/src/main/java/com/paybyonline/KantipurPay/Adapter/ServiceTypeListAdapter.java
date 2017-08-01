package com.paybyonline.KantipurPay.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.paybyonline.KantipurPay.Adapter.Model.CheckListModel;
import com.paybyonline.KantipurPay.R;

import java.util.ArrayList;


public class ServiceTypeListAdapter extends ArrayAdapter{


   // List<CheckListModel> list = Collections.emptyList();
    Context context;
     ArrayList<CheckListModel> list=new ArrayList<>();
    String []CatName;
    String []serviceType;
    LayoutInflater inflater;



    public ServiceTypeListAdapter(Context context, ArrayList<CheckListModel> list) {
        super(context, R.layout.service_type_list_layout, list);
        Log.i("test", "test");
        this.context = context;
      // this.list=list;
        this.list.addAll(list);
        inflater = LayoutInflater.from(context);
    }





    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

   @Override
    public CheckListModel getItem(int position) {
        // TODO Auto-generated method stub
        return (CheckListModel)list.get(position);
    }


    @Override
    public long getItemId(int id) {
        // TODO Auto-generated method stub
        return id;
    }

    public class ViewHolder{
        TextView tagName;
        CheckBox check;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
            if (convertView == null) {
            view = inflater.inflate(R.layout.service_type_list_layout , parent, false);
               // CheckBoxInfo item = objects[position];

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.tagName = (TextView)view.findViewById(R.id.label);
            viewHolder.check = (CheckBox)view.findViewById(R.id.check);
           /* viewHolder.check
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            CheckListModel element = (CheckListModel) viewHolder.check
                                    .getTag();
                            element.setSelected(buttonView.isChecked());

                        }
                    })*/;
                viewHolder.check

                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                final boolean isChecked = viewHolder.check.isChecked();
                                //toggleChecked Do something here.
                                CheckListModel element;

                                if (isChecked) {
                                   // objPlace.setItemChecked("yes");
                                     element = (CheckListModel) viewHolder.check
                                            .getTag();
                                    element.setSelected(viewHolder.check.isChecked());
                                }else{
                                   // objPlace.setItemChecked("no");
                                    element = (CheckListModel) viewHolder.check
                                            .getTag();
                                 element.toggleChecked();
                                }



                            }
                        });
            view.setTag(viewHolder);
            viewHolder.check.setTag(list.get(position));

        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).check.setTag(list.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.tagName.setText(list.get(position).getName());
        holder.check.setChecked(list.get(position).isSelected());


        return view;
    }

}
