package com.xiaozi.taiwan.taiwancwb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class DataResult extends AppCompatActivity {

    private Spinner spinnerCity;
    private RecyclerView recycleView;
    private String[] cityName = new String[]{"新竹縣", "金門縣", "苗栗縣", "新北市", "宜蘭縣", "雲林縣", "臺南市", "高雄市", "彰化縣",
            "臺北市", "南投縣", "澎湖縣", "基隆市", "桃園市", "花蓮縣", "連江縣", "臺東縣", "嘉義市", "嘉義縣", "屏東縣", "臺中市", "新竹市"};
    private ArrayList<HashMap<String, String>> data = new ArrayList<>();
    private ArrayList<HashMap<String, String>> allData = new ArrayList<>();
    private Card_item_Adapter card_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_result);

        allData = WeatherObject.getDataResult();
        getId();

//        recycleView.setLayoutManager(new LinearLayoutManager(this));
//        card_item_adapter = new Card_item_Adapter();
//        recycleView.setAdapter(card_item_adapter);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cityName);
        spinnerCity.setAdapter(adapter);
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                data.clear();
                for (int j = 0; j < allData.size(); j++) {
                    if (allData.get(j).get("city").equals(String.valueOf(i))) {
                        data.add(allData.get(j));
                    }
                }
                recycleView.setLayoutManager(new LinearLayoutManager(DataResult.this));
                card_item = new Card_item_Adapter();
                recycleView.setAdapter(card_item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nop
            }
        });
    }

    private void getId() {
        spinnerCity = findViewById(R.id.spinnerCity);
        recycleView = findViewById(R.id.recycleView);
    }

    private class Card_item_Adapter extends RecyclerView.Adapter<Card_item_Adapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tvTime, tvWx, tvT, tvPop, tvCi;
            private View mView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTime = itemView.findViewById(R.id.tvTime);
                tvWx = itemView.findViewById(R.id.tvWx);
                tvT = itemView.findViewById(R.id.tvT);
                tvPop = itemView.findViewById(R.id.tvPop);
                tvCi = itemView.findViewById(R.id.tvCi);
                mView = itemView;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvTime.setText(data.get(position).get("startTime") + " ~ " + data.get(position).get("endTime"));
            holder.tvWx.setText(data.get(position).get("wx"));
            holder.tvT.setText(data.get(position).get("T"));
            holder.tvPop.setText(data.get(position).get("pop12h")+"%");
            holder.tvCi.setText(data.get(position).get("ci"));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

}