package me.kamadi.memorize.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.kamadi.memorize.R;
import me.kamadi.memorize.model.Word;

/**
 * Created by Madiyar on 12.04.2016.
 */
public class WordAdapter extends BaseAdapter {

    private Context context;
    private List<Word> words;

    public WordAdapter(Context context, List<Word> words) {
        this.context = context;
        this.words = words;
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public Object getItem(int position) {
        return words.get(position);
    }

    @Override
    public long getItemId(int position) {
        return words.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Word word = (Word) getItem(position);
        viewHolder.title.setText(word.getWord());
        return convertView;
    }

    private static class ViewHolder {
        TextView title;
    }
}
