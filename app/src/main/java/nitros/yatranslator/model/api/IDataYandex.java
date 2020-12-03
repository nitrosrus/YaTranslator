package nitros.yatranslator.model.api;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import nitros.yatranslator.YandexConstants;
import nitros.yatranslator.model.entity.RequestLang;
import nitros.yatranslator.model.entity.lang.Language;
import nitros.yatranslator.model.entity.trans.TranslateList;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface IDataYandex {
//Надо поменять <TOKEN> на настаящий токен

    String HEADERS = "Authorization: Bearer " + YandexConstants.AUTH_TOKEN_YANDEX;

    // /translate/v2/languages список языков
    // /translate/v2/translate перевод на указаный язык
    // /translate/v2/detect определение языка

    @POST("/translate/v2/languages")
    @Headers(HEADERS)
    Single<Language> langList(@Body Map<String, String> text);

    @POST("/translate/v2/detect")
    @Headers(HEADERS)
    Single<RequestLang> detect(@Body Map<String, String> text);

    @POST("/translate/v2/translate")
    @Headers(HEADERS)
    Single<TranslateList> translate(@Body Map<String, String> text);
}
