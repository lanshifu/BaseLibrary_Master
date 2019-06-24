package com.lanshifu.demo_module.bean;

public class WeatherResp {

    /**
     * address : 广东省 深圳市
     * cityCode : 440300
     * temp : 18℃
     * weather : 小雨
     * windDirection : 东北
     * windPower : ≤3级
     * humidity : 92%
     * reportTime : 2018-11-27 22:40:53
     */

    private String address;
    private String cityCode;
    private String temp;
    private String weather;
    private String windDirection;
    private String windPower;
    private String humidity;
    private String reportTime;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindPower() {
        return windPower;
    }

    public void setWindPower(String windPower) {
        this.windPower = windPower;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }
}
