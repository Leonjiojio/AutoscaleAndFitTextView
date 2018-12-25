package com.leonjojo.autoscaleandfittextviewdemo;

import java.io.Serializable;

/**
 * Created by liqiang on 2018/3/30.
 */

public class ScriptItem implements Serializable {
    public static final long serialVersionUID = 1000202056;
    public String words;
    public boolean isSelcet;

    public void setSelcet(boolean selcet) {
        isSelcet = selcet;
    }
    public boolean isSelcet() {
        return isSelcet;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getWords() {
        return words;
    }

    @Override
    public String toString() {
        return words;
    }
}
