package com.example.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListComparedItem> comparePhotoList = new ArrayList<>();

    public ListViewAdapter(ArrayList<ListComparedItem> comparePhotoList){
        this.comparePhotoList.addAll(comparePhotoList);
    }

    @Override
    public int getCount() {
        return comparePhotoList.size();
    }

    @Override
    public Object getItem(int position) {
        return comparePhotoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final int pos = position;
       final Context context = parent.getContext();

       if(convertView == null){
           LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView = inflater.inflate(R.layout.listview_item, parent, false);
       }

       ImageView imageView = (ImageView) convertView.findViewById(R.id.compareView);
       TextView textView1 = (TextView) convertView.findViewById(R.id.textView1);
       TextView textView2 = (TextView) convertView.findViewById(R.id.textView2);
       ListComparedItem listComparedItem = comparePhotoList.get(position);
       imageView.setImageBitmap(listComparedItem.getComparedBmp());
       textView1.setText("keypoint1: " + listComparedItem.getKeypoint1() + ", keypoint2: " + listComparedItem.getKeypoint2());
       textView2.setText("matches: " + listComparedItem.getGood_matches());

       return convertView;
    }
}
