package agromax.rdfbrowser.rdfviewer;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import agromax.rdfbrowser.R;

public class RDFViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdfviewer);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        myToolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(myToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(getString(R.string.rdf_viewer_title));

    }
}
