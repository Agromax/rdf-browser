package agromax.rdfbrowser.browser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import agromax.rdfbrowser.R;
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
        setSupportActionBar(myToolbar);


        if (!loadVocabulary()) {
            SimpleAlertDialog alert = new SimpleAlertDialog();
            Bundle msg = new Bundle();
            msg.putString("message", getString(R.string.vocabulary_not_found));
            alert.setArguments(msg);
            alert.show(getFragmentManager(), "AlertDialogFragment");
        } else {
            state = new BrowserActivityState(vocabulary, this, findViewById(R.id.action_view), findViewById(R.id.browser_list_view));
        }

        setupFiltering();
        setupOptions();
        if (state != null) {
            state.renderState();
        }
        hierarchyButtonHandler();
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
            ArrayList<String> res = state.query(constraint);
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
        final Browser self = this;
        Button button = (Button) findViewById(R.id.hierarchy_button);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ch = state.getCurrentHierarchy();
                    if (ch.isEmpty()) {
                        ch = "You are at the root";
                    }
                    SimpleAlertDialog.launchAlertBox(ch, self.getFragmentManager());
                }
            });
        }
    }
}
