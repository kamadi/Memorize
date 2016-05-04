package me.kamadi.memorize.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.kamadi.memorize.R;
import me.kamadi.memorize.adapter.WordPagerAdapter;
import me.kamadi.memorize.event.BusProvider;
import me.kamadi.memorize.event.word.WordDeleteEvent;
import me.kamadi.memorize.model.Word;

public class WordDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = WordDetailActivity.class.getSimpleName();
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private WordPagerAdapter wordPagerAdapter;
    private ArrayList<Word> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        words = getIntent().getParcelableArrayListExtra("words");
        wordPagerAdapter = new WordPagerAdapter(this, getSupportFragmentManager(), words);
        viewPager.setAdapter(wordPagerAdapter);
        viewPager.setCurrentItem(getIntent().getIntExtra("currentItem", 0));
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void handleWordDelete(WordDeleteEvent event) {
        Log.e(LOG_TAG, "delete event");
        words.remove(event.getWord());
        wordPagerAdapter.update(words);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
