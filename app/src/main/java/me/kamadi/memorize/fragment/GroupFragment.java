package me.kamadi.memorize.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import me.kamadi.memorize.activity.GroupActivity;
import me.kamadi.memorize.adapter.GroupAdapter;
import me.kamadi.memorize.database.Repo;
import me.kamadi.memorize.model.Group;
import me.kamadi.memorize.model.Language;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private static final String LOG_TAG = GroupFragment.class.getSimpleName();
    @Bind(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.listView)
    ListView listView;

    GroupAdapter groupAdapter;
    Repo repo;
    List<Group> groups = new ArrayList<>();

    SharedPreferences sharedPrefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        ButterKnife.bind(this, view);
        listView.setOnItemClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getGroups();
            }
        });

    }

    public void getGroups() {
        try {
            repo = new Repo(getActivity());
            groups = repo.getGroupRepo().getByLanguage(sharedPrefs.getString(Language.KEY, Language.ENGLISH));
            groupAdapter = new GroupAdapter(getActivity(), groups);
            listView.setAdapter(groupAdapter);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    public void onGroupCreate(Group group) {
        group.setLanguage(sharedPrefs.getString(Language.KEY, Language.ENGLISH));
        try {
            if (repo.getGroupRepo().create(group)) {
                getGroups();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRefresh() {
        getGroups();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Group group = (Group) parent.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), GroupActivity.class);
        intent.putExtra("group", group);
        startActivity(intent);
    }
}
