package agromax.rdfbrowser.browser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;

import agromax.rdfbrowser.R;
import agromax.rdfbrowser.vocabulary.Vocabulary;

/**
 * Created by Dell on 11-06-2016.
 */
public class BrowserActivityState {
    private final Vocabulary vocabulary;
    private final LinkedList<String> queryState = new LinkedList<>();
    private final Context context;
    private final View optionsView;
    private final View actionView;

    public BrowserActivityState(@NonNull Vocabulary vocabulary, @NonNull Context context, @NonNull View actionView, @NonNull View optionsView) {
        this.vocabulary = vocabulary;
        this.context = context;
        this.actionView = actionView;
        this.optionsView = optionsView;
    }


    public ArrayList<String> query(String part) {
        if (part == null) {
            part = "";
        }
        return vocabulary.query(getPath(), part);
    }

    private String getPath() {
        StringBuilder path = new StringBuilder();
        for (String pe : queryState) {
            path.append(pe).append("/");
        }
        return path.toString();
    }

    public boolean moveUp() {
        if (queryState.size() > 0) {
            queryState.removeLast();
            renderState();
            return true;
        }
        return false;
    }

    public void moveDown(String tag) {
        queryState.add(tag);
        renderState();
    }

    public void renderState() {

        // Clear the search box
        EditText search = (EditText) actionView.findViewById(R.id.search_options);
        search.setText("");

        // Change the title
        populateHierarchy();

        // Populate the options
        ArrayList<String> res = query("");
        ListView listView = (ListView) optionsView.findViewById(R.id.browser_list_view);
        OptionsAdapter adapter = (OptionsAdapter) listView.getAdapter();
        adapter.resetData(res);
        adapter.notifyDataSetChanged();
    }

    private void populateHierarchy() {
        LinearLayout hierarchy = (LinearLayout) actionView.findViewById(R.id.hierarchy_view);
        hierarchy.removeAllViews();

        for (String s : queryState) {
            Button term = new Button(context);
            term.setAllCaps(false);
            term.setText(s);
            hierarchy.addView(term);
        }

        if (queryState.isEmpty()) {
            Button term = new Button(context);
            term.setText("Vocabulary");
            term.setAllCaps(false);
            hierarchy.addView(term);
        }
    }

    public String getCurrentHierarchy() {
        StringBuilder sb = new StringBuilder();
        for (String t : queryState) {
            sb.append(t).append(" > ");
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 3, sb.length());
        }
        return sb.toString();
    }
}
