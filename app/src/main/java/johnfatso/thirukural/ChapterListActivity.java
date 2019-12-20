package johnfatso.thirukural;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import java.util.ArrayList;

public class ChapterListActivity extends AppCompatActivity {

    private final static String LOG_TAG = "TAG";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    Repository repository;

    int palIndex;

    ArrayList<ChapterEntry> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);

        if(savedInstanceState != null) palIndex = savedInstanceState.getInt("PAL");
        else palIndex =2;

        recyclerView = findViewById(R.id.recycler_chapter_list);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        repository = new Repository(this.getApplication());

        new Repository.DaoAsyncTask(repository.getKuralDao(), repository.getChapterDao()){
            @Override
            protected Object doInBackground(Object[] objects) {
                Log.v(LOG_TAG, "retrieval started");
                return chapterDao.getChapterFromPal(palIndex);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                dataSet = toArrayList((ChapterEntry[]) o);
            }
        }.execute();

        adapter = new ChapterListAdapter(dataSet);
    }

    ArrayList<ChapterEntry> toArrayList(ChapterEntry[] array){
        ArrayList<ChapterEntry> list = new ArrayList<>();
        for (ChapterEntry entry: array){
            list.add(entry);
        }
        return list;
    }
}
