package com.github.vignesh_iopex.issues;

import android.os.Bundle;

import com.github.vignesh_iopex.issues.async.AsyncDownloader;
import com.github.vignesh_iopex.issues.async.SingleAsyncExecuter;
import com.github.vignesh_iopex.issues.downloaders.IssueDownloader;
import com.github.vignesh_iopex.issues.models.Issue;

import java.util.ArrayList;
import java.util.List;

public class IssueListPresenter implements AsyncDownloader.DownloadListener<List<Issue>> {
  private static final String EXTRA_STATE_LIST_CACHE = "issue_cache";
  private final IssueListRenderer issueListView;
  private final SingleAsyncExecuter<List<Issue>> asyncExecuter;
  private ArrayList<Issue> issueCache;

  public IssueListPresenter(IssueListRenderer issueListView,
                            SingleAsyncExecuter<List<Issue>> asyncExecuter) {
    this.issueListView = issueListView;
    this.asyncExecuter = asyncExecuter;
  }

  public IssueListPresenter(IssueListRenderer issueListView, IssueDownloader downloader) {
    this(issueListView, new SingleAsyncExecuter<>(downloader));
  }

  public void downloadIssues(String issueLink) {
    asyncExecuter.execute(issueLink, this);
  }

  public void close() {
    asyncExecuter.cancel();
  }

  public boolean restoreInstanceState(Bundle savedState) {
    ArrayList<Issue> restoredList = savedState.getParcelableArrayList(EXTRA_STATE_LIST_CACHE);
    if (restoredList != null) {
      deliverResult(restoredList);
      return true;
    }
    return false;
  }

  public void notifyFailure(String message) {
    issueListView.onError(message);
  }

  public void saveInstanceState(Bundle saveState) {
    saveState.putParcelableArrayList(EXTRA_STATE_LIST_CACHE, issueCache);
  }

  public void deliverResult(List<Issue> issues) {
    issueCache = new ArrayList<>(issues);
    issueListView.render(issues);
  }
}
