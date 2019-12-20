package johnfatso.thirukural;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class XMLParser {

    private static final String ns = null;
    private static final String LOG_TAG = "DB";

    public static final int KURAL = 1;
    public static final int ADHIKARAM = 2;

    public ArrayList parseKural(XmlPullParser kuralXml, int xmldoc) throws XmlPullParserException, IOException{
        try {
            kuralXml.next();
            kuralXml.next();
            return readData(kuralXml, xmldoc);
        }finally {
            Log.v(LOG_TAG, ""+this.getClass().getName()+" | ParseKural() | ");
            Log.v(LOG_TAG, ""+this.getClass().getName()+" | ParseKural() | parse completed");
        }
    }

    private ArrayList readData(XmlPullParser parser, int xmldoc) throws XmlPullParserException, IOException {
        ArrayList<KuralEntry> list;
        ArrayList<ChapterEntry> clist;
        list = new ArrayList<KuralEntry>();
        clist = new ArrayList<ChapterEntry>();
        Log.v(LOG_TAG, ""+this.getClass().getName()+" | data reading entered");

        parser.require(XmlPullParser.START_TAG, ns, "data");
        while (parser.next() != XmlPullParser.END_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if(name.equals("kural") && xmldoc == KURAL){
                Log.v(LOG_TAG, ""+this.getClass().getName()+" | kural tag identified");
                list.add(readKuralEntry(parser));
                Log.v(LOG_TAG, ""+this.getClass().getName()+" | readData() | item added to list");
            }
            else if(name.equals("adhikaram") && xmldoc == ADHIKARAM){
                Log.v(LOG_TAG, ""+this.getClass().getName()+" | adhikaramn tag identified");
                clist.add(readChapterEntry(parser));
                Log.v(LOG_TAG, ""+this.getClass().getName()+" | readData() | item added to list");
            }
            else {
                Log.v(LOG_TAG, ""+this.getClass().getName()+" | tag skipping "+parser.getName());
                skip(parser);
            }
        }

        return list;

    }

    private KuralEntry readKuralEntry(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, ns, "kural");
        String kural;
        int verse_index;
        int chapter_index;

        Log.v(LOG_TAG, ""+this.getClass().getName()+" | readKuralEntry() entered");

        chapter_index = Integer.parseInt(parser.getAttributeValue(ns, "chapter"));
        Log.v(LOG_TAG, ""+this.getClass().getName()+" | chapter_index = "+chapter_index);
        verse_index = Integer.parseInt(parser.getAttributeValue(ns, "index"));
        Log.v(LOG_TAG, ""+this.getClass().getName()+" | verse_index = "+verse_index);
        kural = readText(parser);
        Log.v(LOG_TAG, ""+this.getClass().getName()+" | kural = "+kural);

        //parser.nextTag();


        return new KuralEntry(kural, chapter_index, verse_index, false);
    }

    private ChapterEntry readChapterEntry(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, ns, "adhikaram");
        String chapter;
        int chapter_index;
        int pal_index;

        Log.v(LOG_TAG, ""+this.getClass().getName()+" | readChapterEntry() Entered");

        chapter_index = Integer.parseInt(parser.getAttributeValue(ns, "index"));
        Log.v(LOG_TAG, ""+this.getClass().getName()+" | chapter_index = "+chapter_index);
        pal_index = Integer.parseInt(parser.getAttributeValue(ns, "pal"));
        Log.v(LOG_TAG, ""+this.getClass().getName()+" | pal_index = "+pal_index);
        chapter = readText(parser);
        Log.v(LOG_TAG, ""+this.getClass().getName()+" | chapter_title = "+chapter);

        //parser.nextTag();


        return new ChapterEntry(chapter_index, pal_index, chapter);
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }



    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException{
        if(parser.getEventType() != XmlPullParser.START_TAG){
            throw new IllegalStateException();
        }
        int depth = 1;

        while (depth !=0){
            switch (parser.next()){
                case XmlPullParser.END_TAG:
                    depth--;
                    break;

                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
