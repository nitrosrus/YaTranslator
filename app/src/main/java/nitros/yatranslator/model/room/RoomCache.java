package nitros.yatranslator.model.room;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import nitros.yatranslator.model.room.data.CachedTranslate;
import nitros.yatranslator.model.room.data.Database;

public class RoomCache implements IRoomCache {
    Database database;

    public RoomCache(Database database) {
        this.database = database;
    }

    @Override
    public Single<List<CachedTranslate>> getAllTranslate() {
        return  database.dao().getAll();



    }

    @Override
    public Completable putNewTranslate(CachedTranslate cachedTranslate) {
        return Completable.fromAction(() -> {
            database.dao().insert(cachedTranslate);
        }).subscribeOn(Schedulers.io());


    }


}
