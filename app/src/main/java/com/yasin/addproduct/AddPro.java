package com.yasin.addproduct;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddPro extends AppCompatActivity
{
    private static final int PICK_IMG = 1;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private int uploads = 0;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private StorageReference mStorageRef;
    int index = 0;
    TextView textView;
    Button chose,send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pro);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User_one");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading ..........");
        textView = findViewById(R.id.text);
        chose = findViewById(R.id.choose);
        send = findViewById(R.id.upload);

        chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, PICK_IMG);
            }
        });

        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                progressDialog.show();
                final StorageReference ImageFolder =  FirebaseStorage.getInstance().getReference().child("ImageFolder");
                for (uploads=0; uploads < ImageList.size(); uploads++) {
                    Uri Image  = ImageList.get(uploads);
                    final StorageReference imagename = ImageFolder.child("image/"+Image.getLastPathSegment());

                    imagename.putFile(ImageList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri)
                                {
                                    String url = String.valueOf(uri);
                                    SendLink(url);
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMG) {
            if (resultCode == RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();

                    int CurrentImageSelect = 0;

                    while (CurrentImageSelect < count) {
                        Uri imageuri = data.getClipData().getItemAt(CurrentImageSelect).getUri();
                        ImageList.add(imageuri);
                        CurrentImageSelect = CurrentImageSelect + 1;
                    }
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("You Have Selected "+ ImageList.size() +" Pictures" );
                    chose.setVisibility(View.GONE);
                }
            }
        }
    }

    private void SendLink(String url)
    {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("link", url);
        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                progressDialog.dismiss();
                textView.setText("Image Uploaded Successfully");
                send.setVisibility(View.GONE);
                ImageList.clear();
            }
        });
    }
}