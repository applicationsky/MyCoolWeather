package cn.oeaom.CoolWeather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.litepal.tablemanager.Connector;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String Data[] = {"浙江","江苏","上海","安徽","江西","福建"};
    //private List<String> Citys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getString("weather", null) != null) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }

//        setContentView(R.layout.listviewlay);
//        final ListView listview = (ListView)findViewById(R.id.list_view111);
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,Data);
//        listview.setAdapter(adapter);
//
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View view, int position,
//                                    long id) {
//              //  adapter.clear();
////                1adapter.addAll();
//               // listview.setAdapter();
//                Toast.makeText(getApplicationContext(),"你点击了"+Data[position],Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }
}
