package guopuran.bwie.com.space14;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import guopuran.bwie.com.space14.bean.ShopBean;

public class JiaAdapter extends RecyclerView.Adapter<JiaAdapter.ViewHolder> {
    private List<ShopBean.DataBean> list;
    private Context context;

    public JiaAdapter(Context context) {
        this.context = context;
        //初始化
        list=new ArrayList<>();
    }
    //加载数据
    public void setList(List<ShopBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public ShopBean.DataBean getitem(int position){
        return list.get(position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shangjia, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.getdata(getitem(i),context,i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox jia_box;
        private TextView jia_name;
        private RecyclerView jia_recy;
        private PinAdapter pinAdapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jia_box=itemView.findViewById(R.id.item_jia_box);
            jia_name=itemView.findViewById(R.id.item_jia_name);
            jia_recy=itemView.findViewById(R.id.item_jia_recy);
        }

        public void getdata(ShopBean.DataBean getitem, Context context, int i) {
            //设置商家的名字
            jia_name.setText(getitem.getSellerName());
            //设置商家的复选框
            jia_box.setChecked(getitem.isIscheck());
            //商品的adapter
            initRecy(getitem);
            initData(getitem);
        }

        private void initData(final ShopBean.DataBean getitem) {
            pinAdapter.setListener(new PinAdapter.ShopCallBackListener() {
                @Override
                public void callBack() {
                    //从商品适配里回调回来，回给activity
                    if (mShopCallBackListener!=null){
                        mShopCallBackListener.callBack(list);
                    }
                    List<ShopBean.DataBean.ListBean> mlist = getitem.getList();
                    //创建一个临时的标志位，用来记录心在的点击状态
                    boolean isAllChecked=true;
                    for (ShopBean.DataBean.ListBean bean:mlist){
                        if (!bean.isCheck()){
                            //只要有一个商品未选中，标志位设置成false 并跳出循环
                            isAllChecked=false;
                            break;
                        }
                    }
                    //刷新商家的状态
                    jia_box.setChecked(isAllChecked);
                    getitem.setIscheck(isAllChecked);
                }
            });
            //监听checkbox的点击事件
            //目的是改变旗下所有商品的选中状态
            jia_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //首先改变自己的标志位
                    getitem.setIscheck(jia_box.isChecked());
                    //调用产品adapter的方法，用来全选和反选
                    pinAdapter.selectOrRemoveAll(jia_box.isChecked());
                }
            });

        }

        private void initRecy(final ShopBean.DataBean getitem) {
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            jia_recy.setLayoutManager(linearLayoutManager);
            pinAdapter= new PinAdapter(context);
            pinAdapter.setList(getitem.getList());
            jia_recy.setAdapter(pinAdapter);

        }
    }



    public ShopCallBackListener mShopCallBackListener;
    public void setListener(ShopCallBackListener listener){
        this.mShopCallBackListener=listener;
    }
    public interface ShopCallBackListener{
        void callBack(List<ShopBean.DataBean> list);
    }
}
