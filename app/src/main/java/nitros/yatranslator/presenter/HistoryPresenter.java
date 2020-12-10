package nitros.yatranslator.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import nitros.yatranslator.App;
import nitros.yatranslator.model.entity.trans.TranslationCachedText;
import nitros.yatranslator.model.room.IRoomCache;
import nitros.yatranslator.model.room.data.CachedTranslate;
import nitros.yatranslator.view.HistoryView;
import nitros.yatranslator.view.IHistoryListPresenter;
import nitros.yatranslator.view.TranslateItemView;
import timber.log.Timber;

@InjectViewState
public class HistoryPresenter extends MvpPresenter<HistoryView> {


    public List<TranslationCachedText> translateList = new ArrayList<>();

    Scheduler mainThread;

    public HistoryPresenter(Scheduler mainThread) {
        this.mainThread = mainThread;
    }

    public static class HistoryListPresenter implements IHistoryListPresenter {
        public List<TranslationCachedText> list = new ArrayList<>();

        @Override
        public int getCount() {

            if (list != null) {
                return list.size();

            }
            return 0;
        }

        @Override
        public void bindView(TranslateItemView view) {
            view.setText(
                    list.get(view.getPos()).getText(),
                    list.get(view.getPos()).getTranslation()
            );
        }

        @Override
        public TranslationCachedText getItem(TranslateItemView view) {
            return   list.get(view.getPos());

        }


    }


    public HistoryListPresenter listPresenter = new HistoryListPresenter();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        App.getComponent().inject(this);
        getViewState().init();
    }

    @Inject
    IRoomCache dataBase;

    public void loadData() {

        dataBase.getAllTranslate().subscribeOn(Schedulers.io()).observeOn(mainThread).subscribe(new DisposableSingleObserver<List<CachedTranslate>>() {
            @Override
            public void onSuccess(@NonNull List<CachedTranslate> cachedTranslates) {
                translateList.clear();

                for (CachedTranslate item : cachedTranslates) {
                    translateList.add(new TranslationCachedText(item.id, item.text, item.translation));
                }
                setData();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.e(e);
            }
        });
    }


    public void setData() {
        listPresenter.list.clear();
        listPresenter.list.addAll(translateList);
        updateData();
    }

    void updateData() {
        getViewState().update();
    }

    public void deleteItemById(int id) {
        dataBase.deleteById(id).subscribeOn(Schedulers.io()).observeOn(mainThread).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onComplete() {
                updateData();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.e(e);
                ;
            }
        });
    }


}
