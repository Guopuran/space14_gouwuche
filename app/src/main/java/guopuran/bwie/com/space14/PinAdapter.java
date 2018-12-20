package guopuran.bwie.com.space14;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import guopuran.bwie.com.space14.bean.ShopBean;
import guopuran.bwie.com.space14.custom.CustomNum;

public class PinAdapter extends RecyclerView.Adapter<PinAdapter.ViewHolder> {
    private List<ShopBean.DataBean.ListBean> list;
    private Context context;

    public PinAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<ShopBean.DataBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public ShopBean.DataBean.ListBean getitem(int position){
        return list.get(position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shangpin, viewGroup, false);
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
        private CheckBox pin_box;
        private ImageView pin_image;
        private TextView pin_name;
        private TextView pin_price;
        private CustomNum customNum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pin_box=itemView.findViewById(R.id.item_pin_box);
            pin_image=itemView.findViewById(R.id.item_pin_image);
            pin_name=itemView.findViewById(R.id.item_pin_name);
            pin_price=itemView.findViewById(R.id.item_pin_price);
            customNum=itemView.findViewById(R.id.item_custom_num);
        }

        public void getdata(final ShopBean.DataBean.ListBean getitem, Context context, int i) {
            String image_url = getitem.getImages().split("\\|")[0].replace("https", "http");
            Glide.with(context).load(image_url).into(pin_image);
            pin_name.setText(getitem.getTitle());
            pin_price.setText("优惠价:"+getitem.getPrice());
            //设置自定义view里的Edit
            //customNum.setData(PinAdapter.this,list,i);
            //第二种方法
            customNum.setnum(getitem);
            customNum.setOnCallBack(new CustomNum.CallBackListener() {
                @Override
                public void callBack() {
                    if (mShopCallBackListener!=null){
                        mShopCallBackListener.callBack();
                    }
                }
            });
            pin_box.setChecked(getitem.isCheck());
            pin_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //优先改变自己的状态
                getitem.setCheck(isChecked);
                //回调，目的是告诉activity,有人选中状态被改变
                if (mShopCallBackListener!=null){
                    mShopCallBackListener.callBack();
                }
            }
        });
    }
    }
    //在商品的adapter中，修改子商品的全选全不选
    public void selectOrRemoveAll(boolean isSelectAll){
        for (ShopBean.DataBean.ListBean bean: list){
            bean.setCheck(isSelectAll);
        }
        notifyDataSetChanged();
    }

    public ShopCallBackListener mShopCallBackListener;
    public void setListener(ShopCallBackListener listener){
        this.mShopCallBackListener=listener;
    }
    public interface  ShopCallBackListener{
        void callBack();
    }

}
