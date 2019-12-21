package johnfatso.thirukural;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

public class ChapterListActivity extends AppCompatActivity implements OnClickListener {

    private final static String LOG_TAG = "TAG";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    Repository repository;

    int palIndex;

    ArrayList<ChapterEntry> dataSet;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);

            Intent intent = getIntent();
            palIndex = intent.getIntExtra("PAL", 1);


        recyclerView = findViewById(R.id.recycler_chapter_list);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        repository = new Repository(this.getApplication());

        actionBar = getSupportActionBar();

        switch (palIndex){
            case 1:
                actionBar.setTitle(R.string.aram_topic);
                break;

            case 2:
                actionBar.setTitle(R.string.porul_topic);
                break;

            case 3:
                actionBar.setTitle(R.string.inbam_topic);
                break;
        }

        new Repository.DaoAsyncTask(repository.getKuralDao(), repository.getChapterDao()){
            @Override
            protected Object doInBackground(Object[] objects) {
                Log.v(LOG_TAG, "retrieval started");
                return chapterDao.getChapterFromPal(palIndex);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Log.v(LOG_TAG, "retrieval completed");
                dataSet = toArrayList((ChapterEntry[]) o);
                Log.v(LOG_TAG, "dataset size :"+dataSet.size());
                adapter = new ChapterListAdapter(dataSet, ChapterListActivity.this::onClick);
                recyclerView.setAdapter(adapter);
            }
        }.execute();


    }

    ArrayList<ChapterEntry> toArrayList(ChapterEntry[] array){
        ArrayList<ChapterEntry> list = new ArrayList<>();
        for (ChapterEntry entry: array){
            list.add(entry);
        }
        return list;
    }

    @Override
    public void onClick(View view) {
        int position = recyclerView.getChildLayoutPosition(view);

        Intent intent = new Intent(this, KuralActivity.class);
        intent.putExtra("CHAPTER", dataSet.get(position).getChapterIndex());
        intent.putExtra("CHAPTER_TITLE", dataSet.get(position).getChapter());
        intent.putExtra("FAV", false);

        startActivity(intent);
    }
}
