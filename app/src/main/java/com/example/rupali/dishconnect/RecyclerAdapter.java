package com.example.rupali.dishconnect;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.UserHolder> {
    Context context;
    ArrayList<String> arrayList;
    RecyclerAdapter(Context context,ArrayList<String> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.for_adapter,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, final int position) {
        Uri uri=Uri.parse(arrayList.get(position));
        holder.videoView.setVideoURI(uri);
        // MediaController mediaController=new MediaController(context);
        //  mediaController.setAnchorView(holder.videoView);
        holder.videoView.seekTo(100);
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f,0f);
            }
        });
        holder.videoView.requestFocus();
        // holder.videoView.setMediaController(mediaController);
        holder.videoView.start();
        holder.videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent j=new Intent(context,VideoShowingActivity.class);
                j.putExtra("uri",(arrayList.get(position)));
                context.startActivity(j);
                return true;
            }
        });
    }

//    private String getpath(Uri uri) {
//        String[] projection={MediaStore.Video.Media.DATA};
//        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);
//        if(cursor!=null){
//            int columnindex=cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(columnindex);
//        }
//        else{
//            return null;
//        }
//    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class UserHolder extends RecyclerView.ViewHolder{
        VideoView videoView;
        ImageView imageView;
        public UserHolder(View itemView) {
            super(itemView);
            videoView=itemView.findViewById(R.id.video);
            imageView=itemView.findViewById(R.id.img);
        }

    }
}