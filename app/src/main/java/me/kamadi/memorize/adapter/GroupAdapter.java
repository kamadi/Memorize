package me.kamadi.memorize.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.kamadi.memorize.model.Group;

/**
 * Created by Madiyar on 12.04.2016.
 */
public class GroupAdapter extends ArrayAdapter<List<Group>> {

    private Context context;
    private List<Group> groups;

    public GroupAdapter(Context context, List<Group> groups) {
        super(context, android.R.layout.simple_list_item_1);
        this.context = context;
        this.groups = groups;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Group group = (Group) getItem(position);
        viewHolder.title.setText(group.getName());
        return convertView;
    }

    private static class ViewHolder {
        TextView title;
    }
}
