package cn.oeaom.CoolWeather.GSON;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MaiBenBen on 2017/5/11.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
