package com.blundell.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChooserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
    }

    public void onFragmentWithPreview(View button) {
        Intent intent = new Intent(this, ExampleActivity.class);
        startActivity(intent);
    }

    public void onActivityWithPreview(View button) {
        Intent intent = new Intent(this, ActivityWithPreview.class);
        startActivity(intent);
    }

    public void onSimplestSetupActivity(View button) {
        Intent intent = new Intent(this, ActivityWithSimpleSetup.class);
        startActivity(intent);
    }
}
