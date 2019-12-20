package johnfatso.thirukural;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;


@Database(entities = {KuralEntry.class, ChapterEntry.class}, version = 1)
public abstract class KuralDB extends RoomDatabase {

    static final String LOG_TAG =  "TAG";

    public abstract KuralDao getKuralDao();
    public abstract ChapterDao getChapterDao();

    private static volatile KuralDB INSTANCE;

    static KuralDB getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (KuralDB.class){
                if (INSTANCE == null){
                    INSTANCE = buildDB(context);
                }
            }
        }

        return INSTANCE;
    }

    private static KuralDB buildDB(final Context context){
        KuralDB db = Room.databaseBuilder(context.getApplicationContext(),KuralDB.class, "kural_db")
                .addCallback(new Callback() {

                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        Log.v(LOG_TAG, "DB construction started | new file creation for first time");

                        XmlPullParser kural = context.getResources().getXml(R.xml.kural);
                        XmlPullParser chapter = context.getResources().getXml(R.xml.chapter);
                        KuralDao kdao = KuralDB.getInstance(context).getKuralDao();
                        ChapterDao cdao = KuralDB.getInstance(context).getChapterDao();

                        new buildDBAsyncTask(kdao, cdao, kural, chapter).execute();
                    }
                })
                .build();



        Log.v(LOG_TAG, "DB build completed");

        return db;
    }

    private static class buildDBAsyncTask extends AsyncTask<Void, Void, Void>{

        KuralDao kuralDao;
        ChapterDao chapterDao;
        XmlPullParser kuralXML, chapterXML;

        public buildDBAsyncTask(KuralDao kuralDao, ChapterDao chapterDao, XmlPullParser kuralXML, XmlPullParser chapterXML) {
            this.kuralDao = kuralDao;
            this.chapterDao = chapterDao;
            this.kuralXML = kuralXML;
            this.chapterXML = chapterXML;

            Log.v(LOG_TAG, ""+this.getClass().getName()+" | build task constructed");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //TODO: implement kural xml parsers and write to DB
            XMLParser parser = new XMLParser();
            try{

                chapterDao.insert( toChapterArray(parser.parseKural(chapterXML, XMLParser.ADHIKARAM)));
                kuralDao.insert( toKuralArray(parser.parseKural(kuralXML, XMLParser.KURAL)));
                Log.v(LOG_TAG, ""+this.getClass().getName()+" | insert task completed");
            }catch (Exception e){
                Log.v(LOG_TAG, "Exception thrown Kural DB | "+e.toString());
            }


            return null;
        }

        ChapterEntry[] toChapterArray(ArrayList<ChapterEntry> list){
            ChapterEntry[] array = new ChapterEntry[list.size()];

            for(int i =0; i<list.size(); i++){
                array[i] = list.get(i);
            }

            return array;
        }

        KuralEntry[] toKuralArray(ArrayList<KuralEntry> list){
            KuralEntry[] array = new KuralEntry[list.size()];

            for(int i =0; i<list.size(); i++){
                array[i] = list.get(i);
            }

            return array;
        }
    }

}
