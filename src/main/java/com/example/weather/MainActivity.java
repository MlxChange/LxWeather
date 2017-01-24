package com.example.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.gson.Weather;
import com.example.weather.sercice.MyService;
import com.example.weather.util.HttpUtil;
import com.example.weather.util.Util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //head布局中的所有textview控件
    private TextView head_Wendu,head_city,head_wind,head_shidu,head_Air,head_winName;
    private LinearLayout head_bg;

    //content1中的控件
    private ListView content1_listview;

    //content2中的控件
    private TextView content2_nengjiandu,content2_gailv,content2_sunup,content2_sundown;
    private TextView content2_moonUp,content2_moonDown;

    //content3中的控件
    private CardView cardview;
    private TextView content3_Air;
    private mCirlView content3_cirlView1,content3_mCirlView2;

    //content4中的控件
    private ListView content4_listview;


    //设置content1中listview的adapter
    item1Adapter item1Adapter;
    //设置content4中listview的adapter
    item2Adapter item2Adapter;

    //下拉刷新控件
    public SwipeRefreshLayout swipe;
    //菜单控件
    public DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main);


        //head部分的控件实例化
        head_Wendu= (TextView) findViewById(R.id.wendu);
        head_city= (TextView) findViewById(R.id.head_weather);
        head_wind= (TextView) findViewById(R.id.head_wind);
        head_shidu= (TextView) findViewById(R.id.head_hum);
        head_Air= (TextView) findViewById(R.id.head_Air);
        head_winName= (TextView) findViewById(R.id.head_windName);
        head_bg= (LinearLayout) findViewById(R.id.head_bg);

        //content1控件实例化
        content1_listview= (ListView) findViewById(R.id.content1_listview);
        //content2中控件实例化
        content2_nengjiandu= (TextView) findViewById(R.id.content2_nengjiandu);
        content2_gailv= (TextView) findViewById(R.id.content2_Gailv);
        content2_sunup= (TextView) findViewById(R.id.content2_SunUp);
        content2_sundown= (TextView) findViewById(R.id.content2_SunDown);
        content2_moonUp= (TextView) findViewById(R.id.content2_MoonUp);
        content2_moonDown= (TextView) findViewById(R.id.content2_MoonDown);
        //content3中控件实例化
        cardview= (CardView) findViewById(R.id.content3);
        content3_Air= (TextView) findViewById(R.id.content3_Air);
        content3_cirlView1= (mCirlView) findViewById(R.id.content3_mcilw1);
        content3_mCirlView2= (mCirlView) findViewById(R.id.content3_mcilw2);
        //content4中控件实例化
        content4_listview= (ListView) findViewById(R.id.content4_listview);

        //下拉刷新控件实例化
        swipe= (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setColorSchemeResources(R.color.colorPrimary);

        //菜单控件实例化
        drawer= (DrawerLayout) findViewById(R.id.drawer);

        SharedPreferences preferences=getSharedPreferences("weather",MODE_PRIVATE);
        String weatherString=preferences.getString("weather",null);
        final String weatherID;
        if(weatherString!=null){
            Weather weather= Util.handlerWeatherResponse(weatherString);
            weatherID=weather.basic.weatherID;
            showWeather(weather);

        }else{
            weatherID=getIntent().getStringExtra("weatherID");
            requestWeather(weatherID);
        }

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherID);
            }
        });

        if(Util.isNight()){
            head_bg.setBackgroundResource(R.drawable.night);
            head_Wendu.setTextColor(Color.WHITE);
            head_city.setTextColor(Color.WHITE);
        }else{
            head_bg.setBackgroundResource(R.drawable.sunshine);
            head_Wendu.setTextColor(Color.BLACK);
            head_city.setTextColor(Color.BLACK);
        }

    }


    public void qiehuan(View v){
        drawer.openDrawer(GravityCompat.END);
    }

    public void requestWeather(final String weatherId) {
        String weatherUrl="https://free-api.heweather.com/v5/weather?city="+weatherId+"&key=46f365323eb94498ab007f8fd403973f";
        //Log.i(TAG, "requestWeather: "+weatherUrl);
        HttpUtil.sendOkhttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"获取天气信息失败，" +
                                "请检查网络",Toast.LENGTH_SHORT).show();
                        swipe.setRefreshing(false);
                    }

                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responsestring=response.body().string();
                //Log.i(TAG, "onResponse: "+responsestring);
                final Weather wea=Util.handlerWeatherResponse(responsestring);
                //Log.i(TAG, "wea:"+wea+",weastatus:"+wea.status);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(wea!=null&&wea.status.equals("ok")){
                            SharedPreferences preferences = getSharedPreferences("weather",MODE_PRIVATE);
                            SharedPreferences.Editor edit = preferences.edit();
                            edit.putString("weather",responsestring);
                            edit.commit();
                            Intent intent=new Intent(MainActivity.this, MyService.class);
                            startService(intent);
                            showWeather(wea);
                        }else{
                            Toast.makeText(MainActivity.this,"获取天气信息失败，请检查网络",Toast.LENGTH_SHORT).show();
                        }
                        swipe.setRefreshing(false);
                    }
                });

            }
        });
    }

    private void showWeather(Weather weather){

        //为head部分的控件赋值
            head_Wendu.setText(weather.now.tmp+"°c");//设置head部分温度
            //设置head城市名称和天气状况
            head_city.setText(weather.basic.cityName+" | "+weather.now.cond.txt);
            //设置head部分风向
            head_winName.setText(weather.now.wind.dir);
            //设置head部分风力等级
            head_wind.setText(weather.now.wind.sc+"级");
            //设置head部分相对湿度
            head_shidu.setText(weather.daily_forecast.get(0).Hum);
            //设置head部分空气质量
            if(weather.aqi!=null){
                head_Air.setText(weather.aqi.city.qlty);
            }else{
                head_Air.setText("无数据");
            }

        //设置content1中Listview的数据
            item1Adapter=new item1Adapter(MainActivity.this,weather.daily_forecast);
            content1_listview.setAdapter(item1Adapter);
        //设置content2中的数据
            content2_nengjiandu.setText(weather.daily_forecast.get(0).Vis+"m");
            content2_gailv.setText(weather.daily_forecast.get(0).pop);
            content2_sunup.setText(weather.daily_forecast.get(0).astro.sr);
            content2_sundown.setText(weather.daily_forecast.get(0).astro.ss);
            content2_moonUp.setText(weather.daily_forecast.get(0).astro.mr);
            content2_moonDown.setText(weather.daily_forecast.get(0).astro.ms);
        //设置content3中的数据
            if(weather.aqi==null){
                cardview.setVisibility(View.GONE);
            }else{
                cardview.setVisibility(View.VISIBLE);
                content3_Air.setText(weather.aqi.city.qlty);
                content3_cirlView1.setNumber(Integer.parseInt(weather.aqi.city.aqi));
                content3_mCirlView2.setNumber(Integer.parseInt(weather.aqi.city.pm25));
                content3_cirlView1.notifyDatachange();
                content3_mCirlView2.notifyDatachange();
            }

        //设置content4中的数据
            item2Adapter=new item2Adapter(MainActivity.this,weather.suggesstion);
            content4_listview.setAdapter(item2Adapter);

    }


}
