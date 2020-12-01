package nitros.yatranslator.model.room;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import nitros.yatranslator.model.entity.trans.TranslationCachedText;
import nitros.yatranslator.model.room.data.CachedTranslate;

public interface IRoomCache {
    Single<List<TranslationCachedText>> getAllTranslate();
    Completable putNewTranslate(CachedTranslate cachedTranslate);
    void deleteTranslate(CachedTranslate cachedTranslate);

}
