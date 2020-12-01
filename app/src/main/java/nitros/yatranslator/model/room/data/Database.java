package nitros.yatranslator.model.room.data;


import androidx.room.RoomDatabase;

@androidx.room.Database(
        entities = {
                CachedTranslate.class
        },
        version = 1,exportSchema = false)

public abstract class Database extends RoomDatabase {

    public abstract CachedTranslateDao dao();


}
