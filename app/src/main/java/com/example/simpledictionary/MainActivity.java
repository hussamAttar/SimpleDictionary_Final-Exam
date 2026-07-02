package com.example.simpledictionary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<WordItem> wordList;
    private WordAdapter adapter;
    private int editingPosition = -1;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            wordList = (ArrayList<WordItem>) savedInstanceState.getSerializable("list");
        } else {
            wordList = new ArrayList<>();
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new WordAdapter(wordList, new WordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                editingPosition = position;
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra("item", wordList.get(position));
                addEditLauncher.launch(intent);
            }

            @Override
            public void onItemLongClick(int position) {
                wordList.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(MainActivity.this, "Word Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            editingPosition = -1;
            addEditLauncher.launch(new Intent(MainActivity.this, AddEditActivity.class));
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private final ActivityResultLauncher<Intent> addEditLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    WordItem item = (WordItem) result.getData().getSerializableExtra("item");
                    if (editingPosition == -1) {
                        wordList.add(item);
                        Toast.makeText(this, "Word Added", Toast.LENGTH_SHORT).show();
                    } else {
                        wordList.set(editingPosition, item);
                        Toast.makeText(this, "Word Updated", Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                }
            });

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", wordList);
    }
}