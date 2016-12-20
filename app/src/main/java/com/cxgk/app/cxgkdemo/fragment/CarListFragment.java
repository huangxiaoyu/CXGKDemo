package com.cxgk.app.cxgkdemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cxgk.app.cxgkdemo.R;
import com.cxgk.app.cxgkdemo.adapter.CarListAdatper;
import com.cxgk.app.cxgkdemo.bean.Page;
import com.cxgk.app.cxgkdemo.recycleviewdivider.HorizontalDividerItemDecoration;
import com.huang.app.swiperecyclerview.Closeable;
import com.huang.app.swiperecyclerview.OnSwipeMenuItemClickListener;
import com.huang.app.swiperecyclerview.SwipeMenu;
import com.huang.app.swiperecyclerview.SwipeMenuCreator;
import com.huang.app.swiperecyclerview.SwipeMenuItem;
import com.huang.app.swiperecyclerview.SwipeMenuRecyclerView;

/**
 * Created by lenovo on 2016/12/13.
 */

public class CarListFragment extends Fragment implements View.OnClickListener {
    SwipeRefreshLayout srl_layout;
    SwipeMenuRecyclerView rv_list;
    Page mPage;
    View searchView;
    EditText et_search;
    CarListAdatper mCarListAdatper;
    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.swipe_recyclerview_width);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(getContext())
                    .setBackgroundDrawable(R.drawable.selector_red)
                    .setImage(R.mipmap.icon_del)
                    .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

        }
    };
    private OnSwipeMenuItemClickListener mOnSwipeMenuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView#RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION && menuPosition == 0) {
                deleteCar(adapterPosition);
            }
        }
    };

    /**
     * 删除车辆
     *
     * @param adapterPosition
     */
    private void deleteCar(int adapterPosition) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = new Page();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_car_list, null);
        view.findViewById(R.id.iv_add).setOnClickListener(this);
        view.findViewById(R.id.iv_search).setOnClickListener(this);
        searchView = view.findViewById(R.id.ll_search);
        searchView.setVisibility(View.GONE);
        et_search = (EditText) view.findViewById(R.id.et_search);
        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                return false;
            }
        });
        srl_layout = (SwipeRefreshLayout) view.findViewById(R.id.srl_layout);
        rv_list = (SwipeMenuRecyclerView) view.findViewById(R.id.rv_list);
        rv_list.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        // 设置菜单创建器。
        rv_list.setSwipeMenuCreator(mSwipeMenuCreator);
        // 设置菜单Item点击监听。
        rv_list.setSwipeMenuItemClickListener(mOnSwipeMenuItemClickListener);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv_list.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).colorResId(R.color.transparent).sizeResId(R.dimen.list_divier).build());
        srl_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage.setPageIndex(1);
                mPage.setRefreshAble(true);
                loadData();
            }
        });
        return view;
    }

    /**
     * 加载数据
     */
    private void loadData() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                if (searchView.getVisibility() != View.VISIBLE) {
                    searchView.setVisibility(View.VISIBLE);
                } else {
                    searchView.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_add:
                break;
        }

    }

    /**
     * 隐藏搜索控件,
     *
     * @return true 已设置隐藏,false 已隐藏.不用设置隐藏
     */
    public boolean hidentSearchView() {
        if (searchView != null) {
            searchView.setVisibility(View.GONE);
            return true;
        }
        return false;
    }
}
