package me.kamadi.memorize.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.kamadi.memorize.R;
import me.kamadi.memorize.database.Repo;
import me.kamadi.memorize.model.Group;
import me.kamadi.memorize.model.Word;

public class QuestionActivity extends AppCompatActivity {
    private static final int NORMAL = 0;
    private static final int REVERSE = 1;
    @Bind(R.id.word)
    TextView txtWord;

    @Bind(R.id.translation)
    TextView txtTranslation;

    @Bind(R.id.result)
    TextView txtResult;

    @Bind(R.id.answer)
    EditText answer;

    @Bind(R.id.check)
    Button btnCheck;

    @Bind(R.id.next)
    Button btnNext;

    @Bind(R.id.questionLayout)
    LinearLayout questionLayout;

    private Repo repo;
    private ArrayList<Word> words = new ArrayList<>();
    private Group group;
    private int MAX_SIZE;
    private int index = 0;
    private Word currentWord;
    private int correctAnswerCount = 0;
    private int TYPE = NORMAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        group = getIntent().getParcelableExtra("group");
        words = getIntent().getParcelableArrayListExtra("words");
        MAX_SIZE = words.size();
        Collections.shuffle(words);
    }

    @Override
    protected void onStart() {
        super.onStart();

        showDialog();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setCancelable(false);
        adb.setTitle(getString(R.string.select_question_type));
        CharSequence items[] = new CharSequence[]{getString(R.string.normal), getString(R.string.reverse)};
        adb.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface d, int n) {
                if (n == 0) {
                    TYPE = NORMAL;
                } else {
                    TYPE = REVERSE;
                }
                d.dismiss();
                setInfo();
            }

        });
        adb.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                setInfo();
            }
        });
        adb.show();
    }

    public void setInfo() {
        btnCheck.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.GONE);
        txtTranslation.setText("");
        txtWord.setBackgroundColor(getResources().getColor(R.color.white));
        answer.setText("");
        currentWord = words.get(index);
        if (TYPE == NORMAL) {
            txtWord.setText(currentWord.getWord());
        } else {
            txtWord.setText(currentWord.getTranslation());
        }

    }

    @OnClick(R.id.check)
    public void onCheckBtnClick(View view) {
        boolean isCorrect = true;

        if ((TYPE == NORMAL && currentWord.getTranslation().equals(answer.getText().toString())) ||
                (TYPE == REVERSE && currentWord.getWord().equals(answer.getText().toString()))) {
            isCorrect = true;
        } else {
            isCorrect = false;
        }

        if (isCorrect) {
            txtWord.setBackgroundColor(getResources().getColor(R.color.green));
            correctAnswerCount++;
        } else {
            txtWord.setBackgroundColor(getResources().getColor(R.color.red));
        }

        if (TYPE == NORMAL) {
            txtTranslation.setText(currentWord.getTranslation());
        } else {
            txtTranslation.setText(currentWord.getWord());
        }

        if (index + 1 < MAX_SIZE) {
            btnCheck.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
        } else {
            btnCheck.setVisibility(View.GONE);
            btnNext.setText(getString(R.string.finish));
            btnNext.setVisibility(View.VISIBLE);

        }
    }

    @OnClick(R.id.next)
    public void onNextBtnClick(View view) {
        if (index + 1 < MAX_SIZE) {
            index++;
            setInfo();
        } else {
            questionLayout.setVisibility(View.GONE);
            txtResult.setVisibility(View.VISIBLE);
            txtResult.setText(String.format(getString(R.string.test_result), correctAnswerCount));
        }
    }

}
