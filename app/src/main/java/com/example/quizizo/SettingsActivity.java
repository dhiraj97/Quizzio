package com.example.quizizo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.quizizo.data.QuestionListAsyncResponse;
import com.example.quizizo.data.Repository;
import com.example.quizizo.databinding.ActivityMainBinding;
import com.example.quizizo.model.Question;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity{

    Spinner spinnerCategory, spinnerQuestions;
    Button btnSave;
    ActivityMainBinding binding;
    int selectedCategory = 9, numberOfQuestions = 5;
    Integer[] numberOfQuestionsList = {5,10,15,20};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_settings);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerQuestions = findViewById(R.id.spinner_questions);
        btnSave = findViewById(R.id.btnSave);

        ArrayAdapter<Integer> numberOfQuestionsAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, numberOfQuestionsList);
        numberOfQuestionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQuestions.setAdapter(numberOfQuestionsAdapter);


        new Repository().getCategories(categoryList -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(adapter);
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Repository().setCategory(selectedCategory);
                new Repository().setNumberOfQuestions(numberOfQuestions);
            }
        });

        spinnerQuestions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numberOfQuestions = (Integer) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position) {
                    case 0:
                        selectedCategory = 9;
                        break;
                    case 1:
                        selectedCategory = 10;
                        break;
                    case 2:
                        selectedCategory = 11;
                        break;
                    case 3:
                        selectedCategory = 12;
                        break;
                    case 4:
                        selectedCategory = 13;
                        break;
                    case 5:
                        selectedCategory = 14;
                        break;
                    case 6:
                        selectedCategory = 15;
                        break;
                    case 7:
                        selectedCategory = 16;
                        break;
                    case 8:
                        selectedCategory = 17;
                        break;
                    case 9:
                        selectedCategory = 18;
                        break;
                    case 10:
                        selectedCategory = 19;
                        break;
                    case 11:
                        selectedCategory = 20;
                        break;
                    case 12:
                        selectedCategory = 21;
                        break;
                    case 13:
                        selectedCategory = 22;
                        break;
                    case 14:
                        selectedCategory = 23;
                        break;
                    case 15:
                        selectedCategory = 24;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

}