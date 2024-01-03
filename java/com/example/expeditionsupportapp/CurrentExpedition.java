package com.example.expeditionsupportapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentExpedition extends AppCompatActivity {

    // Variables.
    EditText startLocation, endLocation, progressLocation;
    Button openMaps, saveButton, uploadButton;
    TextView progressText;
    ImageView progressImage, uploadImageView;
    RecyclerView imagesView;
    DatabaseReference databaseRef, databaseImagesRef;
    StorageReference storageRef;
    CurrentExpeditionData insertData;
    public Uri imageUri;
    private static final int IMAGE_CODE = 1;
    List<ImageClass> imageClassList;
    CustomAdapter customAdapter;
    Boolean upload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_expedition);

        startLocation = findViewById(R.id.startLocation);
        endLocation = findViewById(R.id.endLocation);
        openMaps = findViewById(R.id.mapsButton);
        saveButton = findViewById(R.id.saveButton);
        uploadButton = findViewById(R.id.uploadButton);
        imagesView = findViewById(R.id.imagesView);

        imagesView.setHasFixedSize(true);
        imagesView.setLayoutManager(new LinearLayoutManager(this));

        imageClassList = new ArrayList<>();

        // Creating the firebase realtime database reference from the app.
        databaseRef = FirebaseDatabase.getInstance().getReference().child("Current Expedition");
        databaseImagesRef = FirebaseDatabase.getInstance().getReference().child("Current Expedition images");

        // Creating the firebase Storage reference from the app.
        storageRef = FirebaseStorage.getInstance().getReference("Current Expedition Images");

        // Method to read the data about locations from firebase database and display in the app.
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    CurrentExpeditionData data = dataSnapshot.getValue(CurrentExpeditionData.class);

                    startLocation.setText(data.getSource());
                    endLocation.setText(data.getDestination());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Method to read the images data from firebase database and display in the app.
        databaseImagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && upload == false)
                {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        ImageClass imageClass = postSnapshot.getValue(ImageClass.class);
                        imageClass.getImageName();
                        imageClassList.add(imageClass);
                    }

                    customAdapter = new CustomAdapter(CurrentExpedition.this, imageClassList);
                    imagesView.setAdapter(customAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Method to open the google maps app on clicking the open in map button.
        openMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sSource = startLocation.getText().toString().trim();
                String sDestination = endLocation.getText().toString().trim();

                if (sSource.equals("")) {
                    startLocation.setError("Start Location cannot be empty!");
                    return;
                }
                if (sDestination.equals("")) {
                    endLocation.setError("End Location cannot be empty!");
                    return;
                }
                else {
                    DisplayTrack(sSource, sDestination);
                }
            }
        });

        // Method to insert the data into the firebase database on clicking the Save button.
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sSource = startLocation.getText().toString().trim();
                String sDestination = endLocation.getText().toString().trim();

                if (sSource.equals("")) {
                    startLocation.setError("Start Location cannot be empty!");
                    return;
                }
                if (sDestination.equals("")) {
                    endLocation.setError("End Location cannot be empty!");
                    return;
                }
                else {
                    PushData(sSource, sDestination);
                }
            }

        });

        // Method to choose an image file to upload in the storage database on clicking Upload Image button.
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FileChooser();
            }
        });

    }

    // Method to display the route from source to destination in google maps.
    private void DisplayTrack(String sSource, String sDestination) {
        try{
            Uri uri = Uri.parse("http://www.google.co.in/maps/dir/" + sSource + "/"
                    + sDestination);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);

            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    // Method to insert the data into the firebase realtime database.
    private void PushData(String source, String destination)
    {
        try{
            insertData = new CurrentExpeditionData(source, destination);
            databaseRef.setValue(insertData);

            Toast.makeText( CurrentExpedition.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();

            startLocation.setText("");
            endLocation.setText("");
        }
        catch (Exception exception)
        {
            Toast.makeText(CurrentExpedition.this, "Error: "+ exception.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    // Method to open a new window to choose image files.
    private void FileChooser()
    {
       Intent intent = new Intent();
       intent.setType("image/*");
       intent.setAction(Intent.ACTION_GET_CONTENT);
       intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
       startActivityForResult(intent, IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK)
        {
            if (data.getClipData() != null)
            {
                int totalItem = data.getClipData().getItemCount();

                for (int i = 0; i < totalItem; i++) {

                    imageUri = data.getClipData().getItemAt(i).getUri();
                    String imageName = getFileName(imageUri);

                    ImageClass imageClass = new ImageClass(imageName, imageUri.toString());
                    imageClassList.add(imageClass);

                    customAdapter = new CustomAdapter(CurrentExpedition.this, imageClassList);
                    imagesView.setAdapter(customAdapter);

                    FileUploader(imageName);
                }
            }
            else if (data.getData() != null)
            {
                imageUri = data.getData();
                String imageName = getFileName(imageUri);

                ImageClass imageClass = new ImageClass(imageName, imageUri.toString());
                imageClassList.add(imageClass);

                customAdapter = new CustomAdapter(CurrentExpedition.this, imageClassList);
                imagesView.setAdapter(customAdapter);

                FileUploader(imageName);
            }
        }
    }

    // Method to extract the file name from the image file.
    private String getFileName(Uri uri)
    {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    // Method to upload the image files into the firebase storage database.
    private void FileUploader(String imageName)
    {
       StorageReference ref = storageRef.child(imageName);

       ref.putFile(imageUri)
               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       upload = true;
                       Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                       while (!uriTask.isSuccessful()) ;
                       Uri downloadUri = uriTask.getResult();
                       String mainUri = downloadUri.toString();

                       String id = databaseImagesRef.push().getKey();

                       Map<String, Object> imageMap = new HashMap<>();
                       imageMap.put("imageUri", mainUri);
                       imageMap.put("imageName", imageName);

                       databaseImagesRef.child(id).setValue(imageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {

                           }
                       });
                   }
                })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(CurrentExpedition.this, "Error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
                   }
               });

    }

}