package com.example.projectsaraAldegaither;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView temperature, description, sunrise,sunset,humid;
    ImageView weatherBackground;
    Button button;
    Spinner spin;
    String group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FirebaseDatabase mDatabase;
        // we"ll make HTTP request to this URL to retrieve weather conditions
        String weatherWebserviceURL =
                "https://api.openweathermap.org/data/2.5/weather?lat=24.68&lon=46.72&appid=070c1556c302bf4092d273caa5ad3853";

        // JSON object that contains weather information
        JSONObject jsonObj;
        //link graphical items to variables
        temperature = (TextView) findViewById(R.id.temperature);
        description = (TextView) findViewById(R.id.description);
        sunrise = (TextView) findViewById(R.id.sunrise);
        sunset = (TextView) findViewById(R.id.sunset);
        humid = (TextView) findViewById(R.id.humid);
        button = (Button) findViewById(R.id.button);
        spin = (Spinner) findViewById(R.id.spinner);
        weather(weatherWebserviceURL);
        weatherBackground = (ImageView) findViewById(R.id.weatherbackground);

        button.setOnClickListener(new View.OnClickListener(){
                                      @Override
                                      public void onClick(View view) {
                                          group = spin.getSelectedItem().toString();
                                          if (group.equals("Riyadh")) {
                                              String url = "https://www.weather-atlas.com/weather/images/city/4/3/2042934-1500-75.jpg";
                                              weather(url);
                                          } else if (group.equals("Athens")) {
                                              String url = "https://www.weather-atlas.com/weather/images/city/7/5/57-1500-75.jpg";
                                              weather(url);
                                          } else if (group.equals("Madrid")) {
                                              String url = "https://cdn.kimkim.com/files/a/images/639730ceb5d75bd65030944924bb2a122c018eaa/big-6630adc36591e7aeb07b23bea1c8c4cd.jpg";
                                              weather(url);
                                          }
                                      }
                                  }
        );
    }
    public void weather(String url){
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SaraAbdulrahman", "Response recieved");
                        Log.d("SaraAbdulrahman",response.toString());
                        try {
                            String town = response.getString("name");
                            Log.d("Sara Town", town);
                            description.setText(town);

                            JSONObject jsonMain = response.getJSONObject("main");
                            JSONObject jsonMain2 = response.getJSONObject("sys");
                            double temp = jsonMain.getDouble("temp");
                            Log.d("Sara", "temp=" + String.valueOf(temp));
                            temperature.setText(String.valueOf(temp));

                            //Here is the Sunrise and sunset code
                            int sr = jsonMain2.getInt("sunrise");
                            String date = new java.text.SimpleDateFormat("HH:mm:ss")
                                    .format(new java.util.Date (sr*1000));
                            sunrise.setText("sunrise: \n"+String.valueOf(date));
                            int ss = jsonMain2.getInt("sunset");
                            String date2 = new java.text.SimpleDateFormat("HH:mm:ss")
                                    .format(new java.util.Date (ss*1000));
                            sunset.setText("sunset: "+String.valueOf(date2));

                            double humidity = jsonMain.getDouble("humidity");
                            humid.setText(String.valueOf(humidity));

                            /* sub categories as JSON arrays */
                            JSONArray jsonArray = response.getJSONArray("weather");
                            Log.d("Sara-array-string", jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.d("Sara-array", jsonArray.getString(i));
                                JSONObject oneObject = jsonArray.getJSONObject(i);
                                String wheater = oneObject.getString("main");
                                Log.d("saldegaither", wheater);
                                if (wheater.equals("Clear")) {
                                    Log.d("Sara","wheater=Clear");
                                    Glide.with(MainActivity.this)
                                            .load("https://i.picsum.photos/id/866/536/354.jpg?hmac=tGofDTV7tl2rprappPzKFiZ9vDh5MKj39oa2D--gqhA")
                                            .into(weatherBackground);
                                } else if (wheater.equals("Clouds")) {
                                    Log.d("Sara","wheater=Clouds");
                                    Glide.with(MainActivity.this)
                                            .load("https://eyesofodysseus.files.wordpress.com/2013/11/wpid-04186_hd.jpg")
                                            .into(weatherBackground);
                                } else if (wheater.equals("Rainy")) {
                                    Glide.with(MainActivity.this)
                                            .load("https://s7d2.scene7.com/is/image/TWCNews/heavy_rain_jpg-6?wid=1250&hei=703&$wide-bg$")
                                            .into(weatherBackground);
                                }
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                            Log.e("JSON error",e.toString());
                        }

                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SaraAbdulrahman", "Error, recieving URL");
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);
    }
}