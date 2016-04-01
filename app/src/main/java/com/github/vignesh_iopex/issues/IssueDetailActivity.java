package com.github.vignesh_iopex.issues;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class IssueDetailActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    if (savedInstanceState != null) {
      return;
    }

    Intent intent = getIntent();
    IssueDetailFragment issueDetailFragment = new IssueDetailFragment();
    issueDetailFragment.setArguments(intent.getExtras());
    getFragmentManager().beginTransaction().add(R.id.cnt_main, issueDetailFragment).commit();
  }
}
