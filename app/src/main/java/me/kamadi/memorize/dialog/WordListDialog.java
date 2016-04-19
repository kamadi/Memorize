package me.kamadi.memorize.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.kamadi.memorize.R;
import me.kamadi.memorize.adapter.WordAdapter;
import me.kamadi.memorize.database.Repo;
import me.kamadi.memorize.event.BusProvider;
import me.kamadi.memorize.model.Language;
import me.kamadi.memorize.model.Word;

/**
 * Created by Madiyar on 19.04.2016.
 */
public class WordListDialog extends DialogFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    @Bind(R.id.listView)
    ListView listView;

    @Bind(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private Repo repo;

    private List<Word> words = new ArrayList<>();
    private WordAdapter wordAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_word_list, container, false);
        getDialog().setTitle(R.string.adding_word);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
        BusProvider.getInstance().register(this);
        try {
            repo = new Repo(getActivity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getWords();
            }
        });
    }

    private void getWords() {
        try {
            words = repo.getWordRepo().getByLanguage(Language.ARABIC);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        wordAdapter = new WordAdapter(getActivity(), words);
        listView.setAdapter(wordAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        getWords();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Word word = (Word) parent.getItemAtPosition(position);
        BusProvider.getInstance().post(word);
        words.remove(position);
        wordAdapter.notifyDataSetChanged();

    }
}
