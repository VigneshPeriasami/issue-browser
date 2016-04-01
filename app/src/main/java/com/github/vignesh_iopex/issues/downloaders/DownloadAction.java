package com.github.vignesh_iopex.issues.downloaders;

import java.io.IOException;

public interface DownloadAction<T> {
  T download(String link) throws IOException;
}
