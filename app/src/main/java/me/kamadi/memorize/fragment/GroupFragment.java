package me.kamadi.memorize.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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
import me.kamadi.memorize.adapter.GroupAdapter;
import me.kamadi.memorize.database.repo.Repo;
import me.kamadi.memorize.model.Group;
import me.kamadi.memorize.model.Language;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = GroupFragment.class.getSimpleName();
    @Bind(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.listView)
    ListView listView;

    GroupAdapter groupAdapter;
    Repo repo;
    List<Group> groups = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        ButterKnife.bind(this, view);
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
            groups = repo.getGroupRepo().getByLanguage(Language.ARABIC);
            Group group = new Group(1L, "Test", Language.ARABIC);
            groups.add(group);

            groupAdapter = new GroupAdapter(getActivity(), groups);
            listView.setAdapter(groupAdapter);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    public void onGroupCreate(Group group) {
        Log.e(LOG_TAG, group.getName() + "");
        try {
            if (repo.getGroupRepo().create(group)) {
                Log.e(LOG_TAG, group.getName() + " created");
                getGroups();
            }
        } catch (SQLException e) {
            Log.e(LOG_TAG, group.getName() + " error");
            e.printStackTrace();
        }

    }

    @Override
    public void onRefresh() {
        getGroups();
    }
}
