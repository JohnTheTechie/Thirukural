package johnfatso.thirukural;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_kural")
public class KuralEntry {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "index")
    private int primaryKey;

    @NonNull
    @ColumnInfo(name = "kural")
    private String kural;

    @ColumnInfo(name = "chapter_index")
    private int chapterIndex;

    @ColumnInfo(name = "verse_index")
    private int verseIndex;

    @ColumnInfo(name = "favourite")
    private boolean favourite;

    public KuralEntry(@NonNull String kural, int chapterIndex, int verseIndex, boolean favourite) {

        this.kural = kural;
        this.chapterIndex = chapterIndex;
        this.verseIndex = verseIndex;
        this.favourite = favourite;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    @NonNull
    public String getKural() {
        return kural;
    }

    public void setKural(@NonNull String kural) {
        this.kural = kural;
    }

    public int getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    public int getVerseIndex() {
        return verseIndex;
    }

    public void setVerseIndex(int verseIndex) {
        this.verseIndex = verseIndex;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
