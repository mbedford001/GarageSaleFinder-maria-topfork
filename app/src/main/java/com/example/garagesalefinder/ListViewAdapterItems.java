package com.example.garagesalefinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.garagesalefinder.PostStuff.Items;
import com.example.garagesalefinder.PostStuff.Post;

import java.util.ArrayList;
import java.util.List;

    public class ListViewAdapterItems extends BaseAdapter {

        Context mContext;
        LayoutInflater inflater;
        private List<Items> results = null;
        private ArrayList<Items> arraylist;

        public ListViewAdapterItems(Context context, List<Items> results) {
            mContext = context;
            this.results = results;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<Items>();
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
        public Items getItem(int position) {
            return results.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final com.example.garagesalefinder.ListViewAdapterItems.ViewHolder holder;
            if (view == null) {
                holder = new com.example.garagesalefinder.ListViewAdapterItems.ViewHolder();
                view = inflater.inflate(R.layout.view_items, null);
                // Locate the TextViews in listview_item.xml
                holder.name = (TextView) view.findViewById(R.id.name);
                // holder.button = (Button) view.findViewById(R.id.btnViewPost);
                view.setTag(holder);
            } else {
                holder = (com.example.garagesalefinder.ListViewAdapterItems.ViewHolder) view.getTag();

            }
            // Set the results into TextViews
            System.out.println(holder.name);
            holder.name.setText(results.get(position).getItem_title());
            return view;
        }
    }

