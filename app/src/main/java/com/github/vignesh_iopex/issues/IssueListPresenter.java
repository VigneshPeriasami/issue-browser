package com.github.vignesh_iopex.issues;

import com.github.vignesh_iopex.issues.async.AsyncDownloader;
import com.github.vignesh_iopex.issues.async.SingleAsyncExecuter;
import com.github.vignesh_iopex.issues.downloaders.IssueDownloader;
import com.github.vignesh_iopex.issues.models.Issue;

import java.util.List;

public class IssueListPresenter implements AsyncDownloader.DownloadListener<List<Issue>> {
  private final IssueListRenderer issueListView;
  private final SingleAsyncExecuter<List<Issue>> asyncExecuter;

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

  public void notifyFailure(String message) {
    issueListView.onError(message);
  }

  public void deliverResult(List<Issue> issues) {
    issueListView.render(issues);
  }
}
