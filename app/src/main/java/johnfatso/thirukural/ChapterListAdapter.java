package johnfatso.thirukural;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChapterListAdapter extends RecyclerView.Adapter<ChapterListAdapter.ChapterViewHolder> {

    class ChapterViewHolder extends RecyclerView.ViewHolder{
        TextView chapterTitle;
        TextView chapterIndex;

        public ChapterViewHolder(@NonNull View itemView, TextView chapterTitle, TextView chapterIndex) {
            super(itemView);
            this.chapterTitle = chapterTitle;
            this.chapterIndex = chapterIndex;
        }
    }

    ArrayList<ChapterEntry> chapterTitleList;

    public ChapterListAdapter(ArrayList<ChapterEntry> chapterTitleList) {
        chapterTitleList = chapterTitleList;
    }


    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_list_item, parent, false);
        TextView chapterTitle = viewHolder.findViewById(R.id.text_chapter_name);
        TextView chapterCount = viewHolder.findViewById(R.id.text_chapter_count);
        return new ChapterViewHolder(viewHolder, chapterTitle, chapterCount);
    }


    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        holder.chapterTitle.setText(chapterTitleList.get(position).getChapter());
        int index = chapterTitleList.get(position).getChapterIndex();
        holder.chapterIndex.setText(""+index);
    }


    @Override
    public int getItemCount() {
        return chapterTitleList.size();
    }
}
