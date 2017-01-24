package com.example.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.gson.Weather;
import com.example.weather.util.HttpUtil;
import com.example.weather.util.Util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/24.
 */

public class Test extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        textView= (TextView) findViewById(R.id.test);

    }

    public void test(View v){
        String address="https://free-api.heweather.com/v5/weather?city=CN101010100&key=46f365323eb94498ab007f8fd403973f";
        HttpUtil.sendOkhttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(Test.this,"失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String string = response.body().string();
                Weather weather = Util.handlerWeatherResponse(string);
                if(weather==null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("weather是null");
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("weather不是null");
                        }
                    });
                }
            }
        });
    }
}
