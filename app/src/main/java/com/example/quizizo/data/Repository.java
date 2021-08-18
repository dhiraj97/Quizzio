package com.example.quizizo.data;

import android.text.Html;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.quizizo.MainActivity;
import com.example.quizizo.controller.AppController;
import com.example.quizizo.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    static String categoryUrl = "https://opentdb.com/api_category.php";
    static int numberOfQuestions = 2;
    static int category = 32;
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

    //Passing a asynchronous class object because of avoiding inconsistencies in data while fetching api
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
        questionsURL = "https://opentdb.com/api.php?amount=" + numberOfQuestions + "&category="+category+"&type=multiple";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, questionsURL, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("results");

                String[] optionsArray = new String[4];
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.d("JsonObject", "getQuestions: "+ jsonObject.toString());
                    Log.d("JsonObject", "getQuestions: "+ jsonObject.getString("incorrect_answers"));
                    String quest = Html.fromHtml(jsonObject.getString("question"), Html.FROM_HTML_MODE_COMPACT).toString();
                    String answer = Html.fromHtml(jsonObject.getString("correct_answer"), Html.FROM_HTML_MODE_COMPACT).toString();
                    Question question = new Question(quest,answer);
                    JSONArray incorrectAnswers = jsonObject.getJSONArray("incorrect_answers");

                    //putting the correct answer on first index
                    optionsArray[0] = answer;

                    //putting incorrect answers on next indexes
                    for (int j = 0; j < incorrectAnswers.length(); j++) {
                        optionsArray[j+1] = incorrectAnswers.getString(j);
                    }

                    // shuffling options
                    shuffle(optionsArray);

                    //setOptions
                    question.setOptions(optionsArray);

                    Log.d("questions"+i, question.toString());


                    questionArrayList.add(question);

                }
                if(null != callBack) {
                    callBack.questionArray(questionArrayList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            Log.d("Questions", "Cannot Load Questions!");
        });

       AppController.getInstance().addToRequestQueue(jsonObjectRequest);

        return questionArrayList;
    }

    private void shuffle(String[] optionsArray) {
        Log.d("inside shuffle", optionsArray[0]);
        for (int i = 0; i < optionsArray.length; i++) {
            int s = i *  (int) (Math.random() * (optionsArray.length - i));

            String temp = optionsArray[s];
            optionsArray[s] = optionsArray[i];
            optionsArray[i] = temp;

        }
    }
}
