package com.example.brandon.apiclient.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.brandon.apiclient.Models.Comment;
import com.example.brandon.apiclient.Models.Post;
import com.example.brandon.apiclient.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Brandon on 20-Oct-17.
 */

public class ExapndableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Post> expandableListPosts;
    private HashMap<Post, List<Comment>> expandableDetails;

    public ExapndableListViewAdapter(Context context, List<Post> expandableListPosts,
                                       HashMap<Post, List<Comment>> expandableDetails) {
        this.context = context;
        this.expandableListPosts = expandableListPosts;
        this.expandableDetails = expandableDetails;
    }

    @Override
    public int getGroupCount() {
        return this.expandableListPosts.size();
    }

    @Override
    public int getChildrenCount(int postPosition) {
        return this.expandableDetails.get(this.expandableListPosts.get(postPosition))
                .size();
    }

    @Override
    public Object getGroup(int postPosition) {
        return this.expandableListPosts.get(postPosition);
    }

    @Override
    public Object getChild(int postPosition, int commentPosition) {
        return this.expandableDetails.get(this.expandableListPosts.get(postPosition))
                .get(commentPosition);
    }

    @Override
    public long getGroupId(int postPosition) {
        return postPosition;
    }

    @Override
    public long getChildId(int postPosition, int commentPosition) {
        return commentPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int postPosition, boolean isExpanded, View view, ViewGroup parent) {
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.posts_layout, null);
        }

        final Post oPost = (Post) getGroup(postPosition);

        TextView tvUserId = (TextView) view.findViewById(R.id.tvUserId);
        TextView tvId = (TextView) view.findViewById(R.id.tvId);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvBody = (TextView) view.findViewById(R.id.tvBody);

        tvUserId.setText(oPost.getUserId()+"");
        tvId.setText(oPost.getId()+"");
        tvTitle.setText(oPost.getTitle());
        tvBody.setText(oPost.getBody());

        return view;
    }

    @Override
    public View getChildView(int postPosition, int commentPosition, boolean isLastChild, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.comments_layout, null);
        }

        final Comment oComment = (Comment) getChild(postPosition, commentPosition);

        TextView tvPostId = (TextView) view.findViewById(R.id.tvPostId);
        TextView tvId = (TextView) view.findViewById(R.id.tvId);
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        TextView tvBody = (TextView) view.findViewById(R.id.tvBody);

        tvPostId.setText(oComment.getPostId()+"");
        tvId.setText(oComment.getId()+"");
        tvName.setText(oComment.getName());
        tvEmail.setText(oComment.getEmail());
        tvBody.setText(oComment.getBody());

        return view;
    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
