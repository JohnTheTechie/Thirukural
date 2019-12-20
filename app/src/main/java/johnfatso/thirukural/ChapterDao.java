package johnfatso.thirukural;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ChapterDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(ChapterEntry... chapterEntries);

    @Query("SELECT * FROM table_chapter WHERE `chapter_index`=:position")
    ChapterEntry getChapterName(int position);

    @Query("SELECT * FROM table_chapter ORDER BY chapter_index ASC")
    ChapterEntry[] getAllChapterList();

    @Query("SELECT * FROM table_chapter WHERE `pal_index`=:palIndex")
    ChapterEntry[] getChapterFromPal(int palIndex);

}
