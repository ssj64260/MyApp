package com.example.lenovo.myapp.ui.activity.test.dbtest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cxb.tools.utils.FastClick;
import com.cxb.tools.utils.StringCheck;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.db.PokemonDBHelper;
import com.example.lenovo.myapp.model.PokemonBean;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.ToastMaster;

/**
 * 数据库操作
 */

public class DatebaseDetailActivity extends BaseActivity {

    public static final String POKEMON_ID = "pokemon_id";

    private EditText etName;
    private Button btnUpdate;
    private Button btnDelete;

    private PokemonDBHelper pmDBHelper;

    private PokemonBean pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datebase_detail);

        initView();
        setData();

    }

    private void initView() {
        etName = (EditText) findViewById(R.id.et_name);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnDelete = (Button) findViewById(R.id.btn_delete);
    }

    private void setData() {
        pmDBHelper = new PokemonDBHelper(DatebaseDetailActivity.this);
        String pmId = getIntent().getStringExtra(POKEMON_ID);

        if (!StringCheck.isEmpty(pmId)) {
            pokemon = pmDBHelper.getPokemonById(pmId);
            if (pokemon != null) {
                etName.setText(pokemon.getName());
                btnUpdate.setOnClickListener(click);
                btnDelete.setOnClickListener(click);
            } else {
                ToastMaster.toast(getString(R.string.toast_not_pokemon_info));
                finish();
            }
        } else {
            ToastMaster.toast(getString(R.string.toast_not_pokemon_info));
            finish();
        }
    }

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!FastClick.isFastClick()) {
                hideKeyboard();
                switch (v.getId()) {
                    case R.id.btn_update:
                        if (pokemon != null) {
                            String name = etName.getText().toString();
                            if (!StringCheck.isEmpty(name)) {
                                pokemon.setName(name);
                                pmDBHelper.update(pokemon);
                                ToastMaster.toast(getString(R.string.toast_pokemon_data_update_success));
                                setResult(RESULT_OK);
                            } else {
                                ToastMaster.toast(getString(R.string.toast_pokemon_name_not_null));
                            }
                        } else {
                            ToastMaster.toast(getString(R.string.toast_not_pokemon_info));
                        }
                        break;
                    case R.id.btn_delete:
                        if (pokemon != null) {
                            pmDBHelper.delete(pokemon.getId());
                            ToastMaster.toast(getString(R.string.toast_pokemon_data_delete_success));
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            ToastMaster.toast(getString(R.string.toast_not_pokemon_info));
                        }
                        break;
                }
            }
        }
    };

}
