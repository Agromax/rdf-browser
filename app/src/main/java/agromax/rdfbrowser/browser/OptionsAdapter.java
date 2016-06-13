package agromax.rdfbrowser.browser;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import agromax.rdfbrowser.R;
import agromax.rdfbrowser.rdfviewer.RDFViewer;
import agromax.rdfbrowser.util.XMLUtil;

/**
 * Created by Dell on 11-06-2016.
 */
public class OptionsAdapter extends BaseAdapter {
    private final ArrayList<String> data;
    private final Context context;
    private final LayoutInflater inflater;
    private final BrowserActivityState state;

    public OptionsAdapter(@NonNull Context context, @NonNull ArrayList<String> data, @NonNull BrowserActivityState state) {
        this.context = context;
        this.data = data;
        this.state = state;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void resetData(List<String> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.browser_option_layout, null);
        }
        final Button content = (Button) view.findViewById(R.id.browser_option_text);
        content.setText(data.get(position));
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state.moveDown(XMLUtil.asXMLTag(data.get(position)));
            }
        });

        Button viewRDF = (Button) view.findViewById(R.id.view_rdf_button);
        viewRDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RDFViewer.class);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
