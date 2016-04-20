package me.kamadi.memorize.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.kamadi.memorize.R;
import me.kamadi.memorize.util.ToastUtil;
import me.kamadi.memorize.adapter.WordAdapter;
import me.kamadi.memorize.database.Repo;
import me.kamadi.memorize.model.Language;
import me.kamadi.memorize.model.Word;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = WordFragment.class.getSimpleName();

    @Bind(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.listView)
    ListView listView;

    WordAdapter wordAdapter;

    Repo repo;

    List<Word> words = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);

        try {
            repo = new Repo(getActivity());
        } catch (SQLException e) {
            ToastUtil.show(getActivity(),e.getMessage());
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

    public void onWordCreate(Word word) {
        try {
            if (repo.getWordRepo().create(word)) {
                getWords();
            }

        } catch (SQLException e) {
            ToastUtil.show(getActivity(),e.getMessage());
            e.printStackTrace();
        }
    }

    private void getWords() {
        try {
            words = repo.getWordRepo().getByLanguage(Language.ARABIC);
            wordAdapter = new WordAdapter(getActivity(), words);
            listView.setAdapter(wordAdapter);
        } catch (SQLException e) {
            ToastUtil.show(getActivity(),e.getMessage());
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        getWords();
    }
}
