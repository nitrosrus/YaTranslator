package nitros.yatranslator.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Language {

    @SerializedName("languages")
    @Expose
    private List<LanguageDes> languages = null;

    public List<LanguageDes> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LanguageDes> languages) {
        this.languages = languages;
    }
}
