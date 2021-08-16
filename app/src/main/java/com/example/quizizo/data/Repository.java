package com.example.quizizo.data;

import android.text.Html;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.quizizo.controller.AppController;
import com.example.quizizo.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    static String categoryUrl = "https://opentdb.com/api_category.php";
    static int numberOfQuestions = 10;
    static int category = 1;
    static String questionsURL = "https://opentdb.com/api.php?amount=10&type=boolean";
    ArrayList<Question> questionArrayList = new ArrayList<>();

    public static void setNumberOfQuestions(int numberOfQuestions) {
        Repository.numberOfQuestions = numberOfQuestions;
    }

    public static int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public static int getCategory() {
        return category;
    }

    public static void setCategory(int category) {
        Repository.category = category;
    }

    ArrayList<String> categories = new ArrayList<>();
    public List<String> getCategories(final CategoryListAsyncResponse callback) {
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, categoryUrl, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("trivia_categories");
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    categories.add(jsonObject.getString("name"));
                }

                if(callback != null) {
                    callback.categoryArray(categories);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.d("Categories", "Cannot Load Categories!");
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return categories;
    }

    public List<Question> getQuestions(final QuestionListAsyncResponse callBack) {
        questionsURL = "https://opentdb.com/api.php?amount=" + numberOfQuestions + "&category="+category+"&type=boolean";
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, questionsURL, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("results");

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Question question = new Question(Html.fromHtml(jsonObject.getString("question"), Html.FROM_HTML_MODE_COMPACT).toString(),
                            jsonObject.getBoolean("correct_answer"));
                    questionArrayList.add(question);
                }
                if(null != callBack) {
                    callBack.questionArray(questionArrayList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            Log.d("Categories", "Cannot Load Questions!");
        });

       AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }


}
