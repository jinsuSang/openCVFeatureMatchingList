package com.example.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
       TextView textView3 = (TextView) convertView.findViewById(R.id.textView3);
       TextView textView4 = (TextView) convertView.findViewById(R.id.textView4);
       TextView textView5 = (TextView) convertView.findViewById(R.id.textView5);
       TextView textView6 = (TextView) convertView.findViewById(R.id.textView6);
       TextView textView7 = (TextView) convertView.findViewById(R.id.textView7);

        ListComparedItem listComparedItem = comparePhotoList.get(position);
       imageView.setImageBitmap(listComparedItem.getComparedBmp());
       textView1.setText("min: " + listComparedItem.getMin_length() + ", max: " + listComparedItem.getMax_length());
       textView2.setText("matches: " + listComparedItem.getGood_matches() + ", num: " + listComparedItem.getNumber());
       textView3.setText("correlation: " + listComparedItem.getCorrelation());
       textView4.setText("chi-square: "+ listComparedItem.getChi_square());
       textView5.setText("intersection: " + listComparedItem.getIntersection());
       textView6.setText("bhattacharyya: " + listComparedItem.getBhattacharyya());

       boolean finalResult = true;
       if(listComparedItem.getGood_matches() <= 20){
           finalResult = false;
       }
       if(listComparedItem.isHistogramResult() == true){
           finalResult = true;
       }
       textView7.setText("histResult: " + listComparedItem.isHistogramResult() + "finalResult: " + finalResult);




       return convertView;
    }
}
