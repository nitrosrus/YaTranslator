package nitros.yatranslator.model.entity.trans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TranslationCachedText {

    public TranslationCachedText(Integer id, String text, String translation) {
        this.id = id;
        this.text = text;
        this.translation = translation;
    }

    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @SerializedName("text")
    @Expose
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @SerializedName("translation")
    @Expose
    private String translation;

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }


}
