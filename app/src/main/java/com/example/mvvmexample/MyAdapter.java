package com.example.mvvmexample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.mvvmexample.listerners.IClicklistener;
import com.example.mvvmexample.models.Hero;


import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Hero> heros;
Context context;
IClicklistener clicklistener;
public static final String TAG = MyAdapter.class.getSimpleName();
    public MyAdapter(List<Hero> heros, Context context, IClicklistener iClicklistener) {
        this.heros = heros;
        this.context = context;
        this.clicklistener= iClicklistener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.hero_profile,null));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {
         final Hero hero=heros.get(i);
        Log.d(TAG, "onBindViewHolder: " + i + "    count " + heros.size()
                + " hero " + hero.getId());
        holder.publisher.setText(hero.getPublisher());
        holder.realName.setText(hero.getCreatedBy());
        holder.name.setText(hero.getName());
        holder.realName.setText(hero.getRealName());
        Glide.with(context).load(hero.getImageUrl())
                .placeholder(R.drawable.defaultprofile)
                .into(holder.imageView);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+ hero.getId() + " holder " + i);
                clicklistener.onclick(heros.get(i),IClicklistener.DELETE);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicklistener.onclick(heros.get(i),IClicklistener.UPDATE);
            }
        });

    }


    @Override
    public int getItemCount() {
        return heros.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,realName,createdBy,publisher;
        ImageView imageView;
        ImageButton edit,remove;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.name);
            realName=itemView.findViewById(R.id.realname);
            publisher =itemView.findViewById(R.id.publisher);
            createdBy =itemView.findViewById(R.id.createdby);
            imageView = itemView.findViewById(R.id.hero_img);
            edit =itemView.findViewById(R.id.hero_edit);
            remove= itemView.findViewById(R.id.hero_delete);
        }
    }
}
