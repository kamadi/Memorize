package me.kamadi.memorize.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.kamadi.memorize.R;
import me.kamadi.memorize.database.Repo;
import me.kamadi.memorize.model.Group;
import me.kamadi.memorize.model.Word;

public class TestActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final String LOG_TAG = TestActivity.class.getSimpleName();
    private static final int CORRECT = 0;
    @Bind(R.id.word)
    TextView txtWord;

    @Bind(R.id.result)
    TextView txtResult;

    @Bind(R.id.answer1)
    RadioButton answer1;

    @Bind(R.id.answer2)
    RadioButton answer2;

    @Bind(R.id.answer3)
    RadioButton answer3;

    @Bind(R.id.answers)
    RadioGroup answersGroup;

    @Bind(R.id.testLayout)
    LinearLayout testLayout;
    Handler handler = new Handler();
    private int MAX_SIZE;
    private Repo repo;
    private ArrayList<Word> words = new ArrayList<>();
    private Group group;
    private int index = 0;
    private Random random = new Random();
    private ArrayList<Word> answers;
    private Word currentWord;
    private int correctAnswerCount = 0;
    private int correctAnswerIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        answer1.setOnCheckedChangeListener(this);
        answer2.setOnCheckedChangeListener(this);
        answer3.setOnCheckedChangeListener(this);

        group = getIntent().getParcelableExtra("group");
        words = getIntent().getParcelableArrayListExtra("words");
        MAX_SIZE = words.size();
        Collections.shuffle(words);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setAnswer();
    }

    public void setAnswer() {
        ArrayList<Integer>indexes = new ArrayList<>();
        indexes.add(index);

        resetRadioBtnValues();

        currentWord = words.get(index);
        txtWord.setText(currentWord.getWord());
        answers = new ArrayList<>(3);
        currentWord.setCorrect(true);
        answers.add(currentWord);

        int randomIndex = getRandomIndex(indexes);
        Word word = words.get(randomIndex);
        word.setCorrect(false);
        answers.add(word);
        indexes.add(randomIndex);

        word = words.get(getRandomIndex(indexes));
        word.setCorrect(false);
        answers.add(word);

        Collections.shuffle(answers);


        answer1.setText(answers.get(0).getTranslation());

        if (answers.get(0).isCorrect())
            correctAnswerIndex = 0;

        answer2.setText(answers.get(1).getTranslation());
        if (answers.get(1).isCorrect())
            correctAnswerIndex = 1;


        answer3.setText(answers.get(2).getTranslation());
        if (answers.get(2).isCorrect())
            correctAnswerIndex = 2;
    }

    public int getRandomIndex(ArrayList<Integer>indexes) {
        int temp = random.nextInt(MAX_SIZE);

        if (!indexes.contains(temp))
            return temp;

        return getRandomIndex(indexes);
    }

    public void resetRadioBtnValues() {
        answer1.setChecked(false);
        answer2.setChecked(false);
        answer3.setChecked(false);

        answer1.setBackgroundColor(getResources().getColor(R.color.white));
        answer2.setBackgroundColor(getResources().getColor(R.color.white));
        answer3.setBackgroundColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            boolean isCorrect = true;
            switch (buttonView.getId()) {
                case R.id.answer1:
                    isCorrect = correctAnswerIndex == 0;
                    break;
                case R.id.answer2:
                    isCorrect = correctAnswerIndex == 1;
                    break;
                case R.id.answer3:
                    isCorrect = correctAnswerIndex == 2;
                    break;
            }

            switch (correctAnswerIndex){
                case 0:
                    answer1.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case 1:
                    answer2.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case 2:
                    answer3.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
            }


            if (isCorrect) {
                correctAnswerCount++;
            }


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (index + 1 < MAX_SIZE) {
                        index++;
                        setAnswer();
                    } else {
                        testLayout.setVisibility(View.GONE);
                        txtResult.setVisibility(View.VISIBLE);
                        txtResult.setText(String.format(getString(R.string.test_result), correctAnswerCount));
                    }
                }
            }, 700);
        }
    }
}
