package nitros.yatranslator.presenter;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import nitros.yatranslator.App;
import nitros.yatranslator.ui.AppScreens;
import nitros.yatranslator.view.MainView;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    @Inject
    Router router;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        App.getComponent().inject(this);
        getViewState().init();
    }

    public void navigateTranslateScreen() {
        router.navigateTo(new AppScreens.TranslateScreen());
    }

    public void navigateHistoryScreen() {
        router.navigateTo(new AppScreens.HistoryScreen());
    }
}
