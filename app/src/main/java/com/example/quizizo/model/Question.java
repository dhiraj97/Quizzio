package com.example.quizizo.model;

import java.util.Arrays;

public class Question {

    private String question;
    private String correctAnswer;
    private String[] options;

    public Question(){}

    public Question(String question, String correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }


    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", options=" + Arrays.toString(options) +
                '}';
    }
}
