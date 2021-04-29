package com.example.myapplication;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public boolean isAnswerCheated() {
        return mAnswerCheated;
    }

    public void setAnswerCheated(boolean mAnswerCheated) {
        this.mAnswerCheated = mAnswerCheated;
    }

    private boolean mAnswerCheated;


    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mAnswerCheated = false;
    }

    public boolean isAnswerTrue() {

        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean mAnswerTrue) {

        this.mAnswerTrue = mAnswerTrue;
    }

    public int getTextResID() {

        return mTextResId;
    }

    public void setTextResId(int mTextResId) {

        this.mTextResId = mTextResId;
    }
}
