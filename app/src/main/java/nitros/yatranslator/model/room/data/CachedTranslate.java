package nitros.yatranslator.model.room.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CachedTranslate {

    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public String text;
    public String translation;

}
