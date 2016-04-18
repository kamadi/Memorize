package me.kamadi.memorize.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.kamadi.memorize.R;
import me.kamadi.memorize.database.repo.Repo;
import me.kamadi.memorize.event.BusProvider;
import me.kamadi.memorize.model.Group;
import me.kamadi.memorize.model.Language;
import me.kamadi.memorize.model.Word;

/**
 * Created by Madiyar on 18.04.2016.
 */
public class WordDialog extends DialogFragment {

    private static final String LOG_TAG = WordDialog.class.getSimpleName();
    @Bind(R.id.word)
    EditText word;

    @Bind(R.id.translation)
    EditText translation;

    @Bind(R.id.transcript)
    EditText transcript;

    @Bind(R.id.spinner)
    Spinner spinner;

    @Bind(R.id.create)
    Button create;

    private Repo repo;

    private List<Group> groups;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_word_dialog, container, false);
        getDialog().setTitle(R.string.new_word);
        ButterKnife.bind(this, view);
        BusProvider.getInstance().register(this);
        try {
            repo = new Repo(getActivity());
            groups = repo.getGroupRepo().getByLanguage(Language.ARABIC);
            initSpinner();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initSpinner() {
        ArrayList<String> items = new ArrayList<>();
        items.add(getString(R.string.without_group));
        for (Group group : groups) {
            items.add(group.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        spinner.setAdapter(adapter);
    }


    @OnClick(R.id.create)
    public void onCreateButtonClick(View view) {
        if (!word.getText().toString().isEmpty() && !translation.getText().toString().isEmpty()) {
            Word newWord = new Word();
            newWord.setWord(word.getText().toString());
            newWord.setTranslation(translation.getText().toString());
            newWord.setTranscript(transcript.getText().toString());
            if (spinner.getSelectedItemPosition() > 0) {
                newWord.setGroup(groups.get(spinner.getSelectedItemPosition() - 1));
            }
            newWord.setLanguage(Language.ARABIC);

            BusProvider.getInstance().post(newWord);
            this.dismiss();
        }

    }
}