package nitros.yatranslator.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import nitros.yatranslator.App;
import nitros.yatranslator.R;
import nitros.yatranslator.entity.Language;
import nitros.yatranslator.entity.LanguageDes;
import nitros.yatranslator.model.api.IDataYandex;
import nitros.yatranslator.presenter.TranslatePresenter;
import nitros.yatranslator.view.TranslateView;


public class TranslateFragment extends MvpAppCompatFragment implements TranslateView {

    private static TranslateFragment instance = null;
    private View rootView;
    private ImageButton btnTranslate;
    private ImageButton btnClear;
    private Spinner spinnerTo;
    private TextInputEditText inputText;
    private TextInputEditText outputText;
    private Language langBase;
    ArrayAdapter<String> adapterTo;
    List<String> data = new ArrayList<>();


    public static TranslateFragment getInstance() {
        return instance = instance == null ? new TranslateFragment() : instance;
    }

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

    @Inject
    IDataYandex translator;

    @Override
    public void init() {
        btnTranslate = rootView.findViewById(R.id.ib_translate);
        btnTranslate.setOnClickListener(v -> btnTranslateClick());
        btnClear = rootView.findViewById(R.id.ib_clear);
        btnClear.setOnClickListener(v -> clearText());
        spinnerTo = rootView.findViewById(R.id.spinner_to);
        spinnerTo.setAdapter(adapterTo);
        inputText = rootView.findViewById(R.id.et_input);
        outputText = rootView.findViewById(R.id.et_output);

        presenter.languageList.observe(this, test -> {
            updateTranslateSelector();
        });

        presenter.loadLang();
    }

    @Override
    public void clearText() {
        inputText.setText("");
        outputText.setText("");
        for (LanguageDes lang : langBase.getLanguages()) {
            if (lang.getName() != null) data.add(lang.getName());
        }

    }

    @Override
    public void getText() {


    }

    @Override
    public void setTranslateText(String input, String output) {
        outputText.setText(input);
        inputText.setText(output);

    }


    private void btnTranslateClick() {
        presenter.initAndTranslate(
                spinnerTo.getSelectedItem().toString(),
                inputText.getText().toString()
        );

    }

    public void updateTranslateSelector() {
        data.clear();
        data.addAll(presenter.getLangList().keySet());
        adapterTo.notifyDataSetChanged();

    }
}