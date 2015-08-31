package com.hackathon.sequoia.sequoiahackathon.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by manishkumar on 29/08/15.
 */
public class ProgressDialogFragment extends DialogFragment {

    public static final String TAG = "com.hackathon.sequoia.sequoiahackathon.fragment.ProgressDialog";

    private static final String MESSAGE = "message";

    public static ProgressDialogFragment newInstance(String message) {
        Bundle bundle = new Bundle();
        ProgressDialogFragment dialogFragment = new ProgressDialogFragment();
        bundle.putString(MESSAGE, message);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle configBundle = getArguments();

        String message = configBundle.getString(MESSAGE);
        if(message == null || message.equals("")) {
            message = "Please wait...";
        }

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }
}
