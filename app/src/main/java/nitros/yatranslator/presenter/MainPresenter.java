package nitros.yatranslator.presenter;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import nitros.yatranslator.App;
import nitros.yatranslator.view.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        App.getComponent().inject(this);
    }

    public  void goToHistory() {

    }

    public  void goToTranslate() {
    }
}
