package com.example.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.gson.Suggesstion;

/**
 * Created by Administrator on 2017/1/23.
 */

public class item2Adapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Suggesstion suggesstion;

    public item2Adapter(Context context,Suggesstion suggesstion) {
        inflater=LayoutInflater.from(context);
        this.suggesstion=suggesstion;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return suggesstion;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewhoder viewhoder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item2,parent,false);
            viewhoder=new Viewhoder();
            viewhoder.img= (ImageView) convertView.findViewById(R.id.item2_img);
            viewhoder.title= (TextView) convertView.findViewById(R.id.item2_Title);
            viewhoder.content= (mTextview) convertView.findViewById(R.id.item2_content);
            convertView.setTag(viewhoder);
        }else{
            viewhoder= (Viewhoder) convertView.getTag();
        }
        switch (position){
            case 0:{
                viewhoder.img.setImageResource(R.mipmap.people);
                viewhoder.title.setText(suggesstion.comf.Brf);
                viewhoder.content.setText(suggesstion.comf.txt);
                break;
            }
            case 1:{
                viewhoder.img.setImageResource(R.mipmap.car);
                viewhoder.title.setText(suggesstion.cw.Brf);
                viewhoder.content.setText(suggesstion.cw.txt);
                break;
            }
            case 2:{
                viewhoder.img.setImageResource(R.mipmap.cloth);
                viewhoder.title.setText(suggesstion.drsg.Brf);
                viewhoder.content.setText(suggesstion.drsg.txt);
                break;
            }
            case 3:{
                viewhoder.img.setImageResource(R.mipmap.sport);
                viewhoder.title.setText(suggesstion.sport.Brf);
                viewhoder.content.setText(suggesstion.sport.txt);
                break;
            }
            case 4:{
                viewhoder.img.setImageResource(R.mipmap.uv);
                viewhoder.title.setText(suggesstion.uv.Brf);
                viewhoder.content.setText(suggesstion.uv.txt);
                break;
            }
        }
        return convertView;
    }

    private class Viewhoder{
        public ImageView img;
        public TextView title;
        public mTextview content;
    }
}
