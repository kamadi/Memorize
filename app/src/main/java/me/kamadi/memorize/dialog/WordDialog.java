package me.kamadi.memorize.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.kamadi.memorize.R;
import me.kamadi.memorize.event.BusProvider;
import me.kamadi.memorize.event.word.WordUpdateEvent;
import me.kamadi.memorize.model.Word;

/**
 * Created by Madiyar on 18.04.2016.
 */
public class WordDialog extends DialogFragment {

    private static final String LOG_TAG = WordDialog.class.getSimpleName();
    private static final String WORD = "word";
    private static final String UPDATE = "update";
    @Bind(R.id.word)
    EditText word;

    @Bind(R.id.translation)
    EditText translation;

    @Bind(R.id.transcript)
    EditText transcript;

    @Bind(R.id.example)
    EditText example;


    @Bind(R.id.save)
    Button save;

    Word currentWord;

    private boolean isUpdate = false;

    public static WordDialog newInstance(Word word) {
        WordDialog wordDialog = new WordDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(WORD, word);
        bundle.putBoolean(UPDATE, true);
        wordDialog.setArguments(bundle);
        return wordDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentWord = getArguments().getParcelable(WORD);
            isUpdate = getArguments().getBoolean(UPDATE, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_word_dialog, container, false);
        getDialog().setTitle(R.string.new_word);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (currentWord != null) {
            word.setText(currentWord.getWord());
            translation.setText(currentWord.getTranslation());
            transcript.setText(currentWord.getTranscript());
            example.setText(currentWord.getExample());
        }
    }

    @OnClick(R.id.save)
    public void onSaveButtonClick(View view) {
        if (!word.getText().toString().isEmpty() && !translation.getText().toString().isEmpty()) {

            if (currentWord == null)
                currentWord = new Word();

            currentWord.setWord(word.getText().toString());
            currentWord.setTranslation(translation.getText().toString());
            currentWord.setTranscript(transcript.getText().toString());
            currentWord.setExample(example.getText().toString());

            if (isUpdate) {
                BusProvider.getInstance().post(new WordUpdateEvent(currentWord));
            } else {
                BusProvider.getInstance().post(currentWord);
            }

            this.dismiss();
        }

    }
}
