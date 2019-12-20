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
                        XmlPullParser kural = context.getResources().getXml(R.xml.kural);
                        XmlPullParser chapter = context.getResources().getXml(R.xml.chapter);
                        KuralDao kdao = KuralDB.getInstance(context).getKuralDao();
                        ChapterDao cdao = KuralDB.getInstance(context).getChapterDao();

                        new buildDBAsyncTask(kdao, cdao, kural, chapter).execute();
                    }
                })
                .build();

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
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //TODO: implement kural xml parsers and write to DB
            XMLParser parser = new XMLParser();
            try{
                kuralDao.insert((KuralEntry[]) toArray(parser.parseKural(kuralXML, XMLParser.KURAL)));
                chapterDao.insert((ChapterEntry[]) toArray(parser.parseKural(chapterXML, XMLParser.ADHIKARAM)));
            }catch (Exception e){
                Log.v(LOG_TAG, "Exception thrown | "+e.toString());
            }


            return null;
        }

        Object[] toArray(ArrayList list){
            Object[] array = new Object[list.size()];

            for(int i =0; i<list.size(); i++){
                array[i] = list.get(i);
            }

            return array;
        }
    }

}
