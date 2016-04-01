package com.github.vignesh_iopex.issues.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Issue implements Parcelable {
  private String author;
  private String title;
  private String issueBody;
  private String commentsLink;
  private String number;

  private Issue(Parcel parcel) {
    author = parcel.readString();
    number = parcel.readString();
    title = parcel.readString();
    issueBody = parcel.readString();
    commentsLink = parcel.readString();
  }

  private Issue(String author, String number, String title, String issueBody, String commentsLink) {
    this.author = author;
    this.number = number;
    this.title = title;
    this.issueBody = issueBody;
    this.commentsLink = commentsLink;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getCommentsLink() {
    return commentsLink;
  }

  public String getIssueBody() {
    return issueBody;
  }

  public String getNumber() {
    return number;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(author);
    parcel.writeString(number);
    parcel.writeString(title);
    parcel.writeString(issueBody);
    parcel.writeString(commentsLink);
  }

  public static final Creator<Issue> CREATOR = new Creator<Issue>() {
    @Override public Issue createFromParcel(Parcel parcel) {
      return new Issue(parcel);
    }

    @Override public Issue[] newArray(int i) {
      return new Issue[i];
    }
  };

  public static class Builder {
    private String author;
    private String title;
    private String body;
    private String number;
    private String commentsLink;

    public Builder author(String author) {
      this.author = author;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder body(String body) {
      this.body = body;
      return this;
    }

    public Builder number(String number) {
      this.number = number;
      return this;
    }

    public Builder commentsLink(String link) {
      this.commentsLink = link;
      return this;
    }

    public Issue build() {
      return new Issue(author, number, title, body, commentsLink);
    }
  }
}
