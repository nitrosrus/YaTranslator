package nitros.yatranslator.presenter;

import android.annotation.SuppressLint;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import nitros.yatranslator.YandexConstants;
import nitros.yatranslator.model.api.IDataYandex;
import nitros.yatranslator.model.entity.RequestLang;
import nitros.yatranslator.model.entity.lang.Language;
import nitros.yatranslator.model.entity.lang.LanguageDescription;
import nitros.yatranslator.model.entity.trans.TranslateList;
import nitros.yatranslator.model.entity.trans.Translation;
import nitros.yatranslator.model.room.IRoomCache;
import nitros.yatranslator.model.room.data.CachedTranslate;
import nitros.yatranslator.view.TranslateView;
import timber.log.Timber;


@InjectViewState
public class TranslatePresenter extends MvpPresenter<TranslateView> {

    public TranslatePresenter(Scheduler mainThread) {
        this.mainThread = mainThread;
    }

    private Scheduler mainThread;
    private TreeMap<String, String> mapLanguage = new TreeMap<>();
    private String folderId = YandexConstants.FOLBER_ID_YANDEX;
    private String fromLanguage = "";
    private String toLanguage = "";
    private String inputText = "";
    private String translateText = "";

    @Inject
    IDataYandex apiTranslator;
    @Inject
    IRoomCache database;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        App.getComponent().inject(this);
        getViewState().init();
        loadDirectionOnTranslation();
    }

    public void refreshData(String spinnerItemLanguage, String inputText) {
        this.inputText = inputText;
        toLanguage = spinnerItemLanguage;
    }

    private Map bodyCreator(String direction) {
        Map<String, String> testMap = new HashMap<>();
        String toLanguage = getLangTo();

        switch (direction) {
            case "translate":
                testMap.put("sourceLanguageCode", fromLanguage);
                testMap.put("targetLanguageCode", toLanguage);
                testMap.put("texts", inputText);
                testMap.put("folderId", folderId);
                break;
            case "detectLanguage":
                testMap.put("text", inputText);
                testMap.put("folderId", folderId);
                break;
            case "listLanguages":
                testMap.put("folderId", folderId);
                break;

        }
        return testMap;
    }

    private String getLangTo() {
        return mapLanguage.get(toLanguage);
    }

    @SuppressLint("CheckResult")
    public void loadDirectionOnTranslation() {
        List<LanguageDescription> languageList = new ArrayList<>();
        apiTranslator
                .getSupportLanguageList(bodyCreator("listLanguages"))
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread)
                .subscribe(new DisposableSingleObserver<Language>() {
                    @Override
                    public void onSuccess(@NotNull Language language) {
                        languageList.addAll(language.getLanguages());
                        languageParserToMap(languageList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        getViewState().errorMassage(e.getLocalizedMessage());
                    }
                });
    }


    private void languageParserToMap(List<LanguageDescription> list) {
        if (!checkLanguageDescription(list)) {
            mapLanguage.clear();
            for (LanguageDescription lang : list) {
                mapLanguage.put(checkLanguageName(lang), lang.getCode());
            }
            updateAdapterSpinner();
        }
    }

    private boolean checkLanguageDescription(List<LanguageDescription> list) {
        return list.isEmpty();
    }

    private String checkLanguageName(LanguageDescription lang) {
        return lang.getName() != null ? lang.getName() : lang.getCode();
    }

    private void updateAdapterSpinner() {
        getViewState().setNewDataSpinner(getLanguageFromSpinner());
        getViewState().updateAdapter();
    }

    private List getLanguageFromSpinner() {
        List listLang = new ArrayList<>();
        listLang.addAll(mapLanguage.keySet());
        return listLang;
    }

    public void btnTranslateClick() {
        getViewState().initializeData();
        detectInputLanguage();

    }

    private void detectInputLanguage() {
        if (!checkInputText()) {
            apiTranslator
                    .detectInputLanguage(bodyCreator("detectLanguage"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(mainThread)
                    .subscribe(new DisposableSingleObserver<RequestLang>() {
                        @Override
                        public void onSuccess(@NonNull RequestLang requestLang) {
                            fromLanguage = requestLang.getLanguageCode();
                            translateInputText();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Timber.e(e);
                            getViewState().errorMassage(e.getLocalizedMessage());
                        }
                    });
        } else getViewState().errorMassage("Text language not detected or empty");

    }

    private boolean checkInputText() {
        return inputText.trim().isEmpty();
    }

    private void translateInputText() {
        apiTranslator.translate(bodyCreator("translate"))
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread)
                .subscribe(new DisposableSingleObserver<TranslateList>() {
                    @Override
                    public void onSuccess(@NonNull TranslateList translation) {
                        convertingTranslateFromServer(translation.getTranslations());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.e(e);
                        getViewState().errorMassage(e.getLocalizedMessage());
                    }
                });
    }


    private void convertingTranslateFromServer(List<Translation> translation) {
        StringBuilder sb = new StringBuilder();
        for (Translation trans : translation) {
            sb.append(trans.getText());
        }
        setTranslateText(sb.toString());
    }

    private void setTranslateText(String translate) {
        translateText = translate;
        getViewState().setTranslateText(translateText);

    }


    public void putTranslationDatabase() {
        database.putNewTranslate(createDatabaseEntity()).subscribeOn(Schedulers.io()).observeOn(mainThread)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getViewState().errorMassage(e.getLocalizedMessage());
                        Timber.e(e);
                    }
                });
    }

    private CachedTranslate createDatabaseEntity() {
        CachedTranslate item = new CachedTranslate();
        item.text = inputText;
        item.translation = translateText;
        return item;
    }


}
