package johnfatso.thirukural;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int ARAM_PAL_INDEX = 1;
    private static final int PORUL_PAL_INDEX = 2;
    private static final int INBAM_PAL_INDEX = 3;

    Button button_aram, button_porul, button_inbam, button_favourite;
    ImageView info;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_aram = findViewById(R.id.button_amin_aram_activity_start);
        button_aram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchChapterListActivity(ARAM_PAL_INDEX);
            }
        });

        button_porul = findViewById(R.id.button_main_porul_activity_start);
        button_porul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchChapterListActivity(PORUL_PAL_INDEX);
            }
        });

        button_inbam = findViewById(R.id.button_main_inbam_activity_start);
        button_inbam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchChapterListActivity(INBAM_PAL_INDEX);
            }
        });

        button_favourite = findViewById(R.id.button_main_favourites);
        button_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchKuralListActivity(view);
            }
        });

        info = findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchInfoScreen(view);
            }
        });

        repository = new Repository(this.getApplication());

        new Repository.DaoAsyncTask(repository.getKuralDao(), repository.getChapterDao()){
            @Override
            protected Object doInBackground(Object[] objects) {
                chapterDao.getChapterName(1);
                return null;
            }
        }.execute();

    }

    void launchChapterListActivity(int palIndex){
        Intent intent= new Intent(this, ChapterListActivity.class);
        intent.putExtra("PAL", palIndex);
        intent.putExtra("FAV", false);
        startActivity(intent);
    }

    void launchKuralListActivity(View view){
        Intent intent= new Intent(this, KuralActivity.class);
        intent.putExtra("FAV", true);
        startActivity(intent);
    }

    void launchInfoScreen(View view){
        Intent intent = new Intent(this, infoActivity.class);
        startActivity(intent);
    }
}
