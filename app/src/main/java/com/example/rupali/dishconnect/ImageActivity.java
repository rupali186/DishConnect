package com.example.rupali.dishconnect;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {
    Button button,btn2;
    ArrayList<String> arrayList,imagearraylist;
    RecyclerAdapter recyclerAdapter;
    RecyclerAdapter2 recyclerAdapter2;
    RecyclerView recyclerView,recyclerView2;
    Sqllitedatabse sqllitedatabse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

    sqllitedatabse =new Sqllitedatabse(this);
    arrayList=fetchfromdb();
    imagearraylist=fetchimagefromdb();
    recyclerView=findViewById(R.id.recyclerView);
    recyclerView2=findViewById(R.id.recyclerView2);
    recyclerAdapter=new RecyclerAdapter(this,arrayList);
    recyclerAdapter2=new RecyclerAdapter2(this,imagearraylist);
        recyclerView2.setAdapter(recyclerAdapter2);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerAdapter.notifyDataSetChanged();
        recyclerAdapter2.notifyDataSetChanged();
    button=findViewById(R.id.btn);
    btn2=findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            call2();
        }
    });
        button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            call();
        }
    });
}

    private void call2() {
        int permission= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permission2=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission3= 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            permission3 = ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_DOCUMENTS);
        }
        if(permission== PackageManager.PERMISSION_GRANTED&&permission2==PackageManager.PERMISSION_GRANTED){
            startCameraForImage();
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},2);
            }
        }
    }

    private void startCameraForImage() {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        }
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "SELECT IMAGE"), 4);
    }

    public ArrayList<String> fetchfromdb(){
        ArrayList<String> arrayList=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=sqllitedatabse.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(Table.name,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            String s=cursor.getString(cursor.getColumnIndex(Table.urit));
            arrayList.add(s);
        }
        return arrayList;
    }
    public ArrayList<String> fetchimagefromdb(){
        ArrayList<String> arrayList=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=sqllitedatabse.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(Table.imageTablename,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            String s=cursor.getString(cursor.getColumnIndex(Table.urit2));
            arrayList.add(s);
        }
        return arrayList;
    }

    private void call() {
        int permission= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permission2=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission3= 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            permission3 = ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_DOCUMENTS);
        }
        if(permission== PackageManager.PERMISSION_GRANTED&&permission2==PackageManager.PERMISSION_GRANTED){
            startCamera();
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        }
    }
    public void startCamera(){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Do you want to pick from gallery or record a video using camera?").setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                }
                intent.setType("video/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent,"SELECT VIDEO"),3);
            }
        }).setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(i,1);
            }
        });
        AlertDialog alertDialog=alert.create();
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            int r3=-2;
            int grantResult = grantResults[0];
            int res=grantResults[1];
            Log.d("res",res+"");
            if(grantResults.length>2){
                r3=grantResults[2];
            }
            Log.d("r3",r3+"");
            if(grantResult == PackageManager.PERMISSION_GRANTED&&res== PackageManager.PERMISSION_GRANTED){
                startCamera();;
            }else {
                Toast.makeText(this,"Permission Denied by User ! ",Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode==2){
            int r3=-2;
            int grantResult = grantResults[0];
            int res=grantResults[1];
            Log.d("res",res+"");
            if(grantResults.length>2){
                r3=grantResults[2];
            }
            Log.d("r3",r3+"");
            if(grantResult == PackageManager.PERMISSION_GRANTED&&res==PackageManager.PERMISSION_GRANTED){
                startCameraForImage();
            }else {
                Toast.makeText(this,"Permission Denied by User ! ",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SQLiteDatabase sqLiteDatabase=sqllitedatabse.getWritableDatabase();
        if(requestCode==1||requestCode==3){
            Uri uri=data.getData();
//            Log.d("uri",uri.toString());
//            MediaController mediaController=new MediaController(this);
//            mediaController.setAnchorView(videoView);
//            videoView.setMediaController(mediaController);
//            videoView.setVideoURI(uri);
//            videoView.seekTo(100);
            ContentValues contentValues=new ContentValues();
            contentValues.put(Table.urit,uri.toString());
            sqLiteDatabase.insert(Table.name,null,contentValues);
            arrayList.add(uri.toString());
            recyclerAdapter.notifyDataSetChanged();
        }
        if(requestCode==4){
            Uri uri2=data.getData();
            Log.d("uri2",uri2+"");
            ContentValues contentValues=new ContentValues();
            contentValues.put(Table.urit2,uri2.toString());
            sqLiteDatabase.insert(Table.imageTablename,null,contentValues);
            imagearraylist.add(uri2.toString());
            recyclerAdapter2.notifyDataSetChanged();
        }
    }
}
