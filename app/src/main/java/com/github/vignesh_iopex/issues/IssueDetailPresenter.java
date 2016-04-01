package com.github.vignesh_iopex.issues;

import com.github.vignesh_iopex.issues.async.AsyncDownloader;
import com.github.vignesh_iopex.issues.async.SingleAsyncExecuter;
import com.github.vignesh_iopex.issues.downloaders.DownloadAction;
import com.github.vignesh_iopex.issues.models.Comment;
import com.github.vignesh_iopex.issues.models.Issue;

import java.util.List;

public class IssueDetailPresenter implements AsyncDownloader.DownloadListener<List<Comment>> {
  private final CommentListRenderer commentListView;
  private final SingleAsyncExecuter<List<Comment>> asyncExecuter;

  public IssueDetailPresenter(CommentListRenderer commentListView,
                              DownloadAction<List<Comment>> commentDownloader) {
    this(commentListView, new SingleAsyncExecuter<>(commentDownloader));
  }

  public IssueDetailPresenter(CommentListRenderer commentListView,
                              SingleAsyncExecuter<List<Comment>> asyncExecuter) {
    this.asyncExecuter = asyncExecuter;
    this.commentListView = commentListView;
  }

  public void downloadComments(Issue issue) {
    asyncExecuter.execute(issue.getCommentsLink(), this);
  }

  public void close() {
    asyncExecuter.cancel();
  }

  public void notifyFailure(String message) {
    commentListView.onError(message);
  }

  public void deliverResult(List<Comment> comments) {
    commentListView.render(comments);
  }
}
