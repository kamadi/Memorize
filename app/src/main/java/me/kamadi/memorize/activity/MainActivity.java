package me.kamadi.memorize.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.kamadi.memorize.R;
import me.kamadi.memorize.adapter.MainPagerAdapter;
import me.kamadi.memorize.dialog.GroupDialog;
import me.kamadi.memorize.dialog.WordDialog;
import me.kamadi.memorize.event.BusProvider;
import me.kamadi.memorize.model.Group;
import me.kamadi.memorize.model.Word;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    MainPagerAdapter mainPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BusProvider.getInstance().register(this);

        setSupportActionBar(toolbar);
        mainPagerAdapter = new MainPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(mainPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onFabClicked(View view) {
        switch (viewPager.getCurrentItem()) {
            case 0:
                GroupDialog dialog = new GroupDialog();
                dialog.show(getSupportFragmentManager(), "GroupDialog");
                break;
            case 1:
                WordDialog wordDialog = new WordDialog();
                wordDialog.show(getSupportFragmentManager(), "WordDialog");
                break;
        }
    }

    @Subscribe
    public void handleGroupCreate(Group group) {
        mainPagerAdapter.getGroupFragment().onGroupCreate(group);
    }

    @Subscribe
    public void handleWordCreate(Word word) {
        mainPagerAdapter.getWordFragment().onWordCreate(word);
    }
}
