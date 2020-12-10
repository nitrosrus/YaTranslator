package nitros.yatranslator.model.api;

import java.util.Map;

import io.reactivex.Single;
import nitros.yatranslator.YandexConstants;
import nitros.yatranslator.model.entity.RequestLang;
import nitros.yatranslator.model.entity.lang.Language;
import nitros.yatranslator.model.entity.trans.TranslateList;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface IDataYandex {

    String HEADERS = "Authorization: Bearer " + YandexConstants.AUTH_TOKEN_YANDEX;

    @POST("/translate/v2/languages")
    @Headers(HEADERS)
    Single<Language> getSupportLanguageList(@Body Map<String, String> text);

    @POST("/translate/v2/detect")
    @Headers(HEADERS)
    Single<RequestLang> detectInputLanguage(@Body Map<String, String> text);

    @POST("/translate/v2/translate")
    @Headers(HEADERS)
    Single<TranslateList> translate(@Body Map<String, String> text);
}
