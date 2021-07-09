package com.example.covid_track_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covid_track_app.api.CountryData;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CountryAdaptor extends RecyclerView.Adapter<CountryAdaptor.CountryViewHolder> {
    private Context context;
    private List<CountryData> list;

    public CountryAdaptor(Context context, List<CountryData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override



    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.country_item_layout,parent,false);
        return new CountryViewHolder(view);
    }

    public void FilterList(List<CountryData> filterlist){
        list= filterlist;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
           CountryData data= list.get(position);

           holder.Ccases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));
           holder.Cname.setText(data.getCountry());
           holder.sno.setText(String.valueOf(position+1));


           Map<String,String> img=data.getCountryInfo();
           Glide.with(context).load(img.get("flag")).into(holder.imageview);
           holder.itemView.setOnClickListener(view -> {
               Intent intent=new Intent(context,MainActivity.class);
               intent.putExtra("country", data.getCountry());
               context.startActivity(intent);
           });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {
        private TextView sno,Cname,Ccases;
        private ImageView imageview;
        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);

            sno= itemView.findViewById(R.id.sno);
            Cname= itemView.findViewById(R.id.cname);
            Ccases= itemView.findViewById(R.id.Countrycases);
            imageview= itemView.findViewById(R.id.flag);
        }
    }
}
