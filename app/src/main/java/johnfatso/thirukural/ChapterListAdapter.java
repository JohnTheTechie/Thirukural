package johnfatso.thirukural;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChapterListAdapter extends RecyclerView.Adapter<ChapterListAdapter.ChapterViewHolder> {

    private static final String LOG_TAG = "TAG";

    class ChapterViewHolder extends RecyclerView.ViewHolder{
        TextView chapterTitle;
        TextView chapterIndex;
        ChapterEntry chapterEntry;

        public ChapterViewHolder(@NonNull View itemView, TextView chapterTitle, TextView chapterIndex) {
            super(itemView);
            this.chapterTitle = chapterTitle;
            this.chapterIndex = chapterIndex;
        }
    }

    ArrayList<ChapterEntry> chapterTitleList;
    View.OnClickListener listener;

    public ChapterListAdapter(ArrayList<ChapterEntry> chapterTitleList, View.OnClickListener listener) {
        this.chapterTitleList = chapterTitleList;
        this.listener =listener;
    }


    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_list_item, parent, false);
        TextView chapterTitle = viewHolder.findViewById(R.id.text_chapter_name);
        TextView chapterCount = viewHolder.findViewById(R.id.text_chapter_count);
        Log.v(LOG_TAG, "ViewHolder Created");
        viewHolder.setOnClickListener(listener);
        return new ChapterViewHolder(viewHolder, chapterTitle, chapterCount);
    }


    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        String kural = chapterTitleList.get(position).getChapter();
        int index = chapterTitleList.get(position).getChapterIndex();
        Log.v(LOG_TAG, "Binding initiated for chapter entry ("+kural+", "+index+")");
        holder.chapterTitle.setText(kural);
        holder.chapterIndex.setText(""+index);

        holder.chapterEntry = chapterTitleList.get(position);
    }


    @Override
    public int getItemCount() {
        return chapterTitleList.size();
    }


}
