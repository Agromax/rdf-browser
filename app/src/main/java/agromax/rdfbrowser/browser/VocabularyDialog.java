package agromax.rdfbrowser.browser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import agromax.rdfbrowser.R;
import agromax.rdfbrowser.vocabulary.VocabManager;

/**
 * Created by Dell on 13-06-2016.
 */
public class VocabularyDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View view = inflater.inflate(R.layout.vocab_dialog_layout, null);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.vocab_dialog_download_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText url = (EditText) view.findViewById(R.id.vocab_url);
                        String uri = null;
                        if (url != null) {
                            uri = url.toString();
                        }
                        VocabManager.downloadVocabulary(getActivity(), uri);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        VocabularyDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
