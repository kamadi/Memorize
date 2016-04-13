package me.kamadi.memorize.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;

import com.squareup.otto.Subscribe;

import java.sql.SQLException;
import java.util.List;

import me.kamadi.memorize.adapter.GroupAdapter;
import me.kamadi.memorize.database.repo.Repo;
import me.kamadi.memorize.event.BusProvider;
import me.kamadi.memorize.model.Group;
import me.kamadi.memorize.model.Language;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends ListFragment {

    GroupAdapter groupAdapter;
    Repo repo;
    List<Group> groups;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BusProvider.getInstance().register(getActivity());
        try {
            repo = new Repo(getActivity());
            groups = repo.getGroupRepo().getByLanguage(Language.ARABIC);
            groupAdapter = new GroupAdapter(getActivity(), groups);
            setListAdapter(groupAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Subscribe
    public void handleGroupCreate(Group group) {
        groups.add(group);
        groupAdapter.notifyDataSetChanged();
    }
}
