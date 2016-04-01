package com.github.vignesh_iopex.issues;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    if (savedInstanceState != null) {
      return;
    }
    getFragmentManager().beginTransaction().add(R.id.cnt_main, new IssueListFragment()).commit();
  }
}
