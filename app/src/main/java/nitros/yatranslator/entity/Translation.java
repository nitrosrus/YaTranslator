package nitros.yatranslator.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Translation {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("detectedLanguageCode")
    @Expose
    private String detectedLanguageCode;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDetectedLanguageCode() {
        return detectedLanguageCode;
    }

    public void setDetectedLanguageCode(String detectedLanguageCode) {
        this.detectedLanguageCode = detectedLanguageCode;
    }
}
