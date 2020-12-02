package nitros.yatranslator.presenter;


import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import nitros.yatranslator.App;
import nitros.yatranslator.model.api.IDataYandex;
import nitros.yatranslator.model.entity.RequestLang;
import nitros.yatranslator.model.entity.lang.Language;
import nitros.yatranslator.model.entity.lang.LanguageDes;
import nitros.yatranslator.model.entity.trans.TranslateList;
import nitros.yatranslator.model.entity.trans.Translation;
import nitros.yatranslator.model.room.IRoomCache;
import nitros.yatranslator.model.room.data.CachedTranslate;
import nitros.yatranslator.view.TranslateView;


@InjectViewState
public class TranslatePresenter extends MvpPresenter<TranslateView> {


    public TranslatePresenter(Scheduler mainThread) {
        this.mainThread = mainThread;
    }

    Scheduler mainThread;

    public MutableLiveData<List<LanguageDes>> languageList = new MutableLiveData<>();

    TreeMap<String, String> mapLanguage = new TreeMap<>();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        App.getComponent().inject(this);
        getViewState().init();

    }

    @Inject
    IDataYandex translator;

    @Inject
    IRoomCache database;


    private Map bodyCreator(String direction, String text, String from, String to) {
        Map<String, String> testMap = new HashMap<>();

        switch (direction) {
            case "translate":
                testMap.put("sourceLanguageCode", from);
                testMap.put("targetLanguageCode", to);
                //testMap.put("format", text);
                testMap.put("texts", text);
                testMap.put("folderId", "b1gf1b6jrptsu34pb50m");
                break;
            case "detectLanguage":
                testMap.put("text", text);
                testMap.put("folderId", "b1gf1b6jrptsu34pb50m");
                break;
            case "listLanguages":
                testMap.put("folderId", "b1gf1b6jrptsu34pb50m");
                break;

        }
        return testMap;
    }

    public void loadLang() {
        translator.langList(bodyCreator("listLanguages", null, null, null))
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread)
                .subscribe(new DisposableSingleObserver<Language>() {
                    @Override
                    public void onSuccess(@NotNull Language language) {
                        languageList.postValue(language.getLanguages());
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    public TreeMap<String, String> getLangList() {
        mapLanguage.clear();
        if (languageList.getValue() != null) {
            for (LanguageDes lang : languageList.getValue()) {
                mapLanguage.put(lang.getName() != null ? lang.getName() : lang.getCode(), lang.getCode());
            }
        }
        return mapLanguage;
    }

    public void initAndTranslate(String to, String text) {
        if (!text.trim().isEmpty()) {

            translator.detect(bodyCreator("detectLanguage", text, null, null))
                    .subscribeOn(Schedulers.io())
                    .observeOn(mainThread)
                    .subscribe(new DisposableSingleObserver<RequestLang>() {
                        @Override
                        public void onSuccess(@NonNull RequestLang requestLang) {
                            translate(to, text, requestLang.getLanguageCode());
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });

        }

    }

    public void translate(String to, String text, String from) {

        translator.translate(bodyCreator("translate", text, from, getLangTo(to)))
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread)
                .subscribe(new DisposableSingleObserver<TranslateList>() {
                    @Override
                    public void onSuccess(@NonNull TranslateList translation) {
                        setTranslate(translation.getTranslations());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {


                    }
                });
    }

    private void setTranslate(List<Translation> translation) {
        StringBuilder sb = new StringBuilder();
        for (Translation trans : translation) {
            sb.append(trans.getText());
        }
        getViewState().setTranslateText(sb.toString());

    }

    private String getLangTo(String to) {
        String text = mapLanguage.get(to);
        return text;
    }

    public void putTranslationDatabase(String text, String translation) {
        CachedTranslate item = new CachedTranslate();
        item.text = text;
        item.translation = translation;
        database.putNewTranslate(item).subscribeOn(Schedulers.io()).observeOn(mainThread).subscribe();
    }

}
