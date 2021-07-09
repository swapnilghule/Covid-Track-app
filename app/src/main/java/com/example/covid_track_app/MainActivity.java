package com.example.covid_track_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_track_app.api.APIUtilities;
import com.example.covid_track_app.api.CountryData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private TextView totalConfirm, totalActive, totalRecovered, totalDeath, totalTests;
    private TextView todayConfirm, todayRecovered,todayDeaths,date;
    private PieChart pieChart;
    String country="India";


    private List<CountryData> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       list= new ArrayList<>();

       if(getIntent().getStringExtra("country") != null)
           country= getIntent().getStringExtra("country");


        init();
        TextView cname=findViewById(R.id.cname);
        cname.setText(country);
        cname.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this,CountryActivity.class)));


        APIUtilities.getApiInterface().getCountryData()
                .enqueue(new Callback<List<CountryData>>() {
                    @Override
                    public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                        list.addAll(response.body());

                        for(int i=0;i<list.size();i++)
                        {
                            if(list.get(i).getCountry().equals(country))
                            {
                                int Todayconfirm= Integer.parseInt(list.get(i).getTodayCases());
                                int active= Integer.parseInt(list.get(i).getActive());
                                int recovred= Integer.parseInt(list.get(i).getRecovered());
                                int death= Integer.parseInt(list.get(i).getDeaths());
                                int confirm= Integer.parseInt(list.get(i).getCases());


                                todayConfirm.setText(NumberFormat.getInstance().format(Todayconfirm));
                                totalActive.setText(NumberFormat.getInstance().format(active));
                                totalRecovered.setText(NumberFormat.getInstance().format(recovred));
                                totalDeath.setText(NumberFormat.getInstance().format(death));


                                todayDeaths.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));
                                totalConfirm.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getCases())));
                                todayRecovered.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));
                                totalTests.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));


                                setText(list.get(i).getUpdated());

                                pieChart.addPieSlice(new PieModel("confirm", confirm,getResources().getColor(R.color.yellow)));
                                pieChart.addPieSlice(new PieModel("active", active,getResources().getColor(R.color.blue_pie)));
                                pieChart.addPieSlice(new PieModel("recovered", recovred,getResources().getColor(R.color.green_pie)));
                                pieChart.addPieSlice(new PieModel("death", death,getResources().getColor(R.color.red_pie)));

                            }
                        }
                    }

                    private void setText(String updated) {
                        DateFormat format= new SimpleDateFormat("yyyy-MM-dd");
                        long milliseconds= Long.parseLong(updated);
                        Calendar calendar=Calendar.getInstance();
                        calendar.setTimeInMillis(milliseconds);

                        date.setText("Updated at "+ format.format(calendar.getTime()));
                    }

                    @Override
                    public void onFailure(Call<List<CountryData>> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"Error"+ t.getMessage(),Toast.LENGTH_LONG);
                    }
                });
    }

    private void init() {

        totalConfirm= findViewById(R.id.totalConfirm);
        totalRecovered= findViewById(R.id.totalRecovered);
        totalDeath=findViewById(R.id.totalDeath);
        totalTests=findViewById(R.id.totalTest);
        todayConfirm=findViewById(R.id.todayConfirm);
        todayDeaths=findViewById(R.id.todayDeath);
        todayRecovered=findViewById(R.id.todayRecovered);
        totalActive=findViewById(R.id.totalActive);
        pieChart=findViewById(R.id.piechart);
        date=findViewById(R.id.date);


    }
}