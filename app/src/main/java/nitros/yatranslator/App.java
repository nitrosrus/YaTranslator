package nitros.yatranslator;

import android.app.Application;

import nitros.yatranslator.di.AppComponent;
import nitros.yatranslator.di.AppModule;
import nitros.yatranslator.di.DaggerAppComponent;
import timber.log.Timber;


public class App extends Application {

    private static App instance = null;

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Timber.plant(new Timber.DebugTree());
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static AppComponent getComponent()
    {
        return component;
    }

}
