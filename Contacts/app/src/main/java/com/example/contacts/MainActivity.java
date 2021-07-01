package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView contactList;
    TextView emptyView;
    SearchView uInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList = findViewById(R.id.listContacts);
        emptyView = findViewById(R.id.emptyView);
        final DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        List<String> everyone = databaseHelper.getAllNames();
        final ArrayAdapter contactArrayAdapter = new ArrayAdapter<String>(
                MainActivity.this, android.R.layout.simple_list_item_1, everyone);
        contactList.setAdapter(contactArrayAdapter);

        AdapterView.OnItemClickListener contactSelected = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int selected, long l) {
                String fullName = (String)contactList.getItemAtPosition(selected);
                String[] separateNames = fullName.split(" ");
                String fName = separateNames[0];
                Intent intent = new Intent(MainActivity.this, viewContact.class);
                intent.putExtra("selectedName", fName);
                startActivity(intent);
            }
        };

        contactList.setOnItemClickListener(contactSelected);
        contactList.setEmptyView(emptyView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);

        MenuItem searchItem = menu.findItem(R.id.searchName);
        SearchView searcher = (SearchView) searchItem.getActionView();
        final DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

        searcher.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                List<String> searchResults = databaseHelper.queryContact(newText);
                final ArrayAdapter contactArrayAdapter = new ArrayAdapter<String>(
                        MainActivity.this, android.R.layout.simple_list_item_1, searchResults);
                contactList.setAdapter(contactArrayAdapter);
                return false;
            }
        });
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addContact:
                Intent intent = new Intent(this, addContact.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}