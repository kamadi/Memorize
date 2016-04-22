package me.kamadi.memorize.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.kamadi.memorize.R;
import me.kamadi.memorize.model.Word;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordFullInfoFragment extends Fragment {

    @Bind(R.id.word)
    TextView txtWord;

    @Bind(R.id.transcript)
    TextView txtTranscript;

    @Bind(R.id.translation)
    TextView txtTranslation;

    private Word word;

    public static WordFullInfoFragment newInstance(Word word) {
        WordFullInfoFragment fragment = new WordFullInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("word", word);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        word = getArguments().getParcelable("word");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_full_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        txtWord.setText(word.getWord());
        txtTranslation.setText(word.getTranslation());
        txtTranscript.setText(word.getTranscript());
    }
}
