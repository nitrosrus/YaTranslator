package nitros.yatranslator.di;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
public class CiceroneModule {


    @NotNull
    @Contract(pure = true)
    @Singleton
    @Provides
    public static Cicerone<Router> cicerone() {
        return Cicerone.create();
    }


    @Provides
    public static NavigatorHolder navigatorHolder(@NotNull Cicerone<Router> cicerone) {
        return cicerone.getNavigatorHolder();
    }

    @Provides
    public static Router router(@NotNull Cicerone<Router> cicerone) {
        return cicerone.getRouter();
    }

}
