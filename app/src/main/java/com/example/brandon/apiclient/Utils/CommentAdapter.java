package com.example.brandon.apiclient.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.brandon.apiclient.Models.Comment;
import com.example.brandon.apiclient.R;

/**
 * Created by Brandon on 13-Oct-17.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {

    public CommentAdapter(Context context) {
        super(context, R.layout.comments_layout, R.id.tvPostId);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        final View oView = super.getView(position, convertView, parent);

        TextView tvPostId = (TextView) oView.findViewById(R.id.tvPostId);
        TextView tvId = (TextView) oView.findViewById(R.id.tvId);
        TextView tvName = (TextView) oView.findViewById(R.id.tvName);
        TextView tvEmail = (TextView) oView.findViewById(R.id.tvEmail);
        TextView tvBody = (TextView) oView.findViewById(R.id.tvBody);

        Comment oComment = this.getItem(position);

        tvPostId.setText(oComment.getPostId()+"");
        tvId.setText(oComment.getId()+"");
        tvName.setText(oComment.getName());
        tvEmail.setText(oComment.getEmail());
        tvBody.setText(oComment.getBody());

        return oView;
    }
}
