package nitros.yatranslator.view;



public interface IHistoryListPresenter {

    int getCount();

    void bindView(TranslateItemView view);

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
