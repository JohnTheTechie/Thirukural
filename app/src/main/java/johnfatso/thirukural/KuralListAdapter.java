package johnfatso.thirukural;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KuralListAdapter extends RecyclerView.Adapter<KuralListAdapter.KuralViewHolder> {

    private final String LOG_TAG = "TAG";

    ArrayList<KuralEntry> kuralEntryList;

    public KuralListAdapter(ArrayList<KuralEntry> kuralEntryList) {
        this.kuralEntryList = kuralEntryList;
    }


    @NonNull
    @Override
    public KuralViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View VH = LayoutInflater.from(parent.getContext()).inflate(R.layout.kural_list_item, parent, false);
        TextView kural_text = VH.findViewById(R.id.text_kural);
        TextView kural_index = VH.findViewById(R.id.text_kural_count);
        ImageView favIcon = VH.findViewById(R.id.button_set_favourite);
        return new KuralViewHolder(VH, kural_text, kural_index, favIcon);
    }


    @Override
    public void onBindViewHolder(@NonNull KuralViewHolder holder, int position) {
        String text = kuralEntryList.get(position).getKural();
        String index = ""+kuralEntryList.get(position).getVerseIndex();
        boolean isFavourite = kuralEntryList.get(position).isFavourite();


        holder.kural_text.setText(text);
        holder.kural_index_text.setText(index);
        if(isFavourite){
            holder.favourite_icon.setImageResource(android.R.drawable.btn_star_big_on);
        }
        else {
            holder.favourite_icon.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    @Override
    public int getItemCount() {
        return kuralEntryList.size();
    }

    class KuralViewHolder extends RecyclerView.ViewHolder{

        TextView kural_text;
        TextView kural_index_text;
        ImageView favourite_icon;

        public KuralViewHolder(@NonNull View itemView, TextView kural_text, TextView kural_index_text, ImageView favourite_icon) {
            super(itemView);
            this.kural_text = kural_text;
            this.kural_index_text = kural_index_text;
            this.favourite_icon = favourite_icon;
        }
    }
}
