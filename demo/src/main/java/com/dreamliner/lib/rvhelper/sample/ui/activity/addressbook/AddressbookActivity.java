package com.dreamliner.lib.rvhelper.sample.ui.activity.addressbook;

import android.widget.TextView;

import com.dreamliner.lib.rvhelper.sample.AppContext;
import com.dreamliner.lib.rvhelper.sample.R;
import com.dreamliner.lib.rvhelper.sample.entity.Addressbook;
import com.dreamliner.lib.rvhelper.sample.ui.base.BaseActivity;
import com.dreamliner.lib.rvhelper.sample.utils.CharacterParser;
import com.dreamliner.lib.rvhelper.sample.utils.DividerUtil;
import com.dreamliner.lib.rvhelper.sample.utils.PinyinComparator;
import com.dreamliner.lib.rvhelper.sample.view.LetterView;
import com.dreamliner.lib.rvhelper.sample.view.decoration.TitleItemDecoration;
import com.dreamliner.rvhelper.OptimumRecyclerView;

import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;


/**
 * @author chenzj
 * @Title: AddressbookActivity
 * @Description: 类的描述 -
 * @date 2016/10/14 16:54
 * @email admin@chenzhongjin.cn
 */
public class AddressbookActivity extends BaseActivity implements LetterView.OnTouchingLetterChangedListener {

    @BindView(R.id.search_tv)
    TextView mSearchTv;
    @BindView(R.id.optimum_rv)
    OptimumRecyclerView mOptimumRv;
    @BindView(R.id.letter_tip_tv)
    TextView mLetterTipTv;
    @BindView(R.id.right_letter)
    LetterView mRightLetter;

    private AddressbookAdapter mAdapter;

    private List<Addressbook> mAddressbookList;
    private PinyinComparator mPyComparator;

    @Override
    protected int getLayoutId() {
        return R.layout.act_addressbook;
    }

    @Override
    protected void initViews() {

        mRightLetter.setTextView(mLetterTipTv);
        mRightLetter.setOnTouchingLetterChangedListener(this);

        mPyComparator = new PinyinComparator<>();

        mAdapter = new AddressbookAdapter(new ItemClickIml(this));
        mAddressbookList = mAdapter.getData();

        mOptimumRv.setLayoutManager(new LinearLayoutManager(this));
        mOptimumRv.addItemDecoration(new TitleItemDecoration<>(this, mAddressbookList, "↑"));
        mOptimumRv.addItemDecoration(DividerUtil.getDefalutDivider(AppContext.getInstance(), 1));
        mOptimumRv.setAdapter(mAdapter);

        mAddressbookList.add(new Addressbook("陈仲锦"));
        mAddressbookList.add(new Addressbook("曹思维"));
        mAddressbookList.add(new Addressbook("陈佳峰"));
        mAddressbookList.add(new Addressbook("包小柏"));
        mAddressbookList.add(new Addressbook("贝壳"));
        mAddressbookList.add(new Addressbook("博陆山"));
        mAddressbookList.add(new Addressbook("林宇"));
        mAddressbookList.add(new Addressbook("张晓华"));
        mAddressbookList.add(new Addressbook("林世刚"));
        mAddressbookList.add(new Addressbook("何杰伟"));
        mAddressbookList.add(new Addressbook("凌云"));
        mAddressbookList.add(new Addressbook("周方慧"));
        mAddressbookList.add(new Addressbook("刘莹"));
        mAddressbookList.add(new Addressbook("胡玉婷"));
        mAddressbookList.add(new Addressbook("陈耀枨"));
        mAddressbookList.add(new Addressbook("王彪"));
        mAddressbookList.add(new Addressbook("何琴"));
        mAddressbookList.add(new Addressbook("卫裕发"));
        mAddressbookList.add(new Addressbook("梁锴"));
        mAddressbookList.add(new Addressbook("朱世科"));
        mAddressbookList.add(new Addressbook("马龙"));
        mAddressbookList.add(new Addressbook("孙悟空"));
        mAddressbookList.add(new Addressbook("唐憎"));

        for (int i = 0; i < mAddressbookList.size(); i++) {
            Addressbook addressbook = mAddressbookList.get(i);
            String pinyin = CharacterParser.getInstance().getSelling(addressbook.getRealname());
            addressbook.setItemDecorationtag(pinyin.substring(0, 1).toUpperCase());
        }
        Collections.sort(mAddressbookList, mPyComparator);

        Addressbook addressbook = new Addressbook();
        addressbook.setItemDecorationtag("↑");
        mAddressbookList.add(0, addressbook);
        mAddressbookList.add(0, addressbook);
        mAddressbookList.add(0, addressbook);

        mAdapter.update(mAddressbookList);
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        if (s.equals("↑")) {
            mOptimumRv.move(0);
        } else {
            int index = mAdapter.getFirstPosition(s);
            if (index != -1) {
                mOptimumRv.move(index);
            }
        }
    }
}
