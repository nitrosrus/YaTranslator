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

public class HistoryRVAdapter extends RecyclerView.Adapter<HistoryRVAdapter.ViewHolder> {
    HistoryPresenter.HistoryListPresenter presenter;
    OnItemClickListener listener;

    public HistoryRVAdapter(HistoryPresenter.HistoryListPresenter presenter) {
        this.presenter = presenter;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements TranslateItemView, View.OnClickListener {
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

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClick(view, getPos());
            }
        }

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int pos);
    }

    public void SetOnItemClickListener(final OnItemClickListener listener) {
        this.listener = listener;
    }

}
