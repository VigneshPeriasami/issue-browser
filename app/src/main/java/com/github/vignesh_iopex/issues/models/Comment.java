package com.github.vignesh_iopex.issues.models;

public class Comment {
  private final String author;
  private final String body;

  private Comment(String author, String body) {
    this.author = author;
    this.body = body;
  }

  public String getAuthor() {
    return author;
  }

  public String getBody() {
    return body;
  }

  public static class Builder {
    private String author;
    private String body;

    public Builder author(String name) {
      author = name;
      return this;
    }

    public Builder body(String body) {
      this.body = body;
      return this;
    }

    public Comment build() {
      return new Comment(author, body);
    }
  }
}
