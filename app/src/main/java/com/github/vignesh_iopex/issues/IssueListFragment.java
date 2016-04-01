package com.github.vignesh_iopex.issues;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.vignesh_iopex.issues.adapters.IssuesListAdapter;
import com.github.vignesh_iopex.issues.downloaders.IssueDownloader;
import com.github.vignesh_iopex.issues.models.Issue;

import java.util.List;

import static com.github.vignesh_iopex.issues.IssueDetailFragment.buildActivityArgs;

public class IssueListFragment extends Fragment implements IssueListRenderer,
    AdapterView.OnItemClickListener {
  static final String ISSUE_URL = BuildConfig.ISSUE_URL;

  private final IssueListPresenter presenter;
  private ListView lstIssues;

  public IssueListFragment() {
    this(new IssueDownloader());
  }

  @SuppressLint("ValidFragment")
  public IssueListFragment(IssueDownloader issueDownloader) {
    this.presenter = new IssueListPresenter(this, issueDownloader);
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.issues_list_view, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    injectViews(view);
    downloadIssues();
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.main_menu, menu);
  }

  private void downloadIssues() {
    presenter.downloadIssues(ISSUE_URL);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_refresh:
        downloadIssues();
        return true;
      default:
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onDestroyView() {
    presenter.close();
    super.onDestroyView();
  }

  private void injectViews(View view) {
    lstIssues = (ListView) view.findViewById(R.id.lst_issues);
  }

  @Override public void render(List<Issue> issueList) {
    IssuesListAdapter issuesListAdapter = new IssuesListAdapter(getActivity(), issueList);
    lstIssues.setAdapter(issuesListAdapter);
    lstIssues.setOnItemClickListener(this);
  }

  @Override public void onError(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  @Override public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
    Issue selectedIssue = (Issue) adapterView.getItemAtPosition(i);
    getActivity().startActivity(buildActivityArgs(getActivity(), selectedIssue));
  }
}
