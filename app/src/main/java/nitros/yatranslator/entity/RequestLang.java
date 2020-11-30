package nitros.yatranslator.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestLang {

    @SerializedName("languageCode")
    @Expose
    private String languageCode;

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
