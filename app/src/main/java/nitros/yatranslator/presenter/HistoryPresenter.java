package nitros.yatranslator.presenter;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import nitros.yatranslator.App;
import nitros.yatranslator.view.HistoryView;
import nitros.yatranslator.view.TranslateView;

@InjectViewState
public class HistoryPresenter extends MvpPresenter<HistoryView> {


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        App.getComponent().inject(this);
    }


}
