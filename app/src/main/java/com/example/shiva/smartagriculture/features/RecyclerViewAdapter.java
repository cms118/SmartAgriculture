package com.example.shiva.smartagriculture.features;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shiva.smartagriculture.R;

import java.util.List;

/**
 * Created by shiva on 25-10-2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    private Context mContext;
    private List<Items> items;

    public RecyclerViewAdapter(Context mContext, List<Items> items) {
        this.mContext = mContext;
        this.items = items;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardtem,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.agrText.setText(items.get(position).getName());
        holder.agrImage.setImageResource(items.get(position).getThumbnail());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DiseaseDetection.class);
                //intent.putExtra("val1",1);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        private ImageView agrImage;
        private TextView agrText;
        private CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            agrImage = (ImageView)itemView.findViewById(R.id.agrImg);
            agrText = (TextView)itemView.findViewById(R.id.agrText);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
        }
    }


}
