package me.kamadi.memorize.fragment;


import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.kamadi.memorize.App;
import me.kamadi.memorize.R;
import me.kamadi.memorize.model.Language;
import me.kamadi.memorize.model.Word;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordDetailFragment extends Fragment implements TextToSpeech.OnInitListener {

    @Bind(R.id.word)
    TextView txtWord;

    @Bind(R.id.transcript)
    TextView txtTranscript;

    @Bind(R.id.translation)
    TextView txtTranslation;

    @Bind(R.id.example)
    TextView txtExample;

    TextToSpeech textToSpeech;
    private Word word;

    public static WordDetailFragment newInstance(Word word) {
        WordDetailFragment fragment = new WordDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("word", word);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        word = getArguments().getParcelable("word");
        textToSpeech = new TextToSpeech(getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        txtWord.setText(word.getWord());
        txtTranslation.setText(word.getTranslation());
        txtTranscript.setText(word.getTranscript());
        txtExample.setText(word.getExample());
    }

    @OnClick(R.id.word)
    public void onWordTxtClick(View view) {
        if (Build.VERSION.RELEASE.startsWith("5")) {
            textToSpeech.speak(word.getWord(), TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            textToSpeech.speak(word.getWord(), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onInit(int status) {
        if (status != TextToSpeech.ERROR) {
            switch (App.getInstance().getLanguage()) {
                case Language.ENGLISH:
                    textToSpeech.setLanguage(Locale.UK);
                    break;
                case Language.ARABIC:
                    textToSpeech.setLanguage(new Locale("ar", "SA"));
                    break;
            }

        }
    }
}
