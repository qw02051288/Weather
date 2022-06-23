package com.xiaozi.taiwan.taiwancwb;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class WeatherObject {
    private static ArrayList<HashMap<String, String>> dataResult = new ArrayList<>();
    private String KEY = "CWB-7AAC11ED-B4AE-4314-BB2D-12BC2774BE76";

    public WeatherObject() {
        GET_DATA();
    }

    public static ArrayList<HashMap<String, String>> getDataResult() {
        return dataResult;
    }

    private ArrayList<HashMap<String, String>> setDataResult(ArrayList<HashMap<String, String>> dataResult) {
        if (dataResult.isEmpty()) {
            return null;
        }
        return dataResult;
    }

    private void GET_DATA() {
        String URL = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-D0047-091?Authorization=" + KEY + "&format=JSON&locationName=&elementName=MinCI,MaxCI,MinT,MaxT,PoP12h,Wx";

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();

        Request request = new Request.Builder()
                .url(URL)
                .addHeader("User-Agent:", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36")
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                //nop
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray records = jsonObject.getJSONObject("records").getJSONArray("locations").getJSONObject(0).getJSONArray("location");
                    for (int i = 0; i < records.length(); i++) {
                        for (int j = 0; j < 3; j++) {
                            HashMap<String, String> hashMap = new HashMap<>();
                            String city = String.valueOf(i);
                            String indexInCity = String.valueOf(j);
                            String startTime = records.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(2).getJSONArray("time").getJSONObject(j).getString("startTime");
                            String endTime = records.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(2).getJSONArray("time").getJSONObject(j).getString("endTime");
                            String wx = records.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(2).getJSONArray("time").getJSONObject(j).getJSONArray("elementValue").getJSONObject(0).getString("value");
                            String pop12h = records.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(0).getJSONArray("time").getJSONObject(j).getJSONArray("elementValue").getJSONObject(0).getString("value");
                            String minT = records.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(4).getJSONArray("time").getJSONObject(j).getJSONArray("elementValue").getJSONObject(0).getString("value");
                            String maxT = records.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(5).getJSONArray("time").getJSONObject(j).getJSONArray("elementValue").getJSONObject(0).getString("value");
                            String minCI = records.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(1).getJSONArray("time").getJSONObject(j).getJSONArray("elementValue").getJSONObject(1).getString("value");
                            String maxCI = records.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(3).getJSONArray("time").getJSONObject(j).getJSONArray("elementValue").getJSONObject(1).getString("value");
                            hashMap.put("city", city);
                            hashMap.put("indexInCity", indexInCity);
                            hashMap.put("startTime", startTime);
                            hashMap.put("endTime", endTime);
                            hashMap.put("wx", wx);
                            hashMap.put("pop12h", pop12h);
                            hashMap.put("T", minT + "度 ~ " + maxT+"度");
                            hashMap.put("ci", minCI + "至" + maxCI);
                            Log.e("citycode",city);
                            getDataResult().add(hashMap);
                        }
                    }

                    Comparator<HashMap<String, String>> sort = new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> t1, HashMap<String, String> t2) {
                            Integer city1 = Integer.valueOf(t1.get("city"));
                            Integer city2 = Integer.valueOf(t2.get("city"));
                            return city1.compareTo(city2);
                        }
                    };
                    Collections.sort(getDataResult(), sort);

                    Log.e("test", String.valueOf(dataResult.size()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

