package agromax.rdfbrowser.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;

import agromax.rdfbrowser.R;

/**
 * Created by Dell on 11-06-2016.
 */
public class SimpleAlertDialog extends DialogFragment {

    private String message = "";

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        message = (String) args.get("message");
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public static void launchAlertBox(@NonNull String message, @NonNull FragmentManager fragmentManager) {
        SimpleAlertDialog alert = new SimpleAlertDialog();
        Bundle msg = new Bundle();
        msg.putString("message", message);
        alert.setArguments(msg);
        alert.show(fragmentManager, "AlertDialogFragment");
    }
}
