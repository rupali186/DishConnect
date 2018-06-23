package com.example.rupali.dishconnect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.UserHolder2> {
    ArrayList<String> arrayList;
    Context context;
    RecyclerAdapter2(Context context,ArrayList<String> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public UserHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.for_image_adapter,parent,false);
        return new RecyclerAdapter2.UserHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder2 holder, final int position) {
        final Uri uri= Uri.parse(arrayList.get(position));
        holder.image.setImageURI(uri);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(context,ImageActivity.class);
                j.putExtra("image",uri);
                context.startActivity(j);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class UserHolder2 extends RecyclerView.ViewHolder {
        ImageView image,img2;
        public UserHolder2(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
        }
    }
}
