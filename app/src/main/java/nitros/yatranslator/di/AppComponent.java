package nitros.yatranslator.di;

import javax.inject.Singleton;

import dagger.Component;
import nitros.yatranslator.ui.HistoryFragment;
import nitros.yatranslator.ui.MainActivity;
import nitros.yatranslator.ui.TranslateFragment;
import nitros.yatranslator.presenter.HistoryPresenter;
import nitros.yatranslator.presenter.MainPresenter;
import nitros.yatranslator.presenter.TranslatePresenter;

@Singleton
@Component(modules = {
        ApiModule.class,
        CiceroneModule.class
})

public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(MainPresenter mainPresenter);

    void inject(TranslateFragment translateFragment);

    void inject(TranslatePresenter translatePresenter);

    void inject(HistoryFragment historyFragment);

    void inject(HistoryPresenter historyPresenter);


}
