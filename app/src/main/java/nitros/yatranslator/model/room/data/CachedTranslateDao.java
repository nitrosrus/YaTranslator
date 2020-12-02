package nitros.yatranslator.model.room.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;


@Dao
public interface CachedTranslateDao {


    @Query("SELECT * FROM CachedTranslate ")
   Single <List<CachedTranslate>> getAll();

    @Insert
    void insert(CachedTranslate item);
}
