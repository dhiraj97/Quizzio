package com.example.quizizo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizizo.data.CategoryListAsyncResponse;
import com.example.quizizo.data.QuestionListAsyncResponse;
import com.example.quizizo.data.Repository;
import com.example.quizizo.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Repository().setNumberOfQuestions(2);
        new Repository().setCategory(32);

        new Repository().getQuestions(questionArrayList -> {
            for (Question question : questionArrayList) {
                Log.d("Repo", "Question: " + question.getQuestion());
                Log.d("Repo", "Answer: " + question.isCorrectAnswer() + "");
            }
        });


        new Repository().getCategories(categoryList -> {

            for (String category : categoryList) {
                Log.d("Repo", category);
            }
        });

    }
}