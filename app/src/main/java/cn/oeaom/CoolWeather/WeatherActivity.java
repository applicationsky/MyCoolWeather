package cn.oeaom.CoolWeather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import cn.oeaom.CoolWeather.GSON.Weather;
import cn.oeaom.CoolWeather.Util.Utility;
import okhttp3.Call;
import okhttp3.Callback;

import cn.oeaom.CoolWeather.Util.HttpUtil;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";

    private static final String API_KEY = "bc0418b57b2d4918819d3974ac1285d9";
    //鉴权码

    //天气信息面板所要展现的东西

    public DrawerLayout drawerLayout;              //左侧滑动和点击小房子展现的界面
    //public TextView tvTitle;                      //标题 *弃用
    private TextView weatherTime;                  //天气信息的时间
    private TextView weatherDegree;                 //天气信息的温度值
    private TextView measure2;                      //天气信息的温度单位
    private TextView weatherPlace;                  //天气信息的地点
    private TextView weatherType;                     //天气信息的类型
    private String mWeatherId;                          //城市的编号

    private ImageView weatherStat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/thin.ttf");
        // 字体文件必须是true type font的格式(ttf)；
        // 当使用外部字体却又发现字体没有变化的时候(以 Droid Sans代替)，通常是因为
        // 这个字体android没有支持,而非你的程序发生了错误
        weatherTime = (TextView)findViewById(R.id.weather_info_time);

        weatherTime.setTypeface(fontFace);
//
        weatherDegree  = (TextView)findViewById(R.id.degree_value);

        weatherDegree.setTypeface(fontFace);

        TextView measure = (TextView)findViewById(R.id.degree_measure);

        measure.setTypeface(fontFace);

        measure2 = (TextView)findViewById(R.id.degree_measure2);

        measure2.setTypeface(fontFace);

        weatherPlace = (TextView)findViewById(R.id.weather_info_place);

        weatherPlace.setTypeface(fontFace);

        weatherType = (TextView)findViewById(R.id.weather_info_text);

        weatherType.setTypeface(fontFace);

        weatherStat = (ImageView)findViewById(R.id.weatherIcon);

//
//        TextView weatherInfo = (TextView)findViewById(R.id.weather_info_text);
//
//        weatherInfo.setTypeface(fontFace);
        //
        //text.setTextSize(50);

        Intent intent=getIntent();
        //获取这个Intent对象的Extra中对应键的值
        String weatherId=intent.getStringExtra("weather_id");
        String CountryName = intent.getStringExtra("CountryName");
//         tvTitle = (TextView)findViewById(R.id.title_text_weather);
//        //tvTitle.setText(weatherId);
//        tvTitle.setText(CountryName);
//       // tvTitle.setTextSize(60);
//        tvTitle.setTypeface(fontFace);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

            Button btnBack = (Button)findViewById(R.id.btn_home);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent intent = new Intent(WeatherActivity.this,MainActivity.class);
                    //startActivity(intent);
                   // WeatherActivity.this.finish();
                    drawerLayout.openDrawer(GravityCompat.START);
                    Log.v(TAG,"Clicked nav btn");
                }
            });
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null) {
            // 有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            // 无缓存时去服务器查询天气
            mWeatherId = getIntent().getStringExtra("weather_id");
           // weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }
//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                requestWeather(mWeatherId);
//            }
//        });

    }
//    public void requestWeather(final String weatherId){
//        tvTitle.setText(weatherId);
//    }


    public void requestWeather(final String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key="+API_KEY;
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.v(TAG,"=======================================================================");
                Log.v(TAG,responseText);
                Log.v(TAG,"=======================================================================");
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            //mWeatherId = weather.basic.weatherId;
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        //swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        //swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        //loadBingPic();
    }

    private int findWeatherIconByName(String weatherName)
    {
        switch(weatherName)
        {
            case "晴":return R.drawable.a044;
            case "多云":return R.drawable.a045;
            case "少云":return R.drawable.a046;
            case "晴间多云":return R.drawable.a047;
            case "阴":return R.drawable.a048;
            case "有风":return R.drawable.a049;
            case "平静":return R.drawable.a050;
            case "微风":return R.drawable.a000;
            case "和风":return R.drawable.a001;
            case "清风":return R.drawable.a002;
            case "强风":return R.drawable.a003;
            case "劲风":return R.drawable.a003;
            case "大风":return R.drawable.a004;
            case "烈风":return R.drawable.a005;
            case "风暴":return R.drawable.a006;
            case "狂爆风":return R.drawable.a007;
            case "龙卷风":return R.drawable.a008;
            case "热带风暴":return R.drawable.a009;
            case "阵雨":return R.drawable.a012;
            case "强阵雨":return R.drawable.a013;
            case "雷阵雨":return R.drawable.a014;
            case "强雷阵雨":return R.drawable.a015;
            case "雷阵雨伴有冰雹":return R.drawable.a016;
            case "小雨":return R.drawable.a017;
            case "中雨":return R.drawable.a018;
            case "大雨":return R.drawable.a019;
            case "极端降雨":return R.drawable.a020;
            case "毛毛雨":return R.drawable.a021;
            case "细雨":return R.drawable.a021;
            case "暴雨":return R.drawable.a022;
            case "大暴雨":return R.drawable.a023;
            case "特大暴雨":return R.drawable.a024;
            case "冻雨":return R.drawable.a025;
            case "小雪":return R.drawable.a026;
            case "中雪":return R.drawable.a027;
            case "大雪":return R.drawable.a028;
            case "暴雪":return R.drawable.a029;
            case "雨夹雪":return R.drawable.a030;
            case "雨雪天气":return R.drawable.a031;
            case "阵雨夹雪":return R.drawable.a032;
            case "阵雪":return R.drawable.a033;
            case "薄雾":return R.drawable.a034;
            case "雾":return R.drawable.a035;
            case "霾":return R.drawable.a036;
            case "扬沙":return R.drawable.a037;
            case "浮尘":return R.drawable.a038;
            case "沙尘暴":return R.drawable.a039;
            case "热":return R.drawable.a041;
            case "冷":return R.drawable.a042;
            case "强沙尘暴":return R.drawable.a040;
            case "未知":return R.drawable.a043;
            default:{
                break;
            }
        }
        return -1;
    }

    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature;
        String weatherInfo = weather.now.more.info;
        weatherPlace.setText(cityName);
        weatherTime.setText(updateTime);
        weatherDegree.setText(degree);
        weatherType.setText(weatherInfo);
        weatherStat.setImageResource(findWeatherIconByName(weatherInfo));
//        forecastLayout.removeAllViews();
//        for (Forecast forecast : weather.forecastList) {
//            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
//            TextView dateText = (TextView) view.findViewById(R.id.date_text);
//            TextView infoText = (TextView) view.findViewById(R.id.info_text);
//            TextView maxText = (TextView) view.findViewById(R.id.max_text);
//            TextView minText = (TextView) view.findViewById(R.id.min_text);
//            dateText.setText(forecast.date);
//            infoText.setText(forecast.more.info);
//            maxText.setText(forecast.temperature.max);
//            minText.setText(forecast.temperature.min);
//            forecastLayout.addView(view);
//        }
//        if (weather.aqi != null) {
//            aqiText.setText(weather.aqi.city.aqi);
//            pm25Text.setText(weather.aqi.city.pm25);
//        }
//        String comfort = "舒适度：" + weather.suggestion.comfort.info;
//        String carWash = "洗车指数：" + weather.suggestion.carWash.info;
//        String sport = "运行建议：" + weather.suggestion.sport.info;
//        comfortText.setText(comfort);
//        carWashText.setText(carWash);
//        sportText.setText(sport);
//        weatherLayout.setVisibility(View.VISIBLE);
//        Intent intent = new Intent(this, AutoUpdateService.class);
//        startService(intent);
    }

}
