package com.example.myapplication;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAnswerCheated;

    public boolean isAnswerCheated() {

        return mAnswerCheated;
    }

    public void setAnswerCheated(boolean mAnswerCheated) {

        this.mAnswerCheated = mAnswerCheated;
    }

    public boolean ismAnswerCheated() {
        return mAnswerCheated;
    }

    public void setmAnswerCheated(boolean mAnswerCheated) {
        this.mAnswerCheated = mAnswerCheated;
    }

    public Question(int textResId, boolean answerTrue, boolean AnswerCheated) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mAnswerCheated = AnswerCheated;
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


    public Question(Parcel in) {
        mTextResId = in.readInt();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mAnswerTrue = in.readBoolean();
            mAnswerCheated = in.readBoolean();
        }
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return mTextResId + ": " + mAnswerTrue + ":" + mAnswerCheated;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mTextResId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            out.writeBoolean(mAnswerTrue);
            out.writeBoolean(mAnswerCheated);
        }
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
