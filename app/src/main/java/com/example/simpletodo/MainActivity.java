package com.example.simpletodo;
//package com.codepath.rkpandey.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button btnAdd;
    EditText edItem;
    RecyclerView rvItem;

    // declare ItemAdapter
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAdd = findViewById(R.id.btnAdd);
        edItem = findViewById(R.id.edItem);
        rvItem = findViewById(R.id.rvItem);

        //edItem.setText("I'm doing this from Java!");


        //items = new ArrayList<>();
        //items.add("Buy Milk");
        //items.add("Go to the Gym");
        //items.add("Have fun");

        loadItem();

        ItemAdapter.OnLongClickListener onLongClickListener= new ItemAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                // Delete the item from model
                items.remove(position);
                // Notify the adapter
                itemAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was Removed",Toast.LENGTH_SHORT).show();
                saveItems();
            }

        };// end function of variable On_Long

        itemAdapter = new ItemAdapter(items, onLongClickListener);
        rvItem.setAdapter(itemAdapter);
        rvItem.setLayoutManager( new LinearLayoutManager(this));

        // added new item to the lists
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String todoItem= edItem.getText().toString();

                    // Add item to the model
                items.add(todoItem);

                    // Notify adapter that an item is inserted
                itemAdapter.notifyItemInserted(items.size()-1);
                edItem.setText("");

                // show message after successful added
                Toast.makeText(getApplicationContext(),"Item was added",Toast.LENGTH_SHORT).show();
                saveItems();
            }

        });// btnAdd function

    }// protected function

    private File getDataFile(){
        return new File(getFilesDir(),"data.txt");

    }

    // this function will load items by reading every line of the data file
    private void loadItem(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(),Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity","Error reading items",e);
            items=new ArrayList<>();
        }

    }// end private function

    // This function will save items by writing them into the data file
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity","Error writting items",e);
            //items= new ArrayList<>();
        }

    }// end private function

}



