package nitros.yatranslator.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Translate {
    @SerializedName("translations")
    @Expose
    private List<Translation> translations = null;

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }
}
