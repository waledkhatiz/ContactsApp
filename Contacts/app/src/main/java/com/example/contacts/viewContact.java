package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class viewContact extends AppCompatActivity {

    ImageView avatar;
    TextView names;
    TextView mobile;
    TextView home;
    TextView email;
    DatabaseHelper databaseHelper;
    ContactModel selectedContact;
    Integer selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        avatar = findViewById(R.id.avatar);
        names = findViewById(R.id.nameView);
        mobile = findViewById(R.id.mobileView);
        home = findViewById(R.id.homeView);
        email = findViewById(R.id.emailView);
        avatar.setImageResource(R.drawable.cat);
        databaseHelper = new DatabaseHelper(viewContact.this);

        Intent intent = getIntent();
        selectedId = intent.getIntExtra("selectedId", -1);
        selectedContact = databaseHelper.getContact(selectedId);

        Bitmap bitmap = BitmapFactory.decodeByteArray(selectedContact.getAvatar(), 0, selectedContact.getAvatar().length);
        avatar.setImageBitmap(bitmap);
        names.setText(selectedContact.getfName() + " " +  selectedContact.getlName());
        mobile.setText(selectedContact.getMobile());
        home.setText(selectedContact.getHome());
        email.setText(selectedContact.getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.viewcontactoptions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case(R.id.editContact):
                Intent intent1 = new Intent(viewContact.this, editContact.class);
                intent1.putExtra("selectedId", selectedId);
                startActivity(intent1);
                break;
            case(R.id.delete):
                databaseHelper.deleteContact(selectedId);
                Intent intent2 = new Intent(viewContact.this, MainActivity.class);
                startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }
}