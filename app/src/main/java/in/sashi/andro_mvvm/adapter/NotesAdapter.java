package in.sashi.andro_mvvm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.sashi.andro_mvvm.R;
import in.sashi.andro_mvvm.entities.Note;
import in.sashi.andro_mvvm.viewholders.NotesViewHolder;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {

//    private final Context context;
    private List<Note> itemsList;
    private onNoteItemClickListener clickListener;

//    public NotesAdapter(Context context, List<Note> itemsList) {
//        this.context = context;
//        this.itemsList = itemsList;
//    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_items, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder viewholder, final int position) {
        Note note = itemsList.get(position);
        viewholder.titleTV.setText(note.getTitle());
        viewholder.descTV.setText(note.getDesc());
        viewholder.priorityTV.setText(note.getPriority() + "");

        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null && position != RecyclerView.NO_POSITION){
                    clickListener.onNoteClicked(itemsList.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        }
        return itemsList.size();
    }

    public void setItems(List<Note> notes){
        this.itemsList = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int posn) {
        return itemsList.get(posn);
    }

    public interface onNoteItemClickListener {
        void onNoteClicked(Note note);
    }

    public void setClickListener(onNoteItemClickListener mClickListener){
        this.clickListener = mClickListener;
    }
}