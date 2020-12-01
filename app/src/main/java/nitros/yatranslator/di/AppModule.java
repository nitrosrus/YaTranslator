package nitros.yatranslator.di;

import dagger.Module;
import dagger.Provides;
import nitros.yatranslator.App;


@Module
public class AppModule {
    public AppModule(App app) {
        this.app = app;
    }

    App app;

    @Provides
    public App getApp() {
        return app;
    }

}
