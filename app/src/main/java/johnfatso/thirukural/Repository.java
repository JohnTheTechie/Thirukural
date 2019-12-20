package johnfatso.thirukural;

import android.app.Application;
import android.os.AsyncTask;

public class Repository {
    private static final String LOG_TAG = "TAG";

    private KuralDao kuralDao;
    private ChapterDao chapterDao;

    public Repository(Application application) {
        KuralDB db = KuralDB.getInstance(application);
        kuralDao = db.getKuralDao();
        chapterDao = db.getChapterDao();
    }

    public KuralDao getKuralDao() {
        return kuralDao;
    }

    public ChapterDao getChapterDao() {
        return chapterDao;
    }

    public static abstract class DaoAsyncTask extends AsyncTask{
        KuralDao kuralDao;
        ChapterDao chapterDao;

        public DaoAsyncTask(KuralDao kuralDao, ChapterDao chapterDao) {
            this.kuralDao = kuralDao;
            this.chapterDao = chapterDao;
        }
    }
}
