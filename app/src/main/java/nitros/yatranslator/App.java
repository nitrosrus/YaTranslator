package nitros.yatranslator;

import android.app.Application;

import nitros.yatranslator.di.AppComponent;
import nitros.yatranslator.di.DaggerAppComponent;

public class App extends Application {

    private static AppComponent component;




    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder().build();
    }

    public static   AppComponent getComponent() {
        return component;
    }
}
