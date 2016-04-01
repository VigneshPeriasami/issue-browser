package com.github.vignesh_iopex.issues;

public interface Renderer<T> {
  void render(T data);

  void onError(String message);
}
