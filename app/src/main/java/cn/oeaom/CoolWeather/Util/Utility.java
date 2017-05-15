package cn.oeaom.CoolWeather.Util;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.oeaom.CoolWeather.GSON.Weather;
import cn.oeaom.CoolWeather.JavaBeanForDB.City;
import cn.oeaom.CoolWeather.JavaBeanForDB.Country;
import cn.oeaom.CoolWeather.JavaBeanForDB.Province;

/**
 * Created by MaiBenBen on 2017/5/4.
 */

public class Utility {

    public static Weather handleWeatherResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static boolean handleProvinceResponse(String response)
    {
        if(!TextUtils.isEmpty(response))
        {
            try{
                JSONArray allProvinces = new JSONArray(response);
                for(int i = 0;i<allProvinces.length();i++)
                {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            }catch(JSONException e){
            e.printStackTrace();
        }
        }
        return false;
    }

    public static boolean handleCityResponse(String response,int provinceId)
    {
        if(!TextUtils.isEmpty(response))
        {
            try{
                JSONArray allCitys = new JSONArray(response);
                for(int i = 0;i<allCitys.length();i++)
                {
                    JSONObject cityObject = allCitys.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean handleCountryResponse(String response,int cityId)
    {
        if(!TextUtils.isEmpty(response))
        {
            try{
                JSONArray allCountries = new JSONArray(response);
                for(int i = 0;i<allCountries.length();i++)
                {
                    JSONObject countryObject = allCountries.getJSONObject(i);
                    Country country = new Country();
                    country.setCountryName(countryObject.getString("name"));
                    country.setWeatherId(countryObject.getString("weather_id"));
                    country.setCityId(cityId);
                    country.save();
                }
                return true;
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
}
