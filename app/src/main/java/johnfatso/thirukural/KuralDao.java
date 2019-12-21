package johnfatso.thirukural;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface KuralDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(KuralEntry... kuralEntries);

    @Query("SELECT * FROM table_kural ORDER BY verse_index ASC")
    KuralEntry[] getAllVerses();

    @Query("SELECT * FROM table_kural WHERE `chapter_index` =:chapterIndex ORDER BY verse_index ASC" )
    KuralEntry[] getVersesFromChapter(int chapterIndex);

    @Query("Select * FROM table_kural WHERE `verse_index`=:position")
    KuralEntry getVerseNumbered(int position);

    @Query("Select * FROM table_kural WHERE `favourite`=:favourites ")
    KuralEntry[] getFavouriteKurals(boolean favourites);

    @Query("UPDATE table_kural SET `favourite` = :favourite WHERE `verse_index` = :verse_index")
    void updateFavourite(boolean favourite, int verse_index);

}
