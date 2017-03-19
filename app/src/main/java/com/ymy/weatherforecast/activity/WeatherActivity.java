package com.ymy.weatherforecast.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ymy.weatherforecast.R;
import com.ymy.weatherforecast.gson.Forecast;
import com.ymy.weatherforecast.gson.Weather;
import com.ymy.weatherforecast.util.HttpUtil;
import com.ymy.weatherforecast.util.Utility;

import org.xutils.ViewInjector;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    @ViewInject(R.id.weather_layout)
    private ScrollView weatherLayput;
    @ViewInject(R.id.title_city)
    private TextView titeCity;
    @ViewInject(R.id.title_update_time)
    private TextView titleUpdateTime;
    @ViewInject(R.id.degree_text)
    private TextView degreeText;
    @ViewInject(R.id.weather_info_text)
    private TextView weatherInfoText;
    @ViewInject(R.id.forecast_layout)
    private LinearLayout forecastLayout;
    @ViewInject(R.id.aqi_text)
    private TextView aqiText;
    @ViewInject(R.id.pm25_text)
    private TextView pm25Text;
    @ViewInject(R.id.comfort_text)
    private TextView comfortText;
    @ViewInject(R.id.car_wash_text)
    private TextView carWashText;
    @ViewInject(R.id.sport_text)
    private TextView sportText;
    @ViewInject(R.id.drawer_layout)
    public DrawerLayout drawerLayout;
    @ViewInject(R.id.nav_button)
    private Button navButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        x.view().inject(this);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = sp.getString("weather",null);
        if(weatherString != null){
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);

        }else {

            String weatherId = getIntent().getStringExtra("weather_id");
            Log.e("eee",weatherId);
            weatherLayput.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    /***
     * 根据天气Id请求城市天气信息
     * @param weatherId
     */
    public void requestWeather(final String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid="+weatherId+"&key=fc75001c88674b8186530d6af3e43357";

        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weather != null && "ok".equals(weather.status)){
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",responseText);
                            editor.apply();
                            showWeatherInfo(weather);

                        }else {
                            Toast.makeText(WeatherActivity.this,"获取天气信息失败1",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /***
     * 处理并展示Weather 实体类中的数据
     * @param weather
     */
    private void showWeatherInfo(Weather weather) {
      String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature+"'C";
        String weatherInfo = weather.now.more.info;
        titeCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList){
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperture.max);
            minText.setText(forecast.temperture.min);
            forecastLayout.addView(view);
        }
        if(weather.aqi != null){
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度："+weather.suggestion.comform.info;
        String carWash = "洗车指数："+weather.suggestion.carWash.info;
        String sport = "运动建议："+weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayput.setVisibility(View.VISIBLE);
    }
}
