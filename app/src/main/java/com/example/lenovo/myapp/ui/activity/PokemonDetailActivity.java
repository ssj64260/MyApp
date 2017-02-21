package com.example.lenovo.myapp.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.cxb.tools.newstab.NewsTabResoureUtil;
import com.cxb.tools.utils.AssetsUtil;
import com.cxb.tools.utils.DisplayUtil;
import com.cxb.tools.utils.StringCheck;
import com.cxb.tools.utils.ThreadPoolUtil;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.MyApplication;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.db.PokemonDBHelper;
import com.example.lenovo.myapp.model.CharacteristicBean;
import com.example.lenovo.myapp.model.PokemonBean;
import com.example.lenovo.myapp.model.PokemonNameBean;
import com.example.lenovo.myapp.model.PropertyBean;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 口袋妖怪详情页面
 */

public class PokemonDetailActivity extends BaseActivity {

    private ScrollView svBackground;//总背景色

    private TextView tvCnName;//中文名
    private TextView tvJpEnName;//英文日文名
    private TextView tvId;//编号

    private ImageView ivImage;//图片

    private LinearLayout llBgCharacteristic;//特性总背景色
    private LinearLayout llBgProperty;//属性总背景色
    private LinearLayout llBgEthnicValue;//种族值背景色
    private LinearLayout llBgAttributes;//各项属性背景色

    private TextView tvCharacteristic1;//特性1
    private TextView tvCharacteristic2;//特殊特性

    private TextView tvProperty1;//属性1
    private TextView tvProperty2;//属性2

    private TextView tvEthnicValue;//种族值

    private TextView tvHp;//hp
    private TextView tvAttack;//攻击
    private TextView tvDefense;//防御
    private TextView tvSattack;//特攻
    private TextView tvSdefense;//特防
    private TextView tvSpeed;//速度

    private View lineHp;//hp柱状图
    private View lineAttack;//攻击柱状图
    private View lineDefense;//防御柱状图
    private View lineSattack;//特攻柱状图
    private View lineSdefense;//特防柱状图
    private View lineSpeed;//速度柱状图

    private Button btnAddDatabase;//加入数据库

    private PokemonBean pokemon;
    private List<CharacteristicBean> cList;
    private List<PropertyBean> pList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        pokemon = (PokemonBean) getIntent().getSerializableExtra("pokemon");

        initView();
        setData();

    }

    private void initView() {
        svBackground = (ScrollView) findViewById(R.id.sv_background);

        tvCnName = (TextView) findViewById(R.id.tv_cn_name);
        tvJpEnName = (TextView) findViewById(R.id.tv_jp_en_name);
        tvId = (TextView) findViewById(R.id.tv_pm_id);
        ivImage = (ImageView) findViewById(R.id.iv_pm_img);

        llBgCharacteristic = (LinearLayout) findViewById(R.id.ll_bg_characteristic);
        llBgProperty = (LinearLayout) findViewById(R.id.ll_bg_property);
        llBgEthnicValue = (LinearLayout) findViewById(R.id.ll_bg_ethnic_value);
        llBgAttributes = (LinearLayout) findViewById(R.id.ll_bg_attributes);

        tvCharacteristic1 = (TextView) findViewById(R.id.tv_characteristic1);
        tvCharacteristic2 = (TextView) findViewById(R.id.tv_characteristic2);
        tvProperty1 = (TextView) findViewById(R.id.tv_pm_property1);
        tvProperty2 = (TextView) findViewById(R.id.tv_pm_property2);

        tvEthnicValue = (TextView) findViewById(R.id.tv_ethnic_value);

        tvHp = (TextView) findViewById(R.id.tv_hp);
        tvAttack = (TextView) findViewById(R.id.tv_attack);
        tvDefense = (TextView) findViewById(R.id.tv_defense);
        tvSattack = (TextView) findViewById(R.id.tv_s_attack);
        tvSdefense = (TextView) findViewById(R.id.tv_s_defense);
        tvSpeed = (TextView) findViewById(R.id.tv_speed);

        lineHp = findViewById(R.id.view_hp_line);
        lineAttack = findViewById(R.id.view_attack_line);
        lineDefense = findViewById(R.id.view_defense_line);
        lineSattack = findViewById(R.id.view_s_attack_line);
        lineSdefense = findViewById(R.id.view_s_defense_line);
        lineSpeed = findViewById(R.id.view_speede_line);

        btnAddDatabase = (Button) findViewById(R.id.btn_add_database);
    }

    private void setData() {
        String id = pokemon.getId();
        String mega = pokemon.getMega();
        String genuineSmallLogo = "http://www.koudai8.com/pmdex/img/pm/cg/" + id + ".png";
        final String noBackgroundLogo = "http://res.pokemon.name/sprites/core/xy/front/" + id + "." + mega + ".png";
        String url;
        if ("00".equals(mega)) {
            url = genuineSmallLogo;
        } else {
            url = noBackgroundLogo;
        }

        pList = pokemon.getProperty();
        cList = pokemon.getCharacteristic();
        String hp = pokemon.getHp();
        String attack = pokemon.getAttack();
        String defense = pokemon.getDefense();
        String sAttack = pokemon.getS_attack();
        String sDefense = pokemon.getS_defense();
        String speed = pokemon.getSpeed();
        String ethnicValue = pokemon.getEthnic_value();

        int propertyId = Integer.parseInt(pList.get(0).getId()) - 1;

        int allBgColor = NewsTabResoureUtil.property_color[propertyId];
        int textBgColorRes = NewsTabResoureUtil.perty_bg_color[propertyId];

        Gson gson = new Gson();

        if (pokemon.getPmName() == null) {
            String pmNmaeJson = AssetsUtil.getAssetsTxtByName(this, "pokemon_name.txt");
            if (!StringCheck.isEmpty(pmNmaeJson)) {
                Type type = new TypeToken<List<PokemonNameBean>>() {
                }.getType();
                List<PokemonNameBean> temp = gson.fromJson(pmNmaeJson, type);
                for (PokemonNameBean pmn : temp) {
                    if (id.equals(pmn.getId())) {
                        pokemon.setPmName(pmn);
                        break;
                    }
                }
            }
        }

        //设置背景颜色
        svBackground.setBackgroundColor(allBgColor);
        llBgCharacteristic.setBackgroundResource(textBgColorRes);
        llBgProperty.setBackgroundResource(textBgColorRes);
        llBgEthnicValue.setBackgroundResource(textBgColorRes);
        llBgAttributes.setBackgroundResource(textBgColorRes);

        //设置名称编号图片
        tvCnName.setText(pokemon.getName());
        if (pokemon.getPmName() != null) {
            tvJpEnName.setText(pokemon.getPmName().getJp_name() + " " + pokemon.getPmName().getEn_name());
        }
        tvId.setText("#" + id);
        final RequestManager requestManager = Glide.with(this);
        requestManager.load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .fitCenter()
                .placeholder(R.drawable.bg_ditto)
                .dontAnimate()
                .into(new GlideDrawableImageViewTarget(ivImage) {
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        requestManager.load(noBackgroundLogo)
                                .error(R.drawable.bg_ditto)
                                .fitCenter()
                                .into(ivImage);
                    }
                });

        //设置特性
        if (cList != null && cList.size() > 0) {
            String cJson = AssetsUtil.getAssetsTxtByName(this, "characteristic.txt");
            List<CharacteristicBean> temp = new ArrayList<>();
            if (!StringCheck.isEmpty(cJson)) {
                Type type = new TypeToken<List<CharacteristicBean>>() {
                }.getType();
                temp = gson.fromJson(cJson, type);
            }
            List<String> cNames = new ArrayList<>();
            for (CharacteristicBean c : cList) {
                for (CharacteristicBean cb : temp) {
                    if (c.getId().equals(cb.getId())) {
                        c.setJp_name(cb.getJp_name());
                        c.setEn_name(cb.getEn_name());
                        c.setDescription(cb.getDescription());
                    }
                }
                String cName = c.getName();
                if (!StringCheck.isEmpty(cName)) {
                    cNames.add(cName);
                }
            }

            if (cNames.size() >= 3) {
                tvCharacteristic1.setText(cNames.get(0) + "  或  " + cNames.get(1));
                tvCharacteristic2.setText(cNames.get(2));
            } else if (cNames.size() >= 2) {
                tvCharacteristic1.setText(cNames.get(0));
                tvCharacteristic2.setText(cNames.get(1));
            } else {
                tvCharacteristic1.setText(cNames.get(0));
                tvCharacteristic2.setText("无");
            }
            tvCharacteristic1.setOnClickListener(click);
            tvCharacteristic2.setOnClickListener(click);
        }

        //设置属性
        if (pList.size() > 0) {
            String pJson = AssetsUtil.getAssetsTxtByName(this, "property.txt");
            List<PropertyBean> temp = new ArrayList<>();
            if (!StringCheck.isEmpty(pJson)) {
                Type type = new TypeToken<List<PropertyBean>>() {
                }.getType();
                temp = gson.fromJson(pJson, type);
            }

            for (PropertyBean p : pList) {
                for (PropertyBean pb : temp) {
                    if (p.getId().equals(pb.getId())) {
                        p.setEn_name(pb.getEn_name());
                    }
                }
            }

            PropertyBean pb1 = pList.get(0);

            tvProperty1.setText(pb1.getName());
            tvProperty1.setVisibility(View.VISIBLE);
            tvProperty1.setOnClickListener(click);

            if (!StringCheck.isEmpty(pb1.getId())) {
                tvProperty1.setBackgroundResource(NewsTabResoureUtil.property_bg_color[Integer.parseInt(pb1.getId()) - 1]);
            }

            if (pList.size() > 1) {
                PropertyBean pb2 = pList.get(1);

                tvProperty2.setText(pb2.getName());
                tvProperty2.setVisibility(View.VISIBLE);
                tvProperty2.setOnClickListener(click);

                if (!StringCheck.isEmpty(pb2.getId())) {
                    tvProperty2.setBackgroundResource(NewsTabResoureUtil.property_bg_color[Integer.parseInt(pb2.getId()) - 1]);
                }
            } else {
                tvProperty2.setVisibility(View.GONE);
            }
        } else {
            tvProperty1.setVisibility(View.GONE);
        }

        //设置种族值
        tvEthnicValue.setText(ethnicValue);

        //设置各项属性
        tvHp.setText(hp);
        tvAttack.setText(attack);
        tvDefense.setText(defense);
        tvSattack.setText(sAttack);
        tvSdefense.setText(sDefense);
        tvSpeed.setText(speed);

        LinearLayout.LayoutParams hpParams = (LinearLayout.LayoutParams) lineHp.getLayoutParams();
        hpParams.width = (int) (DisplayUtil.dip2px(MyApplication.getInstance(), Float.parseFloat(hp)) * 0.8);
        LinearLayout.LayoutParams attackParams = (LinearLayout.LayoutParams) lineAttack.getLayoutParams();
        attackParams.width = (int) (DisplayUtil.dip2px(MyApplication.getInstance(), Float.parseFloat(attack)) * 0.8);
        LinearLayout.LayoutParams defenseParams = (LinearLayout.LayoutParams) lineDefense.getLayoutParams();
        defenseParams.width = (int) (DisplayUtil.dip2px(MyApplication.getInstance(), Float.parseFloat(defense)) * 0.8);
        LinearLayout.LayoutParams sAttackParams = (LinearLayout.LayoutParams) lineSattack.getLayoutParams();
        sAttackParams.width = (int) (DisplayUtil.dip2px(MyApplication.getInstance(), Float.parseFloat(sAttack)) * 0.8);
        LinearLayout.LayoutParams sDefenseParams = (LinearLayout.LayoutParams) lineSdefense.getLayoutParams();
        sDefenseParams.width = (int) (DisplayUtil.dip2px(MyApplication.getInstance(), Float.parseFloat(sDefense)) * 0.8);
        LinearLayout.LayoutParams speedParams = (LinearLayout.LayoutParams) lineSpeed.getLayoutParams();
        speedParams.width = (int) (DisplayUtil.dip2px(MyApplication.getInstance(), Float.parseFloat(speed)) * 0.8);

        lineHp.setLayoutParams(hpParams);
        lineAttack.setLayoutParams(attackParams);
        lineDefense.setLayoutParams(defenseParams);
        lineSattack.setLayoutParams(sAttackParams);
        lineSdefense.setLayoutParams(sDefenseParams);
        lineSpeed.setLayoutParams(speedParams);

        ScaleAnimation scaleX = new ScaleAnimation(0f, 1f, 1f, 1f);
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleX.setFillAfter(true);
        scaleX.setDuration(1500);

        lineHp.startAnimation(scaleX);
        lineAttack.startAnimation(scaleX);
        lineDefense.startAnimation(scaleX);
        lineSattack.startAnimation(scaleX);
        lineSdefense.startAnimation(scaleX);
        lineSpeed.startAnimation(scaleX);

        btnAddDatabase.setOnClickListener(click);
    }

    private void addPokemonToDB() {
        ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
            @Override
            public void run() {

                PokemonDBHelper.addPokemon(PokemonDetailActivity.this, pokemon);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.toast("已经成功加入数据库");
                    }
                });
            }
        });
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_add_database:
                    addPokemonToDB();
                    break;
                case R.id.tv_characteristic1:
                    ToastUtil.toast(cList.get(0).getDescription());
                    break;
                case R.id.tv_characteristic2:
                    ToastUtil.toast(cList.get(cList.size() - 1).getDescription());
                    break;
                case R.id.tv_pm_property1:
                    if (tvProperty1.getText().toString().equals(pList.get(0).getName())) {
                        tvProperty1.setText(pList.get(0).getEn_name());
                    } else {
                        tvProperty1.setText(pList.get(0).getName());
                    }
                    break;
                case R.id.tv_pm_property2:
                    if (tvProperty2.getText().toString().equals(pList.get(1).getName())) {
                        tvProperty2.setText(pList.get(1).getEn_name());
                    } else {
                        tvProperty2.setText(pList.get(1).getName());
                    }
                    break;
            }
        }
    };
}
