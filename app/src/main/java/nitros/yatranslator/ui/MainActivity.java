package nitros.yatranslator.ui;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import nitros.yatranslator.App;
import nitros.yatranslator.R;
import nitros.yatranslator.presenter.MainPresenter;
import nitros.yatranslator.view.MainView;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;


public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;
    BottomNavigationView bottomNavigation;
    SupportAppNavigator navigator = new SupportAppNavigator(this, R.id.container);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.translate_item:
                    router.navigateTo(new AppScreens.TranslateScreen());
                    return true;
                case R.id.history_item:
                    router.navigateTo(new AppScreens.HistoryScreen());
                    return true;
            }
            return true;
        });

    }

    @Inject
    NavigatorHolder navigatorHolder;
    @Inject
    Router router;


    @ProvidePresenter
    MainPresenter provide() {
        return new MainPresenter();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
           router.navigateTo(new AppScreens.TranslateScreen());

    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }
}