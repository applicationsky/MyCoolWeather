package cn.oeaom.CoolWeather;

import android.animation.Animator;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Transition;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import cn.oeaom.CoolWeather.JavaBeanForDB.City;
import cn.oeaom.CoolWeather.JavaBeanForDB.Country;
import cn.oeaom.CoolWeather.JavaBeanForDB.Province;
import cn.oeaom.CoolWeather.Util.HttpUtil;
import cn.oeaom.CoolWeather.Util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by MaiBenBen on 2017/5/5.
 */

public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTRY =2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    private List<Province> provinceList;
    private List<City> cityList;
    private List<Country> countryList;
    private Province selectedProvince;
    private City selectedCity;
    private int currentLevel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.choose_area,container,false);
        titleText = (TextView)view.findViewById(R.id.title_text);
        backButton = (Button)view.findViewById(R.id.back_button);
        listView = (ListView)view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> psrent, View view, int position, long id) {
                if(currentLevel==LEVEL_PROVINCE){
                    selectedProvince = provinceList.get(position);
                    //TODO
                    queryCities();
                }
                else if(currentLevel == LEVEL_CITY){
                    selectedCity = cityList.get(position);
                    //TODO
                    queryCountries();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(currentLevel == LEVEL_COUNTRY){
                    //TODO
                    queryCities();
                }else if(currentLevel == LEVEL_CITY){
                    //TODO
                    queryProvinces();
                }
            }
        });
        //TODD
        queryProvinces();

    }
    private void queryProvinces(){
        titleText.setText("中国");;
        backButton.setVisibility(View.GONE);
        SQLiteDatabase db = Connector.getDatabase();
//        try{
               provinceList = DataSupport.findAll(Province.class);
//        }catch(RuntimeException e){
//            e.printStackTrace();
//        }

        if(provinceList.size()>0){
            dataList.clear();
            for(Province province : provinceList)
            {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        }else{
            String address = "http://guolin.tech/api/china";
            queryFromServer(address,LEVEL_PROVINCE);
        }
    }
    private void queryCities(){
        titleText.setText(selectedProvince.getProvinceName());;
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceid = ?",String.valueOf(selectedProvince.getId())).find(City.class);
        if(cityList.size()>0){
            dataList.clear();
            for(City city : cityList)
            {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        }else{
            int provinceCode = selectedProvince.getProvinceCode();

            String address = "http://guolin.tech/api/china/"+provinceCode;
            queryFromServer(address,LEVEL_CITY);
        }
    }
    private void queryCountries(){
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countryList = DataSupport.where("cityid = ?",String.valueOf(selectedCity.getId())).find(Country.class);
        if(countryList.size()>0){
            dataList.clear();
            for(Country country : countryList)
            {
                dataList.add(country.getCountryName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTRY;
        }else{
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();

            String address = "http://guolin.tech/api/china/"+provinceCode+"/"+cityCode;
            queryFromServer(address,LEVEL_COUNTRY);
        }
    }
    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        if(progressDialog!=null)
        {
            progressDialog.dismiss();
        }
    }

    private void queryFromServer(String address,final int type)
    {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                        }
                    });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr =  response.body().string();
                boolean result  = false;
                switch (type)
                {
                    case LEVEL_PROVINCE:{
                        result = Utility.handleProvinceResponse(responseStr);
                    }break;
                    case LEVEL_CITY:{
                        result = Utility.handleCityResponse(responseStr,selectedProvince.getId());
                    }break;
                    case LEVEL_COUNTRY:{
                        result = Utility.handleCityResponse(responseStr,selectedCity.getId());
                    }break;
                    default:{

                    }break;
                }
                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            switch (type)
                            {
                                case LEVEL_PROVINCE:{
                                    queryProvinces();
                                }break;
                                case LEVEL_CITY:{
                                    queryCities();
                                }break;
                                case LEVEL_COUNTRY:{
                                   queryCountries();
                                }break;
                                default:{

                                }break;
                            }
                        }
                    });
                }
            }
        });
    }

}
