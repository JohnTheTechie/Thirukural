package johnfatso.thirukural;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.ArrayList;

public class ChapterListActivity extends AppCompatActivity {

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

        palIndex = savedInstanceState.getInt("PAL");

        recyclerView = findViewById(R.id.recycler_chapter_list);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        repository = new Repository(this.getApplication());

        new Repository.DaoAsyncTask(repository.getKuralDao(), repository.getChapterDao()){
            @Override
            protected Object doInBackground(Object[] objects) {
                return chapterDao.getChapterFromPal(palIndex);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                dataSet = (ArrayList<ChapterEntry>) o;
            }
        }.execute();

        adapter = new ChapterListAdapter(dataSet);
    }
}
