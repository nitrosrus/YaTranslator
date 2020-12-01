package nitros.yatranslator;

import android.app.Application;

import nitros.yatranslator.di.AppComponent;
import nitros.yatranslator.di.AppModule;
import nitros.yatranslator.di.DaggerAppComponent;


public class App extends Application {


    private static App instance = null;

//    public static App getInstance() {
//        return instance = instance == null ? new App() : instance;
//    }

    private static AppComponent component;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        //component = DaggerAppComponent.builder().build();
    }

    public static AppComponent getComponent() {
        return component;
    }
}
