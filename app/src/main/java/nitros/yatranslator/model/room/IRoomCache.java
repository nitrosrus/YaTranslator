package nitros.yatranslator.model.room;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

import nitros.yatranslator.model.room.data.CachedTranslate;

public interface IRoomCache {

    Single<List<CachedTranslate>> getAllTranslate();

    Completable putNewTranslate(CachedTranslate cachedTranslate);

    Completable deleteById(Integer id);




}
