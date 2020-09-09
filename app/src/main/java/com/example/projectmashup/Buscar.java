package com.example.projectmashup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class Buscar extends DialogFragment { //implements TextView.OnEditorActionListener {

    private EditText campo;

    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View myView = inflater.inflate(R.layout.buscar, null);
        campo = (EditText) myView.findViewById(R.id.buscar);
        //campo.setOnEditorActionListener(this);
        builder.setView(myView)
                // Add action buttons
                .setPositiveButton("buscar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        //Log.d("APIREST-----list-",listener.toString());
                        EditNameDialogListener listener = (EditNameDialogListener) getActivity();
                        listener.onFinishEditDialog(campo.getText().toString());
                        //listener.onFinishEditDialog(campo.getText().toString());
                        dismiss();

                    }
                });
        return builder.create();
    }

    /**@Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            EditNameDialogListener listener = (EditNameDialogListener) getActivity();
            //Log.d("APIREST-----",campo.getText().toString());
            listener.onFinishEditDialog(campo.getText().toString());
            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }
        return false;
    }**/

}
