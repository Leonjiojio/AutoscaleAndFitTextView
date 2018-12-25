package com.leonjojo.autoscaleandfittextviewdemo;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by liqiang on 2018/3/30.
 */

public class ScriptAdapter extends WowAdapter<ScriptItem>{

    public ScriptAdapter(Context context) {
        super(context);
    }
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        final ScriptItem i= getItem(position);
        ViewHolder vh;
        if (v==null){
             v=getInflater().inflate(R.layout.adapter_scriptwords,null);
            vh=new ViewHolder();
            vh.tvScript= v.findViewById(R.id.diy_adpater_scriptwords);
            v.setTag(vh);
        }else{
            vh= (ViewHolder) v.getTag();
        }
        if (!TextUtils.isEmpty(i.getWords())){
            if (i.isSelcet){
                vh.tvScript.setTextColor(Color.BLUE);
            }else{
                vh.tvScript.setTextColor(Color.BLACK);
            }
            vh.tvScript.setText(i.getWords().trim());
        }
        return v;
    }

    class ViewHolder{
         TextView tvScript;
    }
}
