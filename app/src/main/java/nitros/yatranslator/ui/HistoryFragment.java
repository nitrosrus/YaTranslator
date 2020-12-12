package nitros.yatranslator.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import nitros.yatranslator.App;
import nitros.yatranslator.R;
import nitros.yatranslator.presenter.HistoryPresenter;
import nitros.yatranslator.ui.adapter.HistoryRVAdapter;
import nitros.yatranslator.ui.adapter.SimpleItemTouchHelperCallback;
import nitros.yatranslator.view.HistoryView;


public class HistoryFragment extends MvpAppCompatFragment implements HistoryView {

    private static HistoryFragment instance = null;

    public static HistoryFragment getInstance() {

        return instance = instance == null ? new HistoryFragment() : instance;

    }

    @InjectPresenter
    HistoryPresenter presenter;
    RecyclerView recyclerView;
    HistoryRVAdapter adapter;
    ItemTouchHelper.Callback callback;
    ItemTouchHelper touchHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        App.getComponent().inject(this);
        recyclerView = view.findViewById(R.id.rv_history_list);
    }

    @ProvidePresenter
    HistoryPresenter provide() {
        return new HistoryPresenter(AndroidSchedulers.mainThread());
    }

    @Override
    public void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new HistoryRVAdapter(presenter.listPresenter);
        recyclerView.setAdapter(adapter);
        callback = new SimpleItemTouchHelperCallback(adapter);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.loadAllDataBaseTranslationText();
    }

    @Override
    public void update() {
        adapter.notifyDataSetChanged();
    }
}