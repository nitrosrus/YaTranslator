package nitros.yatranslator.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import nitros.yatranslator.App;
import nitros.yatranslator.R;
import nitros.yatranslator.presenter.TranslatePresenter;
import nitros.yatranslator.view.TranslateView;


public class TranslateFragment extends MvpAppCompatFragment implements TranslateView {

    public static TranslateFragment getInstance() {
        return instance = instance == null ? new TranslateFragment() : instance;
    }

    private static TranslateFragment instance = null;
    private View rootView;
    private ImageButton btnTranslate;
    private ImageButton btnClear;
    private Spinner spinnerTo;
    private TextInputEditText inputText;
    private TextInputEditText outputText;
    private ArrayAdapter<String> adapterTo;
    private List<String> data = new ArrayList<>();
    @InjectPresenter
    TranslatePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_translate, container, false);
        adapterTo = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, data);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        App.getComponent().inject(this);
    }

    @ProvidePresenter
    TranslatePresenter provide() {
        return new TranslatePresenter(AndroidSchedulers.mainThread());
    }


    @Override
    public void init() {
        btnTranslate = rootView.findViewById(R.id.ib_translate);
        btnTranslate.setOnClickListener(v -> presenter.btnTranslateClick());
        btnClear = rootView.findViewById(R.id.ib_clear);
        btnClear.setOnClickListener(v -> clearText());
        spinnerTo = rootView.findViewById(R.id.spinner_to);
        spinnerTo.setAdapter(adapterTo);
        inputText = rootView.findViewById(R.id.et_input);
        outputText = rootView.findViewById(R.id.et_output);
    }

    @Override
    public void onResume() {
        super.onResume();
        clearText();
    }

    @Override
    public void initializeData() {
        presenter.refreshData(spinnerTo.getSelectedItem().toString(), inputText.getText().toString());
    }

    @Override
    public void clearText() {
        inputText.setText("");
        outputText.setText("");
    }

    @Override
    public void setTranslateText(String translation) {
        presenter.putTranslationDatabase();
        outputText.setText(translation);
    }

    @Override
    public void setNewDataSpinner(List directionOnTranslation) {
        data.clear();
        data.addAll(directionOnTranslation);
    }

    @Override
    public void updateAdapter() {
        adapterTo.notifyDataSetChanged();
    }

    @Override
    public void errorMassage(String text) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show();

    }
}