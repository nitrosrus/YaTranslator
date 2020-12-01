package nitros.yatranslator.view;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface TranslateView extends MvpView {
    void init();

    void clearText();

    void getText();



    void setTranslateText(String input);
}
