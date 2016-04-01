package com.github.vignesh_iopex.issues.downloaders;

import android.util.JsonReader;

import com.github.vignesh_iopex.issues.models.Comment;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CommentDownloader implements DownloadAction<List<Comment>> {

  @Override public List<Comment> download(String commentLink) throws IOException {
    return downloadComments(commentLink);
  }

  public List<Comment> downloadComments(String link) throws IOException {
    HttpURLConnection urlConnection = (HttpURLConnection) new URL(link).openConnection();
    urlConnection.setRequestMethod("GET");
    InputStreamReader streamReader = new InputStreamReader(urlConnection.getInputStream());
    return readCommentsArray(streamReader);
  }

  private List<Comment> readCommentsArray(InputStreamReader reader) throws IOException {
    List<Comment> comments = new ArrayList<>();
    JsonReader jsonReader = new JsonReader(reader);
    jsonReader.beginArray();
    while (jsonReader.hasNext()) {
      comments.add(readComment(jsonReader));
    }
    jsonReader.endArray();
    jsonReader.close();
    return comments;
  }

  private Comment readComment(JsonReader jsonReader) throws IOException {
    Comment.Builder builder = new Comment.Builder();
    jsonReader.beginObject();
    while (jsonReader.hasNext()) {
      String jsonKey = jsonReader.nextName();
      if ("body".equals(jsonKey)) {
        builder.body(jsonReader.nextString());
      } else if ("user".equals(jsonKey)) {
        builder.author(readUsername(jsonReader));
      } else {
        jsonReader.skipValue();
      }
    }
    jsonReader.endObject();
    return builder.build();
  }

  private String readUsername(JsonReader jsonReader) throws IOException {
    jsonReader.beginObject();
    String name = null;
    while (jsonReader.hasNext()) {
      String jsonKey = jsonReader.nextName();
      if ("login".equals(jsonKey)) {
        name = jsonReader.nextString();
      } else {
        jsonReader.skipValue();
      }
    }
    jsonReader.endObject();
    return name;
  }
}
