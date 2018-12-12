package in.sashi.andro_mvvm.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.sashi.andro_mvvm.R;

public class NotesViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTV, descTV, priorityTV;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTV = itemView.findViewById(R.id.titleTV);
        descTV = itemView.findViewById(R.id.descTV);
        priorityTV = itemView.findViewById(R.id.priorityTV);
    }
}
