package com.example.brandon.apiclient.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.brandon.apiclient.Models.Post;
import com.example.brandon.apiclient.R;

/**
 * Created by Brandon on 13-Oct-17.
 */

public class PostAdapter extends ArrayAdapter<Post> {

    public PostAdapter(Context context) {
        super(context, R.layout.posts_layout, R.id.tvUserId);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        final View oView = super.getView(position, convertView, parent);

        TextView tvUserId = (TextView) oView.findViewById(R.id.tvUserId);
        TextView tvId = (TextView) oView.findViewById(R.id.tvId);
        TextView tvTitle = (TextView) oView.findViewById(R.id.tvTitle);
        TextView tvBody = (TextView) oView.findViewById(R.id.tvBody);

        Post oPost = this.getItem(position);

        tvUserId.setText(oPost.getUserId()+"");
        tvId.setText(oPost.getId()+"");
        tvTitle.setText(oPost.getTitle());
        tvBody.setText(oPost.getBody());

        return oView;
    }
}
