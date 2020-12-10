package nitros.yatranslator.model.room;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import nitros.yatranslator.model.room.data.CachedTranslate;
import nitros.yatranslator.model.room.data.Database;

public class RoomCache implements IRoomCache {
    Database database;

    public RoomCache(Database database) {
        this.database = database;
    }

    @Override
    public Single<List<CachedTranslate>> getAllTranslate() {
        return database.dao().getAll();

    }

    @Override
    public Completable putNewTranslate(CachedTranslate cachedTranslate) {
        return Completable.fromAction(() ->
                database.dao().insert(cachedTranslate)).subscribeOn(Schedulers.io());


    }

    @Override
    public Completable deleteById(Integer id) {
        return Completable.fromAction(() ->
                database.dao().deleteById(id)).subscribeOn(Schedulers.io());
    }


}
