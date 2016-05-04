package me.kamadi.memorize.fragment;


import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.sql.SQLException;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.kamadi.memorize.App;
import me.kamadi.memorize.R;
import me.kamadi.memorize.database.Repo;
import me.kamadi.memorize.dialog.WordDialog;
import me.kamadi.memorize.event.BusProvider;
import me.kamadi.memorize.event.word.WordDeleteEvent;
import me.kamadi.memorize.event.word.WordUpdateEvent;
import me.kamadi.memorize.model.Language;
import me.kamadi.memorize.model.Word;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordDetailFragment extends Fragment implements TextToSpeech.OnInitListener {

    private static final String LOG_TAG = WordDetailFragment.class.getSimpleName();
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

    private Repo repo;


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
        setHasOptionsMenu(true);

        try {
            repo = new Repo(getActivity());
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        setValue();
    }

    private void setValue() {
        txtWord.setText(word.getWord());
        txtTranslation.setText(word.getTranslation());
        txtTranscript.setText(word.getTranscript());
        txtExample.setText(word.getExample());
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        inflater.inflate(R.menu.menu_word_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                WordDialog wordDialog = WordDialog.newInstance(word);
                wordDialog.show(getFragmentManager(), "WordDialog");
                break;
            case R.id.action_delete:
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.confirmation_question)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                try {
                                    if (repo.getWordRepo().delete(word)) {
                                        BusProvider.getInstance().post(new WordDeleteEvent(word));
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                break;
        }

        return false;
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

    @Subscribe
    public void onWordUpdate(WordUpdateEvent event) {
        try {
            if (repo.getWordRepo().update(event.getWord())) {
                this.word = event.getWord();
                setValue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
