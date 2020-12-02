package nitros.yatranslator.model.room;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.rxjava3.core.Completable;
import nitros.yatranslator.model.room.data.CachedTranslate;

public interface IRoomCache {
    Single<List<CachedTranslate>> getAllTranslate();

    Completable putNewTranslate(CachedTranslate cachedTranslate);



}
