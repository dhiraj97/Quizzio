package com.example.quizizo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.quizizo.data.CategoryListAsyncResponse;
import com.example.quizizo.data.QuestionListAsyncResponse;
import com.example.quizizo.data.Repository;
import com.example.quizizo.databinding.ActivityMainBinding;
import com.example.quizizo.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";
    private ActivityMainBinding binding;
    private List<Question> questionsList;

    int currentQuestionIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        new Repository().setNumberOfQuestions(2);
        new Repository().setCategory(32);


        questionsList = new Repository().getQuestions(questionArrayList -> {
            updateQuestion();

            for (int i = 0; i < questionArrayList.size(); i++) {
                Log.d(TAG, questionArrayList.get(i).toString());
            }
        });

        new Repository().getCategories(categoryList -> {

            for (String category : categoryList) {
                Log.d("Repo", category);
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex = (currentQuestionIndex + 1) % questionsList.size();
                updateQuestion();
            }
        });

        binding.btnOption1.setOnClickListener(v -> {
            checkAnswer(binding.btnOption1.getText().toString());
        });
        binding.btnOption2.setOnClickListener(v -> {
            checkAnswer(binding.btnOption2.getText().toString());
        });
        binding.btnOption3.setOnClickListener(v -> {
            checkAnswer(binding.btnOption3.getText().toString());
        });
        binding.btnOption4.setOnClickListener(v -> {
            checkAnswer(binding.btnOption4.getText().toString());
        });
    }

    private void checkAnswer(String userChoice) {
        String answer = questionsList.get(currentQuestionIndex).getCorrectAnswer();
        if(userChoice.equals(answer)) {
            Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateQuestion() {
        Log.d(TAG, "updateQuestion: "+ questionsList.size());
        binding.txtQuestionNumber.setText("Question "+ (currentQuestionIndex+1) + "/" + questionsList.size());
        binding.txtQuestion.setText(questionsList.get(currentQuestionIndex).getQuestion());
        binding.btnOption1.setText(questionsList.get(currentQuestionIndex).getOptions(0));
        binding.btnOption2.setText(questionsList.get(currentQuestionIndex).getOptions(1));
        binding.btnOption3.setText(questionsList.get(currentQuestionIndex).getOptions(2));
        binding.btnOption4.setText(questionsList.get(currentQuestionIndex).getOptions(3));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}