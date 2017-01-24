package com.example.weather;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.db.City;
import com.example.weather.db.County;
import com.example.weather.db.Province;
import com.example.weather.util.HttpUtil;
import com.example.weather.util.Util;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/17.
 */

public class SelectView extends Fragment {

    public static int Level_Province = 0;
    public static int Level_City = 1;
    public static int Level_County = 2;

    private List<String> mlist = new ArrayList<String>();
    private List<Province> provinceList = new ArrayList<Province>();
    private List<City> cityList = new ArrayList<City>();
    private List<County> countyList = new ArrayList<County>();
    private Province selectProvince;
    private City selectCity;
    private ProgressDialog dialog;
    private int currentLevel = Level_Province;
    private TextView textView;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private static final String TAG = "MainActivity";


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentLevel == Level_Province) {
                    selectProvince = provinceList.get(i);
                    queryCitys();
                } else if (currentLevel == Level_City) {
                    selectCity = cityList.get(i);
                    queryCounty();
                } else if (currentLevel == Level_County) {
                    SharedPreferences preferences = getActivity().
                            getSharedPreferences("weatherID", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = preferences.edit();
                    String weatherid = countyList.get(i).getWeatherId();
                    edit.putString("weatherID", weatherid);
                    edit.commit();
                    if (getActivity() instanceof mainAcvitity) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("weatherID", weatherid);
                        startActivity(intent);
                        getActivity().finish();
                    } else if (getActivity() instanceof MainActivity) {
                        MainActivity activity= (MainActivity) getActivity();
                        activity.drawer.closeDrawers();
                        activity.swipe.setRefreshing(true);
                        activity.requestWeather(weatherid);
                    }

                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLevel == Level_City) {
                    queryProvince();
                } else if (currentLevel == Level_County) {
                    queryCitys();
                }
            }
        });
        queryProvince();
    }

    private void queryCitys() {
        textView.setText(selectProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceID=?", String.valueOf(selectProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
            mlist.clear();
            for (City city :
                    cityList) {
                mlist.add(city.getCityName());
            }
            listView.setSelection(0);
            currentLevel = Level_City;
            adapter.notifyDataSetChanged();
        } else {
            String address = "http:guolin.tech/api/china" + "/" + selectProvince.getProvinceCode();
            QueryFromServce(address, "city");
        }
    }

    private void queryProvince() {
        backButton.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        textView.setText("中国");
        if (provinceList.size() > 0) {
            mlist.clear();
            for (Province province : provinceList) {
                mlist.add(province.getProvinceName());
            }
            listView.setSelection(0);
            currentLevel = Level_Province;
            adapter.notifyDataSetChanged();
        } else {
            String adderss = "http:guolin.tech/api/china";
            QueryFromServce(adderss, "province");
        }


    }

    private void queryCounty() {
        textView.setText(selectCity.getCityName());
        countyList = DataSupport.where("cityID=?", String.valueOf(selectCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            mlist.clear();
            for (County county : countyList
                    ) {
                mlist.add(county.getCountyName());
            }
            listView.setSelection(0);
            currentLevel = Level_County;
            adapter.notifyDataSetChanged();
        } else {
            String address = "http:guolin.tech/api/china/" + selectProvince.getProvinceCode() +
                    "/" + selectCity.getCityCode();
            QueryFromServce(address, "county");
        }

    }

    private void QueryFromServce(String address, final String type) {
        showProdialog();
        HttpUtil.sendOkhttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responsebody = response.body().string();
                //Log.i(TAG, "JSON: "+responsebody);
                boolean result = false;
                if (type.equals("province")) {
                    //Log.i(TAG, "onResponse: "+1);
                    result = Util.SelectProvinceResponse(responsebody);
                } else if (type.equals("city")) {
                    result = Util.SelectCityResponse(responsebody, selectProvince.getId());
                    //Log.i(TAG, "onResponse: "+2);
                } else if (type.equals("county")) {
                    result = Util.SelectCountyResponse(responsebody, selectCity.getId());
                    // Log.i(TAG, "onResponse: "+3);
                }
                // Log.i(TAG, "onResponse: "+result);
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closedialog();
                            if (type.equals("province")) {
                                queryProvince();
                            } else if (type.equals("city")) {
                                queryCitys();
                            } else if (type.equals("county")) {
                                queryCounty();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "加载失败，请检查网络设置", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });


    }

    private void showProdialog() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("正在加载");
        dialog.setTitle("加载中");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void closedialog() {
        dialog.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mfragment, container, false);
        textView = (TextView) view.findViewById(R.id.tv);
        backButton = (Button) view.findViewById(R.id.backbutton);
        listView = (ListView) view.findViewById(R.id.listview);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mlist);
        listView.setAdapter(adapter);
        return view;
    }

}
