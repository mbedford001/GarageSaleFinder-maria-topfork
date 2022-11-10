package com.example.garagesalefinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.garagesalefinder.PostStuff.Post;
import java.util.ArrayList;
import java.util.List;
import android.widget.Button;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<Post> results = null;
    private ArrayList<Post> arraylist;

    public ListViewAdapter(Context context, List<Post> results) {
        mContext = context;
        this.results = results;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Post>();
        this.arraylist.addAll(results);
    }

    public class ViewHolder {
        TextView name;
        Button button;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Post getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.view_posts, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
           // holder.button = (Button) view.findViewById(R.id.btnViewPost);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }
        // Set the results into TextViews
        holder.name.setText(results.get(position).getTitle());//add a getPost() method to Post??
        return view;
    }
}