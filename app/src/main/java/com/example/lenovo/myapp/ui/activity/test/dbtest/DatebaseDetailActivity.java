package com.example.lenovo.myapp.ui.activity.test.dbtest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cxb.tools.utils.StringCheck;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.db.PokemonDBHelper;
import com.example.lenovo.myapp.model.PokemonBean;
import com.example.lenovo.myapp.ui.base.BaseActivity;

import java.util.List;

/**
 * 数据库操作
 */

public class DatebaseDetailActivity extends BaseActivity {

    public static final String POKEMON_ID = "pokemon_id";

    private EditText etName;
    private Button btnUpdate;
    private Button btnDelete;

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
        String pmId = getIntent().getStringExtra(POKEMON_ID);

        if (!StringCheck.isEmpty(pmId)) {
            List<PokemonBean> temp = PokemonDBHelper.selectPokemonById(this, pmId);
            if (temp != null && temp.size() > 0) {
                pokemon = temp.get(0);
                etName.setText(pokemon.getName());
                btnUpdate.setOnClickListener(click);
                btnDelete.setOnClickListener(click);
            } else {
                ToastUtil.toast("没有该Pokemon的信息");
                finish();
            }
        } else {
            ToastUtil.toast("没有该Pokemon的信息");
            finish();
        }
    }

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_update:
                    String name = etName.getText().toString();
                    if (!StringCheck.isEmpty(name)) {
                        pokemon.setName(name);
                        PokemonDBHelper.updatePokemon(DatebaseDetailActivity.this, pokemon);
                        ToastUtil.toast("更新成功");
                        setResult(RESULT_OK);
                    } else {
                        ToastUtil.toast("名字不能为空");
                    }
                    break;
                case R.id.btn_delete:
                    PokemonDBHelper.deletePokemon(DatebaseDetailActivity.this, pokemon);
                    ToastUtil.toast("删除成功");
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    };

}
