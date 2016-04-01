package com.github.vignesh_iopex.issues.downloaders;

import android.util.JsonReader;

import com.github.vignesh_iopex.issues.models.Issue;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class IssueDownloader implements DownloadAction<List<Issue>> {

  @Override public List<Issue> download(String link) throws IOException {
    HttpURLConnection urlConnection = (HttpURLConnection) new URL(link).openConnection();
    urlConnection.setRequestMethod("GET");
    InputStreamReader streamReader = new InputStreamReader(urlConnection.getInputStream());
    return readIssuesArray(streamReader);
  }

  private List<Issue> readIssuesArray(InputStreamReader reader) throws IOException {
    List<Issue> issues = new ArrayList<>();
    JsonReader jsonReader = new JsonReader(reader);
    jsonReader.beginArray();
    while (jsonReader.hasNext()) {
      issues.add(readIssue(jsonReader));
    }
    jsonReader.endArray();
    jsonReader.close();
    return issues;
  }

  private Issue readIssue(JsonReader reader) throws IOException {
    Issue.Builder builder = new Issue.Builder();

    reader.beginObject();
    while (reader.hasNext()) {
      String jsonKeyName = reader.nextName();
      if ("comments_url".equals(jsonKeyName)) {
        builder.commentsLink(reader.nextString());
      } else if ("body".equals(jsonKeyName)) {
        builder.body(reader.nextString());
      } else if ("title".equals(jsonKeyName)) {
        builder.title(reader.nextString());
      } else if ("user".equals(jsonKeyName)) {
        builder.author(readUserName(reader));
      } else if ("number".equals(jsonKeyName)) {
        builder.number(reader.nextString());
      } else {
        reader.skipValue();
      }
    }
    reader.endObject();
    return builder.build();
  }

  private String readUserName(JsonReader reader) throws IOException {
    String name = null;
    reader.beginObject();
    while (reader.hasNext()) {
      String jsonKeyName = reader.nextName();
      if ("login".equals(jsonKeyName)) {
        name = reader.nextString();
      } else {
        reader.skipValue();
      }
    }
    reader.endObject();
    return name;
  }
}
