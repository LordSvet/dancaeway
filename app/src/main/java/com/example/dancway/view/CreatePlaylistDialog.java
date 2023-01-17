package com.example.dancway.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.dancway.R;
import com.example.dancway.model.Playlists;
import com.example.dancway.model.SinglePlaylist;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class CreatePlaylistDialog extends AppCompatDialogFragment {
    EditText editText;
    Button button;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout,null);

        dialogBuilder.setView(view);

        editText = view.findViewById(R.id.edit_text_create_playlist);
        button = view.findViewById(R.id.dialog_create_playlist);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Playlists.getInstance().addPlaylist(new SinglePlaylist(editText.getText().toString(),new ArrayList<>()));
                Intent intent = new Intent(getActivity(), SinglePlaylistActivity.class);
                intent.putExtra("position", Playlists.getInstance().getNoOfPlaylists()-1);
                intent.putExtra("name", editText.getText().toString());
                startActivity(intent);
                dismiss();
            }
        });

        return dialogBuilder.create();
    }
}
