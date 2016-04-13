package me.kamadi.memorize.dialog;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.kamadi.memorize.R;
import me.kamadi.memorize.event.BusProvider;
import me.kamadi.memorize.model.Group;
import me.kamadi.memorize.model.Language;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupDialog extends DialogFragment {

    private static final String LOG_TAG = GroupDialog.class.getSimpleName();
    @Bind(R.id.name)
    EditText name;

    @Bind(R.id.create)
    Button create;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_dialog, container, false);
        ButterKnife.bind(this, view);
        BusProvider.getInstance().register(getActivity());
        return view;
    }

    @OnClick(R.id.create)
    public void onCreateButtonClick(View view) {
        Log.e(LOG_TAG, "clicked");
        Group group = new Group(1L, "Test", Language.ARABIC);
        BusProvider.getInstance().post(group);
    }
}
