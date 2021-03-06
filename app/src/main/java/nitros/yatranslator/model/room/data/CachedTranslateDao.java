package nitros.yatranslator.model.room.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;


@Dao
public interface CachedTranslateDao {


    @Query("SELECT * FROM CachedTranslate ")
    Single<List<CachedTranslate>> getAll();

    @Insert
    void insert(CachedTranslate item);

    @Query("DELETE FROM CachedTranslate WHERE id = :id ")
    void deleteById(Integer id);


}
