package nitros.yatranslator.model.room;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import nitros.yatranslator.model.entity.trans.TranslationCachedText;
import nitros.yatranslator.model.room.data.CachedTranslate;
import nitros.yatranslator.model.room.data.Database;

public class RoomCache implements IRoomCache {
    Database database;

    public RoomCache(Database database) {
        this.database = database;
    }

    @Override
    public Single<List<TranslationCachedText>> getAllTranslate() {
        return Single.fromCallable(() -> {
            List<TranslationCachedText> translate = null;
            List<CachedTranslate> list = database.dao().getAll();
            translate.clear();
            for (CachedTranslate item : list) {

                translate.add(new TranslationCachedText( item.text, item.translation));
            }

            return translate;
        }).subscribeOn(Schedulers.io());


    }

    @Override
    public Completable putNewTranslate(CachedTranslate cachedTranslate) {
        return Completable.fromAction(() -> {
            database.dao().insert(cachedTranslate);
        }).subscribeOn(Schedulers.io());


    }

    @Override
    public void deleteTranslate(CachedTranslate cachedTranslate) {

        database.dao().delete(cachedTranslate);
    }
}
