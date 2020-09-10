package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class editContact extends AppCompatActivity {

    final static int PICK_IMAGE = 1;
    ImageView avatar;
    TextView fName;
    TextView lName;
    TextView mobile;
    TextView home;
    TextView email;
    Uri imageUri;
    DatabaseHelper databaseHelper;
    ContactModel selectedContact = new ContactModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        avatar = findViewById(R.id.avatar);
        fName = findViewById(R.id.firstNameInput);
        lName = findViewById(R.id.lastNameInput);
        mobile = findViewById(R.id.mobileInput);
        home = findViewById(R.id.homeInput);
        email = findViewById(R.id.emailInput);
        databaseHelper = new DatabaseHelper(editContact.this);

        avatar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Image"), PICK_IMAGE);
            }
        });

        Intent intent = getIntent();
        Integer selectedId = intent.getIntExtra("selectedId", -1);
        selectedContact = databaseHelper.getContact(selectedId);

        Bitmap bitmap = BitmapFactory.decodeByteArray(selectedContact.getAvatar(), 0, selectedContact.getAvatar().length);
        avatar.setImageBitmap(bitmap);
        fName.setText(selectedContact.getfName());
        lName.setText(selectedContact.getlName());
        mobile.setText(selectedContact.getMobile());
        home.setText(selectedContact.getHome());
        email.setText(selectedContact.getEmail());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                avatar.setImageBitmap(bitmap);

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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case(R.id.save):
                selectedContact.setAvatar(getByteFromDrawable(avatar.getDrawable()));
                selectedContact.setfName(fName.getText().toString());
                selectedContact.setlName(lName.getText().toString());
                selectedContact.setMobile(mobile.getText().toString());
                selectedContact.setHome(home.getText().toString());
                selectedContact.setEmail(email.getText().toString());

                databaseHelper.editContact(selectedContact);
                Intent intent = new Intent(editContact.this, MainActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

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