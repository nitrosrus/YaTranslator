package nitros.yatranslator.view;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface TranslateView extends MvpView {

    void init();

    void initializeData();

    void clearText();

    void setTranslateText(String translation);

    void setNewDataSpinner(List lang);

    void updateAdapter();

    void errorMassage(String text);
}
