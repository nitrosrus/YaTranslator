package nitros.yatranslator.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import nitros.yatranslator.R;
import nitros.yatranslator.presenter.HistoryPresenter;
import nitros.yatranslator.view.TranslateItemView;

public class HistoryRVAdapter extends RecyclerView.Adapter<HistoryRVAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    HistoryPresenter.HistoryListPresenter presenter;


    public HistoryRVAdapter(HistoryPresenter.HistoryListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (presenter.onItemMove(fromPosition, toPosition)) {
            notifyItemMoved(fromPosition, toPosition);
        }
    }

    @Override
    public void onItemDismiss(int position) {
        presenter.onItemDismiss(position);
        notifyItemRemoved(position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_translations, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pos = position;
        presenter.bindView(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements TranslateItemView {
        Integer pos = -1;
        private TextView textFrom;
        private TextView textTo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textFrom = itemView.findViewById(R.id.tv_translate_from);
            textTo = itemView.findViewById(R.id.tv_translate_to);
        }

        @Override
        public Integer getPos() {
            return pos;
        }

        @Override
        public void setText(String text, String translate) {
            textFrom.setText(text);
            textTo.setText(translate);
        }
    }


}

interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
