package com.github.vignesh_iopex.issues;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.vignesh_iopex.issues.adapters.CommentListAdapter;
import com.github.vignesh_iopex.issues.downloaders.CommentDownloader;
import com.github.vignesh_iopex.issues.models.Comment;
import com.github.vignesh_iopex.issues.models.Issue;

import java.util.List;

public class IssueDetailFragment extends Fragment implements CommentListRenderer {
  public static final String EXTRA_ISSUE = "extra_issue";
  private TextView txtTitle;
  private TextView txtIssue;
  private TextView txtAuthor;

  private ListView lstComments;
  private IssueDetailPresenter issueDetailPresenter;

  public IssueDetailFragment() {
    this(new CommentDownloader());
  }

  @SuppressLint("ValidFragment")
  public IssueDetailFragment(CommentDownloader commentDownloader) {
    issueDetailPresenter = new IssueDetailPresenter(this, commentDownloader);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
    return inflater.inflate(R.layout.comment_list, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    View headerView = View.inflate(view.getContext(), R.layout.issue_detail, null);
    injectIssueDetailView(headerView);

    Issue issue = getArguments().getParcelable(EXTRA_ISSUE);
    if (issue == null) {
      return;
    }
    getActivity().setTitle(String.format("Issue #%s", issue.getNumber()));
    bind(issue);

    injectCommentListView(view, headerView);
    issueDetailPresenter.downloadComments(issue);
  }

  @Override public void onDestroyView() {
    issueDetailPresenter.close();
    super.onDestroyView();
  }

  private void injectCommentListView(View view, View headerView) {
    lstComments = (ListView) view.findViewById(R.id.lst_comments);
    lstComments.addHeaderView(headerView);
  }

  private void injectIssueDetailView(View view) {
    txtTitle = (TextView) view.findViewById(R.id.txt_title);
    txtIssue = (TextView) view.findViewById(R.id.txt_issue);
    txtAuthor = (TextView) view.findViewById(R.id.txt_author);
  }

  private void bind(Issue issue) {
    txtTitle.setText(issue.getTitle());
    txtIssue.setText(issue.getIssueBody());
    txtAuthor.setText(issue.getAuthor());
  }

  public static Intent buildActivityArgs(Context context, Issue issue) {
    Intent intent = new Intent(context, IssueDetailActivity.class);
    intent.putExtras(buildArgs(issue));
    return intent;
  }

  private static Bundle buildArgs(Issue issue) {
    Bundle args = new Bundle();
    args.putParcelable(EXTRA_ISSUE, issue);
    return args;
  }

  @Override public void render(List<Comment> comments) {
    lstComments.setAdapter(new CommentListAdapter(getActivity(), comments));
  }

  @Override public void onError(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
  }
}
