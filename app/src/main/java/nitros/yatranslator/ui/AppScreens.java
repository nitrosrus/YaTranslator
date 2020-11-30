package nitros.yatranslator.ui;

import androidx.fragment.app.Fragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class AppScreens extends SupportAppScreen {


    public static class TranslateScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            return TranslateFragment.getInstance();
        }

    }

    public static class HistoryScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return HistoryFragment.getInstance();
        }
    }


}
