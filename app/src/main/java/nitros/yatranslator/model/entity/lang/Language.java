package nitros.yatranslator.model.entity.lang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Language {

    @SerializedName("languages")
    @Expose
    private List<LanguageDescription> languages = null;

    public List<LanguageDescription> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LanguageDescription> languages) {
        this.languages = languages;
    }
}
