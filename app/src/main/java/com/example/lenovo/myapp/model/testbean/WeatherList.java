package com.example.lenovo.myapp.model.testbean;

import java.util.List;

/**
 * 天气对象列表
 */
public class WeatherList extends WeatherBase {

    private List<WeatherInfo> result;

    public List<WeatherInfo> getResult() {
        return result;
    }

    public void setResult(List<WeatherInfo> result) {
        this.result = result;
    }
}
