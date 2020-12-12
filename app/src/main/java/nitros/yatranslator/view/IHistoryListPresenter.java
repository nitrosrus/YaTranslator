package nitros.yatranslator.view;

import nitros.yatranslator.model.entity.trans.TranslationCachedText;

public interface IHistoryListPresenter {

    int getCount();

    void bindView(TranslateItemView view);

    TranslationCachedText getItem(TranslateItemView view);

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
