package com.github.vignesh_iopex.issues.async;

import android.os.AsyncTask;

import com.github.vignesh_iopex.issues.downloaders.DownloadAction;

public class SingleAsyncExecuter<T> {
  private final DownloadAction<T> downloadAction;
  private AsyncTask lastProcess;

  public SingleAsyncExecuter(DownloadAction<T> downloadAction) {
    this.downloadAction = downloadAction;
  }

  public void execute(String link, AsyncDownloader.DownloadListener<T> downloadListener) {
    cancel();
    lastProcess = new AsyncDownloader(downloadAction, downloadListener).execute(link);
  }

  public void cancel() {
    if (lastProcess == null) {
      return;
    }
    lastProcess.cancel(true);
  }
}
