package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter<NotesViewHolder>{
    Context context;

    List<Notes> listNotes;

    NotesClickListener listener;

    public NotesListAdapter(Context context, List<Notes> listNotes, NotesClickListener listener) {
        this.context = context;
        this.listNotes = listNotes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.tv_title.setText(listNotes.get(position).getTitle());
        holder.tv_title.setSelected(true);

        holder.tv_notes.setText(listNotes.get(position).getNote());

        holder.tv_date.setText(listNotes.get(position).getDate());
        holder.tv_date.setSelected(true);

        if(listNotes.get(position).isPinned()){
            holder.iv_pin.setImageResource(R.drawable.icon_pin);
        }
        else {
            holder.iv_pin.setImageResource(0);
        }

        int color_code = getRandomColor();
        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));


        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(listNotes.get(holder.getAdapterPosition()));
            }
        });

        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(listNotes.get(holder.getAdapterPosition()), holder.notes_container);
                return true;
            }
        });

    }

    private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public void filterList(List<Notes> filteredList){
        listNotes = filteredList;
        notifyDataSetChanged();
    }
}
class NotesViewHolder extends RecyclerView.ViewHolder {
    CardView notes_container;
    TextView tv_title, tv_notes, tv_date;
    ImageView iv_pin;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        notes_container = itemView.findViewById(R.id.notes_container);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_notes = itemView.findViewById(R.id.tv_notes);
        tv_date = itemView.findViewById(R.id.tv_date);
        iv_pin = itemView.findViewById(R.id.iv_pin);
    }
}