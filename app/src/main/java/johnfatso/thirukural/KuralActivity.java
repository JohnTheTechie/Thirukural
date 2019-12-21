package johnfatso.thirukural;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class KuralActivity extends AppCompatActivity {

    private final String LOG_TAG = "TAG";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    private int chapter_index;
    private String chapter_title;
    private boolean isFavouriteToBeDisplayed;

    Repository repository;

    ArrayList<KuralEntry> dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kural);

        Intent intent = getIntent();
        this.chapter_index = intent.getIntExtra("CHAPTER", 1);
        this.isFavouriteToBeDisplayed = intent.getBooleanExtra("FAV", false);

        repository = new Repository(this.getApplication());

        recyclerView = findViewById(R.id.recycler_kural_list);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        new Repository.DaoAsyncTask(repository.getKuralDao(), repository.getChapterDao()){
            @Override
            protected Object doInBackground(Object[] objects) {
                if(isFavouriteToBeDisplayed){
                    return kuralDao.getFavouriteKurals(isFavouriteToBeDisplayed);
                }
                else return kuralDao.getVersesFromChapter(chapter_index);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                dataset = toArrayList((KuralEntry[]) o);
                adapter = new KuralListAdapter(dataset);
                recyclerView.setAdapter(adapter);
            }
        }.execute();
    }

    ArrayList<KuralEntry> toArrayList(KuralEntry[] array){
        ArrayList<KuralEntry> list = new ArrayList<>();
        for (KuralEntry entry: array){
            list.add(entry);
        }
        return list;
    }
}
