package com.github.vignesh_iopex.issues.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.vignesh_iopex.issues.R;
import com.github.vignesh_iopex.issues.models.Comment;

import java.util.List;

public class CommentListAdapter extends BaseAdapter {
  private final Context context;
  private final List<Comment> comments;

  public CommentListAdapter(Context context, List<Comment> comments) {
    this.context = context;
    this.comments = comments;
  }

  @Override public int getCount() {
    return comments.size();
  }

  @Override public Comment getItem(int i) {
    return comments.get(i);
  }

  @Override public long getItemId(int i) {
    return 0;
  }

  @Override public View getView(int i, View view, ViewGroup viewGroup) {
    ViewHolder viewHolder;
    if (view == null) {
      view = View.inflate(context, R.layout.comment_row, null);
      viewHolder = new ViewHolder(view);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }
    viewHolder.bind(getItem(i));
    return view;
  }

  private class ViewHolder {
    TextView txtComment;
    TextView txtAuthor;

    ViewHolder(View view) {
      txtComment = (TextView) view.findViewById(R.id.txt_issue);
      txtAuthor = (TextView) view.findViewById(R.id.txt_author);
    }

    void bind(Comment comment) {
      txtAuthor.setText(comment.getAuthor());
      txtComment.setText(comment.getBody());
    }
  }
}
