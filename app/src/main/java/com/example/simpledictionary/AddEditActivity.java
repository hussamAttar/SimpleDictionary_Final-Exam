package com.example.simpledictionary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {

    private EditText etWord, etMeaning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        etWord = findViewById(R.id.etWord);
        etMeaning = findViewById(R.id.etMeaning);
        Button btnSave = findViewById(R.id.btnSave);

        if (getIntent().hasExtra("item")) {
            WordItem item = (WordItem) getIntent().getSerializableExtra("item");
            assert item != null;
            etWord.setText(item.getWord());
            etMeaning.setText(item.getMeaning());
        }

        btnSave.setOnClickListener(v -> {
            String word = etWord.getText().toString();
            String meaning = etMeaning.getText().toString();

            if (!word.isEmpty() && !meaning.isEmpty()) {
                WordItem resultItem = new WordItem(word, meaning);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("item", resultItem);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}