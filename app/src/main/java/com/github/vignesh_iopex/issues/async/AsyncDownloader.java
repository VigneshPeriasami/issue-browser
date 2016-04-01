package com.github.vignesh_iopex.issues.async;

import android.os.AsyncTask;

import com.github.vignesh_iopex.issues.downloaders.DownloadAction;

import java.io.IOException;

public class AsyncDownloader extends AsyncTask<String, Void, Object> {
  public static final String ERROR_NO_CONNECTION = "No internet connection";
  public static final String ERROR_NO_DATA = "NO data found";

  private final DownloadAction downloadAction;
  private boolean networkFailure;
  private final DownloadListener listener;

  public <T> AsyncDownloader(DownloadAction<T> downloadAction, DownloadListener<T> listener) {
    this.downloadAction = downloadAction;
    this.listener = listener;
  }

  @Override protected Object doInBackground(String... params) {
    try {
      return downloadAction.download(params[0]);
    } catch (IOException e) {
      networkFailure = true;
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void onPostExecute(Object t) {
    super.onPostExecute(t);
    if (isCancelled()) {
      return;
    }

    if (networkFailure) {
      listener.notifyFailure(ERROR_NO_CONNECTION);
    } else if (t == null) {
      listener.notifyFailure(ERROR_NO_DATA);
    } else {
      listener.deliverResult(t);
    }
  }

  public interface DownloadListener<T> {
    void notifyFailure(String message);

    void deliverResult(T message);
  }
}
