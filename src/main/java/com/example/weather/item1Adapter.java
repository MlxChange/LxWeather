package com.example.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.gson.Daily_forecast;
import com.example.weather.util.Util;

import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */

public class item1Adapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Daily_forecast> mdily;

    public item1Adapter(Context context, List<Daily_forecast> mdily) {
        inflater = LayoutInflater.from(context);
        this.mdily = mdily;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return mdily.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) convertView.findViewById(R.id.item_img);
            viewHolder.day = (TextView) convertView.findViewById(R.id.item_day);
            viewHolder.content = (TextView) convertView.findViewById(R.id.item_content);
            viewHolder.wendu = (TextView) convertView.findViewById(R.id.item_wendu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String weather = mdily.get(position).cond.txtn;
        String max=mdily.get(position).tmp.max;
        String min=mdily.get(position).tmp.min;
        String qingtian = "晴";
        String yu="雨";
        String yun="云";
        String xue="雪";
        String yin="阴";

        if(position==0){
            viewHolder.day.setText("今天");
        }else if(position==1){
            viewHolder.day.setText("明天");
        }else{
            viewHolder.day.setText("后天");
        }

        if (Util.isNight() && qingtian.contains(weather)) {
            viewHolder.img.setImageResource(R.mipmap.moon);
        } else {
            if(weather.contains(qingtian)){
                viewHolder.img.setImageResource(R.mipmap.sun);
            }else if(weather.contains(yu)){
                viewHolder.img.setImageResource(R.mipmap.rain);
            }else if(weather.contains(yun)){
                viewHolder.img.setImageResource(R.mipmap.cloud);
            }else if(weather.contains(xue)){
                viewHolder.img.setImageResource(R.mipmap.snow);
            }else if (weather.contains(yin)){
                viewHolder.img.setImageResource(R.mipmap.cloud);
            }
        }
        viewHolder.content.setText(weather);
        viewHolder.wendu.setText(max+"° / "+min+"°");
        return convertView;
    }

    private class ViewHolder {
        private ImageView img;
        private TextView day, content, wendu;

    }
}
