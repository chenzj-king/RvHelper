package com.dreamliner.lib.rvhelper.sample.entity;

import android.text.TextUtils;

import com.dreamliner.lib.rvhelper.sample.view.decoration.ItemDecorationTag;

import java.io.Serializable;

/**
 * @author chenzj
 * @Title: Addressbook
 * @Description: 类的描述 -
 * @date 2016/10/12 15:27
 * @email admin@chenzhongjin.cn
 */
public class Addressbook implements Serializable, ItemDecorationTag {

    private String realname;

    private String itemDecorationtag;

    public Addressbook() {
    }

    public Addressbook(String realname) {
        this.realname = realname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getItemDecorationtag() {
        return itemDecorationtag;
    }

    public void setItemDecorationtag(String itemDecorationtag) {
        this.itemDecorationtag = itemDecorationtag;
    }

    @Override
    public String getTag() {
        return TextUtils.isEmpty(itemDecorationtag) ? "#" : itemDecorationtag;
    }

    @Override
    public String toString() {
        return "Addressbook{" +
                ", realname='" + realname + '\'' +
                ", itemDecorationtag='" + itemDecorationtag + '\'' +
                '}';
    }
}
