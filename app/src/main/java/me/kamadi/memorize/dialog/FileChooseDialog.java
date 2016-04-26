package me.kamadi.memorize.dialog;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FilenameFilter;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.kamadi.memorize.R;
import me.kamadi.memorize.event.BusProvider;

/**
 * Created by Madiyar on 26.04.2016.
 */
public class FileChooseDialog extends DialogFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, FilenameFilter {
    private static final String TAG = FileChooseDialog.class.getSimpleName();
    private static final String FTYPE = ".xls";
    private static final int DIALOG_LOAD_FILE = 1000;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private String[] mFileList;
    private File file = new File(Environment.getExternalStorageDirectory().toString());
    private String path = "";
    private String mChosenFile;
    private ArrayAdapter<String> adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_list, container, false);
        getDialog().setTitle(R.string.choose_file);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
        BusProvider.getInstance().register(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                loadFileList();
            }
        });
    }


    @Override
    public void onRefresh() {
        loadFileList();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String currentPath = (String) parent.getItemAtPosition(position);
        path += "/" + currentPath;
        file = new File(Environment.getExternalStorageDirectory() + path);
        loadFileList();
    }

    private void loadFileList() {
        if (!file.isFile()) {
            mFileList = file.list(this);
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mFileList);
            listView.setAdapter(adapter);
            swipeRefreshLayout.setRefreshing(false);

        } else {
            this.dismiss();
            BusProvider.getInstance().post(path);
        }
    }


    @Override
    public boolean accept(File dir, String filename) {
        File sel = new File(dir, filename);
        return filename.contains(FTYPE) || sel.isDirectory();
    }
}
