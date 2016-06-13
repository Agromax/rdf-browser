package agromax.rdfbrowser.browser;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import java.util.ArrayList;

import agromax.rdfbrowser.R;
import agromax.rdfbrowser.rdfviewer.RDFViewer;
import agromax.rdfbrowser.util.SimpleAlertDialog;
import agromax.rdfbrowser.vocabulary.VocabManager;
import agromax.rdfbrowser.vocabulary.Vocabulary;

public class Browser extends AppCompatActivity {

    private Vocabulary vocabulary;
    private BrowserActivityState state = null;
    private final ArrayList<String> options = new ArrayList<>();
    private ListView optionList = null;
    private OptionsAdapter optionsAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.browser_toolbar);
        myToolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        final Browser self = this;

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                return loadVocabulary();
            }

            @Override
            protected void onPostExecute(Boolean vocabLoaded) {
                if (!vocabLoaded) {
                    SimpleAlertDialog.launchAlertBox(getString(R.string.vocabulary_not_found), getFragmentManager());
                } else {
                    state = new BrowserActivityState(vocabulary, self, findViewById(R.id.action_view), findViewById(R.id.browser_list_view));
                }
                if (state != null) {
                    state.renderState();
                    setupFiltering();
                    setupOptions();
                    hierarchyButtonHandler();
                }
            }
        }.execute();
    }


    private void setupOptions() {
        optionsAdapter = new OptionsAdapter(this, options, state);
        optionList = (ListView) findViewById(R.id.browser_list_view);
        if (optionList != null) {
            optionList.setAdapter(optionsAdapter);
        }
    }

    private boolean loadVocabulary() {
        vocabulary = new VocabManager(this).getVocabulary();
        return vocabulary != null;
    }

    private void setupFiltering() {
        final EditText editText = (EditText) findViewById(R.id.search_options);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        };
        editText.addTextChangedListener(textWatcher);
    }

    private void filter(String constraint) {
        if (state != null) {
            ArrayList<String> res = state.filterQuery(constraint);
            options.clear();
            options.addAll(res);
            System.out.println(options);
            optionsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        if (state != null && state.moveUp()) {
            return;
        }
        super.onBackPressed();
    }

    private void hierarchyButtonHandler() {
        final HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hierarchy_scroll_view);
        hsv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                hsv.fullScroll(View.FOCUS_RIGHT);
            }
        });
    }
}
