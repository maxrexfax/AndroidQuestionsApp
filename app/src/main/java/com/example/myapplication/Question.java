package com.example.myapplication;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    private int mTextResId;
    private int mIntAnswerTrue;
    private int mIntAnswerCheated;
//    private boolean mAnswerTrue;//to decrease level of Android API exclude all boolean types from Parcelable class
//    private boolean mAnswerCheated;

    public Question(int textResId, int intAnswerTrue, int intAnswerCheated) {
        mTextResId = textResId;
        mIntAnswerTrue = intAnswerTrue;
        mIntAnswerCheated = 0;
    }

    public int getTextResID() {

        return mTextResId;
    }

    public void setTextResId(int mTextResId) {

        this.mTextResId = mTextResId;
    }

    public int getIntAnswerTrue() {

        return mIntAnswerTrue;
    }

    public void setIntAnswerTrue(int mIntAnswerTrue) {

        this.mIntAnswerTrue = mIntAnswerTrue;
    }

    public int getIntAnswerCheated() {

        return mIntAnswerCheated;
    }

    public void setIntAnswerCheated(int mIntAnswerCheated) {
        this.mIntAnswerCheated = mIntAnswerCheated;
    }

//    public boolean isAnswerTrue() {
//
//        return mAnswerTrue;
//    }
//
//    public void setAnswerTrue(boolean mAnswerTrue) {
//
//        this.mAnswerTrue = mAnswerTrue;
//    }
//
//    public boolean isAnswerCheated() {
//
//        return mAnswerCheated;
//    }
//
//    public void setAnswerCheated(boolean mAnswerCheated) {
//
//        this.mAnswerCheated = mAnswerCheated;
//    }

//    public Question(int textResId, boolean answerTrue, boolean AnswerCheated) {
//        mTextResId = textResId;
//        mAnswerTrue = answerTrue;
//        mAnswerCheated = AnswerCheated;
//    }



    public Question(Parcel in) {
        mTextResId = in.readInt();
        mIntAnswerTrue = in.readInt();
        mIntAnswerCheated = in.readInt();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            mAnswerTrue = in.readBoolean();
//            mAnswerCheated = in.readBoolean();
//        }
    }

    public int describeContents() {

        return 0;
    }

    @Override
    public String toString() {
        //return mTextResId + ": " + mAnswerTrue + ":" + mAnswerCheated;
        return mTextResId + ": " + mIntAnswerTrue + ":" + mIntAnswerCheated;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mTextResId);
        out.writeInt(mIntAnswerTrue);
        out.writeInt(mIntAnswerCheated);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            out.writeBoolean(mAnswerTrue);
//            out.writeBoolean(mAnswerCheated);
//        }
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
