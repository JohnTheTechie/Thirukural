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
        ArrayList list;
        if(xmldoc == KURAL) list = new ArrayList<KuralEntry>();
        else list = new ArrayList<ChapterEntry>();

        parser.require(XmlPullParser.START_TAG, ns, "data");
        while (parser.next() != XmlPullParser.END_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if(name.equals("kural") && xmldoc == KURAL){
                list.add(readKuralEntry(parser));
            }
            else if(name.equals("adhikaram") && xmldoc == ADHIKARAM){
                list.add(readChapterEntry(parser));
            }
            else {
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

        chapter_index = Integer.parseInt(parser.getAttributeValue(ns, "chapter"));
        verse_index = Integer.parseInt(parser.getAttributeValue(ns, "index"));
        kural = parser.getText();

        parser.nextTag();


        return new KuralEntry(kural, chapter_index, verse_index, false);
    }

    private ChapterEntry readChapterEntry(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, ns, "kural");
        String chapter;
        int chapter_index;
        int pal_index;

        chapter_index = Integer.parseInt(parser.getAttributeValue(ns, "index"));
        pal_index = Integer.parseInt(parser.getAttributeValue(ns, "pal"));
        chapter = parser.getText();

        parser.nextTag();


        return new ChapterEntry(chapter_index, pal_index, chapter);
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
