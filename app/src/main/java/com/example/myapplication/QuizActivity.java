package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    // TODO Подсмотрев ответ, пользователь может повернуть CheatActivity, чтобы сбро-сить результат. //Исправлено
    // TODO После возвращения пользователь может повернуть QuizActivity, чтобы сбро-сить флаг mIsCheater. //Исправлено
    // TODO Пользователь может нажимать кнопку Next до тех пор, пока вопрос, ответ на который был подсмотрен, снова не появится на экране.//Исправлено

    private static final String TAG = "QuizActivity01";
    private static final String KEY_INDEX = "index";
    private static final String KEY_INDEX_CHEAT = "index.boolean";
    private static final String KEY_INDEX_ARRAY = "index.array";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;
    private ArrayList<Question> mQuestionsBank = new ArrayList<Question>();

//    private Question[] mQuestionBank = new Question[]{//from original code
//            new Question(R.string.question_oceans, true, false),
//            new Question(R.string.question_mideast, false, false),
//            new Question(R.string.question_africa, false, false),
//            new Question(R.string.question_americas, true, false),
//            new Question(R.string.question_asia, true, false),
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        mQuestionsBank.add(new Question(R.string.question_oceans, true, false));
        mQuestionsBank.add(new Question(R.string.question_mideast, false, false));
        mQuestionsBank.add(new Question(R.string.question_africa, false, false));
        mQuestionsBank.add(new Question(R.string.question_americas, true, false));
        mQuestionsBank.add(new Question(R.string.question_asia, true, false));

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        int question = mQuestionsBank.get(mCurrentIndex).getTextResID();
        mQuestionTextView.setText(question);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.size();
                mIsCheater = false;
                updateQuestion();
                Log.d(TAG, "75   mCurrentIndex=" + mCurrentIndex + " isAnswerCheated=" + mQuestionsBank.get(mCurrentIndex).isAnswerCheated());
                if (mCurrentIndex == mQuestionsBank.size()) {
                    mCurrentIndex = 0;
                }
            }
        });

        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionsBank.size();
                if (mCurrentIndex <0) {
                    mCurrentIndex = mQuestionsBank.size()-1;
                }
                Log.d(TAG, "90   mCurrentIndex=" + mCurrentIndex + " isAnswerCheated=" + mQuestionsBank.get(mCurrentIndex).isAnswerCheated());
                updateQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                boolean answerIsTrue = mQuestionsBank.get(mCurrentIndex).isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
             }
        });

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_INDEX_CHEAT, false);
            mQuestionsBank = savedInstanceState.getParcelableArrayList(KEY_INDEX_ARRAY);
        }

        updateQuestion();
    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_INDEX_CHEAT, mIsCheater);
        savedInstanceState.putParcelableArrayList(KEY_INDEX_ARRAY, mQuestionsBank);
    }

    private void updateQuestion() {
       // int question = mQuestionBank[mCurrentIndex].getTextResID();
        int question = mQuestionsBank.get(mCurrentIndex).getTextResID();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        //boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        boolean answerIsTrue = mQuestionsBank.get(mCurrentIndex).isAnswerTrue();
        int messageResId = 0;

        //if (mIsCheater)
        if (mQuestionsBank.get(mCurrentIndex).isAnswerCheated()) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            mQuestionsBank.get(mCurrentIndex).setAnswerCheated(mIsCheater);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.quiz, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}