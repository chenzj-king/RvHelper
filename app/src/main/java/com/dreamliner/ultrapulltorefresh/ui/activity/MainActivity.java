package com.dreamliner.ultrapulltorefresh.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dreamliner.ptrlib.PtrClassicFrameLayout;
import com.dreamliner.ptrlib.PtrDefaultHandler;
import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.ptrlib.PtrHandler;
import com.dreamliner.ultrapulltorefresh.R;

import java.util.ArrayList;

/**
 * @author chenzj
 * @Title: MainActivity
 * @Description: 类的描述 -
 * @date 2016/6/12 17:06
 * @email admin@chenzhongjin.cn
 */
public class MainActivity extends AppCompatActivity {

    private PtrClassicFrameLayout mPtrFrame;
    private ListView listView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.rotate_header_list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    Toast.makeText(MainActivity.this, "click " + (position + 1) + " item", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mAdapter = new ArrayAdapter<>(this, R.layout.item_text, R.id.item_text_textview);
        listView.setAdapter(mAdapter);

        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
        if (mPtrFrame != null) {
            mPtrFrame.setLastUpdateTimeRelateObject(this);
            mPtrFrame.setPtrHandler(new PtrHandler() {
                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    updateData();
                }

                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }
            });
            // the following are default settings
            mPtrFrame.setResistance(1.7f);
            mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
            mPtrFrame.setDurationToClose(200);
            mPtrFrame.setDurationToCloseHeader(1000);
            // default is false
            mPtrFrame.setPullToRefresh(false);
            // default is true
            mPtrFrame.setKeepHeaderWhenRefresh(true);
            mPtrFrame.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPtrFrame.autoRefresh();
                }
            }, 100);
        }
    }

    protected void updateData() {

        mAdapter.clear();
        ArrayList<String> strings = new ArrayList<>();
        int size = (int) (Math.random() * 50);
        size = size < 15 ? 15 : size;
        for (int i = 0; i < size; i++) {
            strings.add("测试数据 " + (i + 1));
        }
        mPtrFrame.refreshComplete();
        mAdapter.addAll(strings);
    }
}

