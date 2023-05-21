package com.example.myapplication;

import androidx.cardview.widget.CardView;

public interface NotesClickListener {

    void onClick(Notes notes);
    void onLongClick(Notes notes, CardView cardView);

}
