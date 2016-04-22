package me.kamadi.memorize.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.kamadi.memorize.R;
import me.kamadi.memorize.adapter.WordAdapter;
import me.kamadi.memorize.database.Repo;
import me.kamadi.memorize.dialog.WordListDialog;
import me.kamadi.memorize.event.BusProvider;
import me.kamadi.memorize.model.Group;
import me.kamadi.memorize.model.Word;
import me.kamadi.memorize.model.WordGroup;

public class GroupActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private static final String LOG_TAG = GroupActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.listView)
    ListView listView;

    @Bind(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private Repo repo;

    private List<Word> words = new ArrayList<>();

    private Group group;

    private WordAdapter wordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
        group = getIntent().getParcelableExtra("group");
        setTitle(group.getName());

        try {
            repo = new Repo(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        BusProvider.getInstance().register(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        intent.putExtra("group", group);
        intent.putParcelableArrayListExtra("words", new ArrayList<>(words));
        switch (item.getItemId()) {
            case R.id.action_test:
                intent.setClass(this, TestActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_question:
                intent.setClass(this, QuestionActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
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
            words.clear();
            for (WordGroup wordGroup : repo.getWordGroupRepo().getByGroup(group)) {
                words.add(wordGroup.getWord());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        wordAdapter = new WordAdapter(this, words);
        listView.setAdapter(wordAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        getWords();
    }

    @OnClick(R.id.add)
    public void onAddBtnClick(View view) {
        WordListDialog dialog = new WordListDialog();
        dialog.show(getSupportFragmentManager(), "WordListDialog");
    }

    @Subscribe
    public void onWordAddEvent(Word word) {
        if (!words.contains(word)) {
            WordGroup wordGroup = new WordGroup(group, word);
            try {
                if (repo.getWordGroupRepo().create(wordGroup)) {
                    words.add(word);
                    wordAdapter.notifyDataSetChanged();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, WordFullInfoActivity.class);
        intent.putParcelableArrayListExtra("words", new ArrayList<>(words));
        intent.putExtra("currentItem", position);
        startActivity(intent);
    }
}
