package com.example.weather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/1/17.
 */

public class Province extends DataSupport {
    private int id;
    private String ProvinceName;
    private int ProvinceCode;

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }

    public int getProvinceCode() {
        return ProvinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        ProvinceCode = provinceCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
