package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList = findViewById(R.id.listContacts);
                final DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                List<String> everyone = databaseHelper.getAllNames();
                final ArrayAdapter contactArrayAdapter = new ArrayAdapter<String>(
                        MainActivity.this, android.R.layout.simple_list_item_1, everyone);
                contactList.setAdapter(contactArrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addContact:
                Intent intent = new Intent(this, addContact.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}