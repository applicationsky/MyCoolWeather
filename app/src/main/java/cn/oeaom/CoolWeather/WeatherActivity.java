package cn.oeaom.CoolWeather;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";
    public DrawerLayout drawerLayout;
    //public TextView tvTitle;
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
        TextView weatherTime = (TextView)findViewById(R.id.weather_info_time);

        weatherTime.setTypeface(fontFace);
//
        TextView weatherDegree = (TextView)findViewById(R.id.degree_value);

        weatherDegree.setTypeface(fontFace);

        TextView measure = (TextView)findViewById(R.id.degree_measure);

        measure.setTypeface(fontFace);



        
        TextView measure2 = (TextView)findViewById(R.id.degree_measure2);

        measure2.setTypeface(fontFace);

        TextView weatherPlace = (TextView)findViewById(R.id.weather_info_place);

        weatherPlace.setTypeface(fontFace);

        TextView weatherType = (TextView)findViewById(R.id.weather_info_text);

        weatherType.setTypeface(fontFace);
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
    }
//    public void requestWeather(final String weatherId){
//        tvTitle.setText(weatherId);
//    }

}
