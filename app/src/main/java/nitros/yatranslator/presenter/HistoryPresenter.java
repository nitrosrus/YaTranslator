package nitros.yatranslator.presenter;

import java.util.ArrayList;
import java.util.Collections;
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
    public HistoryPresenter(Scheduler mainThread) {
        this.mainThread = mainThread;
    }

    private List<TranslationCachedText> translateList = new ArrayList<>();
    private Scheduler mainThread;
    @Inject
    IRoomCache dataBase;
    public HistoryListPresenter listPresenter = new HistoryListPresenter(this);


    public static class HistoryListPresenter implements IHistoryListPresenter {
        private List<TranslationCachedText> list = new ArrayList<>();

        public HistoryListPresenter(HistoryPresenter historyPresenter) {
            this.historyPresenter = historyPresenter;
        }

        HistoryPresenter historyPresenter;

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
            return list.get(view.getPos());

        }

        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(list, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(list, i, i - 1);
                }
            }
            return true;
        }

        @Override
        public void onItemDismiss(int position) {
            historyPresenter.deleteRoomItemById((list.get(position)).getId());
            list.remove(position);
        }


    }


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        App.getComponent().inject(this);
        getViewState().init();
    }


    public void loadAllDataBaseTranslationText() {

        dataBase.getAllTranslate().subscribeOn(Schedulers.io()).observeOn(mainThread).subscribe(new DisposableSingleObserver<List<CachedTranslate>>() {
            @Override
            public void onSuccess(@NonNull List<CachedTranslate> cachedTranslates) {
                converterDataBaseTranslationText(cachedTranslates);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.e(e);
            }
        });
    }

    private void converterDataBaseTranslationText(List<CachedTranslate> inputDataBase) {
        translateList.clear();
        for (CachedTranslate item : inputDataBase) {
            translateList.add(new TranslationCachedText(item.id, item.text, item.translation));
        }
        setDataBase();
    }


    private void setDataBase() {
        listPresenter.list.clear();
        listPresenter.list.addAll(translateList);
        updateData();
    }

    private void updateData() {
        getViewState().update();
    }

    public void deleteRoomItemById(int id) {
        dataBase.deleteById(id).subscribeOn(Schedulers.io()).observeOn(mainThread).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.e(e);
            }
        });
    }
}
