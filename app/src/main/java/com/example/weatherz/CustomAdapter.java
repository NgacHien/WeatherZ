package com.example.weatherz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Thoitiet> arrayList;

    public CustomAdapter(Context context, ArrayList<Thoitiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.dong_list_view,null);


        Thoitiet thoitiet = arrayList.get(position);

        TextView txtDay = convertView.findViewById(R.id.textviewNgay);
        TextView txtStatus = convertView.findViewById(R.id.textviewTrangthai);
        TextView txtMaxTemp = convertView.findViewById(R.id.textviewMaxTemp);
        TextView txtMinTemp = convertView.findViewById(R.id.textviewMinTemp);

        ImageView imgStatus = (ImageView) convertView.findViewById(R.id.imageviewTrangthai);

        txtDay.setText(thoitiet.Day);
        txtStatus.setText(thoitiet.Status);
        txtMaxTemp.setText(thoitiet.MaxTemp+"℃");
        txtMinTemp.setText(thoitiet.MinTemp+"℃");



        Picasso.get().load("https:"+thoitiet.Image).into(imgStatus);



        return convertView;
    }
}
