package nitros.yatranslator.model.room.data;

import androidx.room.*;

import java.util.List;


@Dao
public interface CachedTranslateDao {


    @Query("SELECT * FROM CachedTranslate ")
    List<CachedTranslate> getAll();

    @Insert
    void insert(CachedTranslate item);

    @Delete
    void delete(CachedTranslate item);
}
