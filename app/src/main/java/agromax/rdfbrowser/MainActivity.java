package agromax.rdfbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import agromax.rdfbrowser.browser.Browser;
import agromax.rdfbrowser.browser.VocabularyDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        myToolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(myToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(getString(R.string.main_toolbar_title));

        Button browseButton = (Button) findViewById(R.id.browse_button);
        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBrowse();
            }
        });

        Button downloadButton = (Button) findViewById(R.id.download_button);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadVocabulary();
            }
        });
    }

    private void handleBrowse() {
        Intent intent = new Intent(this, Browser.class);
        startActivity(intent);
    }

    private void downloadVocabulary() {
        new VocabularyDialog().show(getFragmentManager(), "VocabDialog");
//        VocabManager.downloadVocabulary(this);
    }
}
