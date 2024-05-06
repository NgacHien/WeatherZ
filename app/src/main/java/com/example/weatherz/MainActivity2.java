package com.example.weatherz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.PixelCopy;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity2 extends AppCompatActivity {
    String tenthanhpho = "";
    ImageView imgback;
    TextView txtName;
    ListView lv;
    CustomAdapter customAdapter;
    ArrayList<Thoitiet> mangthoitiet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Anhxa();
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        Log.d("ketqua","Du lieu truyen qua : " + city);
        if (city.equals("")){
            tenthanhpho = "Saigon";
            Get7DaysData(tenthanhpho);
        }else {
            tenthanhpho = city;
            Get7DaysData(tenthanhpho);
        }
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void Anhxa() {
        imgback = (ImageView)  findViewById(R.id.imageviewBack);
        txtName = (TextView) findViewById(R.id.textviewTenthanhpho);
        lv = (ListView) findViewById(R.id.listview);
        mangthoitiet = new ArrayList<Thoitiet>();
        customAdapter = new CustomAdapter(MainActivity2.this,mangthoitiet);
        lv.setAdapter(customAdapter);
    }

    private void Get7DaysData(String data) {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=f08a442347154c87a3381652243004&q="+data+"&units=metric&days=7&aqi=no&alerts=no";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectLocation = jsonObject.getJSONObject("location");
                            String name = jsonObjectLocation.getString("name");
                            txtName.setText(name);


                            JSONArray jsonArrayForecast = jsonObject.getJSONObject("forecast").getJSONArray("forecastday");
                            for ( int i =0 ; i<jsonArrayForecast.length();i++){
                                JSONObject jsonObjectForecast = jsonArrayForecast.getJSONObject(i);
                                String ngay = jsonObjectForecast.getString("date");

                                JSONObject jsonObjectTemp = jsonObjectForecast.getJSONObject("day");
                                String max = jsonObjectTemp.getString("maxtemp_c");
                                String min = jsonObjectTemp.getString("mintemp_c");

                                Double a = Double.valueOf(max);
                                Double b = Double.valueOf(min);
                                String NhietdoMax = String.valueOf(a.intValue());
                                String NhietdoMin = String.valueOf(b.intValue());

                                JSONObject jsonArrayWeather = jsonObjectTemp.getJSONObject("condition");
                                String status = jsonArrayWeather.getString("text");
                                String icon = jsonArrayWeather.getString("icon");

                                mangthoitiet.add(new Thoitiet(ngay,status,icon,NhietdoMax,NhietdoMin));

                            }
                            customAdapter.notifyDataSetChanged();



                        } catch (JSONException e) {
                            throw new RuntimeException();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

}