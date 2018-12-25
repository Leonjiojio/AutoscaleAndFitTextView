package com.leonjojo.autoscaleandfittextviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.leonjojo.autoscaleandfittextviewdemo.Utils.ViewUtil;
import com.leonjojo.autoscaleandfittextviewdemo.view.DragScaleView;

import java.util.ArrayList;
import java.util.List;


public class AutoScaleFixActivity extends AppCompatActivity implements   View.OnClickListener {
    public static final String TAG = AutoScaleFixActivity.class.getSimpleName();


    //view
    private ImageView ivTempGif;
    private EditText etScriptInput;
    private ListView lvScript;
    private ScriptAdapter scriptAdapter;
    private DragScaleView dragScaleView;


    //加载的资源相关

    private List<ScriptItem> diyScriptItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_scale_demo);
        initView();
        initListener();
        initDate();
    }

    private void initDate() {
        String[] scriptData = getResources().getStringArray(R.array.script_list);

        diyScriptItemList=new ArrayList<>();
        for (int i=0;i<scriptData.length;i++){
            ScriptItem item=new ScriptItem();
            item.words=scriptData[i];
            if (i==0){
                item.setSelcet(true);
            }
            diyScriptItemList.add(item);
        }
        scriptAdapter.setData(diyScriptItemList);
        setScriptText(diyScriptItemList.get(0).getWords());
    }

    private void initListener() {
        lvScript.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for (ScriptItem i : diyScriptItemList) {
                    i.setSelcet(false);
                }
                diyScriptItemList.get(position).setSelcet(true);
                setScriptText(diyScriptItemList.get(position).getWords());
                scriptAdapter.setData(diyScriptItemList);
            }
        });
        etScriptInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    dragScaleView.clearText();
                } else {
                    dragScaleView.setDisplayText(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }





    private void initView() {
        ivTempGif = (ImageView) findViewById(R.id.diy_temp_gif);
        etScriptInput = (EditText) findViewById(R.id.diy_script_text);
        lvScript = (ListView) findViewById(R.id.diy_lv_script);
        dragScaleView = (DragScaleView) findViewById(R.id.diy_dg_view);
        dragScaleView.setParentViewSize(ViewUtil.getViewWidth(ivTempGif), ViewUtil.getViewHeight(ivTempGif));




        scriptAdapter = new ScriptAdapter(getApplicationContext());
        lvScript.setAdapter(scriptAdapter);


    }



    private void setScriptText(String text) {
        etScriptInput.setText(text.trim());
        setgifText(text);
    }

    private void setgifText(String text) {
        dragScaleView.setDisplayText(text);
    }

    @Override
    public void onClick(View v) {

    }




    private void showToastLong(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
