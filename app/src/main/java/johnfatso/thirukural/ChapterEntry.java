package johnfatso.thirukural;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_chapter")
public class ChapterEntry {

    @PrimaryKey
    @ColumnInfo(name = "chapter_index")
    private int chapterIndex;

    @ColumnInfo(name = "pal_index")
    private int palIndex;

    @NonNull
    @ColumnInfo(name = "chapter")
    private String chapter;

    public ChapterEntry(int chapterIndex, int palIndex, @NonNull String chapter) {
        this.chapterIndex = chapterIndex;
        this.chapter = chapter;
        this.palIndex = palIndex;
    }

    public int getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    @NonNull
    public String getChapter() {
        return chapter;
    }

    public void setChapter(@NonNull String chapter) {
        this.chapter = chapter;
    }

    public int getPalIndex() {
        return palIndex;
    }

    public void setPalIndex(int palIndex) {
        this.palIndex = palIndex;
    }
}
