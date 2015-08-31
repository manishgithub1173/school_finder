package com.hackathon.sequoia.sequoiahackathon.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hackathon.sequoia.sequoiahackathon.R;
import com.hackathon.sequoia.sequoiahackathon.views.FontedTextView;

/**
 * Created by sudarshanbhat on 27/01/15.
 */
public class SimpleDialogFragment extends DialogFragment {

    private static final String MESSAGE = "message";

    public static SimpleDialogFragment newInstance(String message) {

        Bundle bundle = new Bundle();
        bundle.putString(MESSAGE, message);
        SimpleDialogFragment dialogFragment = new SimpleDialogFragment();
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle configBundle = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater li = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.dialog_message, null);
        TextView messageView = (FontedTextView) view.findViewById(android.R.id.message);
        messageView.setText(configBundle.getString(MESSAGE));
        builder.setView(view);
        builder.setPositiveButton("OK", null);
        return builder.create();
    }
}
