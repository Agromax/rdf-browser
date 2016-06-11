package agromax.rdfbrowser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import agromax.rdfbrowser.browser.Browser;
import agromax.rdfbrowser.vocabulary.VocabManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        VocabManager.downloadVocabulary(this);
    }
}
