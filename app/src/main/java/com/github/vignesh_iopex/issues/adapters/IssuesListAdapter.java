package com.github.vignesh_iopex.issues.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.vignesh_iopex.issues.R;
import com.github.vignesh_iopex.issues.models.Issue;

import java.util.List;

public class IssuesListAdapter extends BaseAdapter {
  private final List<Issue> issues;
  private Context context;

  public IssuesListAdapter(Context context, List<Issue> issues) {
    this.issues = issues;
    this.context = context;
  }

  @Override public int getCount() {
    return issues.size();
  }

  @Override public Issue getItem(int position) {
    return issues.get(position);
  }

  @Override public long getItemId(int position) {
    return 0;
  }

  @Override public View getView(int position, View view, ViewGroup viewGroup) {
    ViewHolder viewHolder;

    if (view == null) {
      view = View.inflate(context, R.layout.issue_row, null);
      viewHolder = new ViewHolder(view);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }
    viewHolder.bind(getItem(position));
    return view;
  }

  private class ViewHolder {
    TextView txtTitle;
    TextView txtIssue;
    TextView txtAuthor;

    public ViewHolder(View view) {
      txtTitle = (TextView) view.findViewById(R.id.txt_title);
      txtIssue = (TextView) view.findViewById(R.id.txt_issue);
      txtAuthor = (TextView) view.findViewById(R.id.txt_author);
    }

    void bind(Issue issue) {
      txtTitle.setText(issue.getTitle());
      txtIssue.setText(issue.getIssueBody());
      txtAuthor.setText(issue.getAuthor());
    }
  }
}
