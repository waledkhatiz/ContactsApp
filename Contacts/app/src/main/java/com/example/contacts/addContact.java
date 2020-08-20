package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class addContact extends AppCompatActivity {

    final static int PICK_IMAGE = 1;
    ImageView displayPic;
    EditText fName;
    EditText lName;
    EditText mobile;
    EditText home;
    EditText email;
    ContactModel contactModel;
    DatabaseHelper dataBaseHelper;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        displayPic = findViewById(R.id.avatar);
        fName = findViewById(R.id.firstNameInput);
        lName = findViewById(R.id.lastNameInput);
        mobile = findViewById(R.id.mobileInput);
        home = findViewById(R.id.homeInput);
        email = findViewById(R.id.emailInput);
        displayPic.setImageResource(R.drawable.cat);
        dataBaseHelper = new DatabaseHelper(addContact.this);

        displayPic.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Image"), PICK_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                displayPic.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addcontactoptions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                try {
                    contactModel = new ContactModel(-1, getByteFromDrawable(displayPic.getDrawable()),
                            fName.getText().toString(), lName.getText().toString(), mobile.getText().toString(),
                            home.getText().toString(), email.getText().toString());
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } catch(Exception e) {
                    Toast.makeText(addContact.this, "Failed", Toast.LENGTH_SHORT).show();
                    contactModel = new ContactModel(-1, null, "error",
                            "error", "error", "error", "error");
                    System.out.println(e.getMessage());
                }

                boolean result = dataBaseHelper.addContact(contactModel);
                Toast.makeText(addContact.this, "Success: " + result, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //image is saved in database as byte[] but retrieved from the ImageView as either Bitmap or Drawable.
    private byte[] getByteFromDrawable(Drawable image) {
        Bitmap bitmap = ((BitmapDrawable)image).getBitmap();
        return getByteFromBitmap(bitmap);
    }

    private byte[] getByteFromBitmap(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
}