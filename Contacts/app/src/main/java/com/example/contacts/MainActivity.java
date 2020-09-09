package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

                AdapterView.OnItemClickListener contactSelected = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int selected, long l) {
                        Intent intent = new Intent(MainActivity.this, viewContact.class);
                        intent.putExtra("selectedId", selected + 1);
                        startActivity(intent);
                    }
                };

                contactList.setOnItemClickListener(contactSelected);
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