package guopuran.bwie.com.space14.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import guopuran.bwie.com.space14.R;
import guopuran.bwie.com.space14.bean.ShopBean;
import guopuran.bwie.com.space14.PinAdapter;

public class CustomNum extends LinearLayout implements View.OnClickListener {
    private EditText editText;
    private Context context;
    public CustomNum(Context context) {
        super(context);
        init(context);
    }

    public CustomNum(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomNum(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context=context;
        View view=View.inflate(context, R.layout.shangpin_num,null);
        ImageView jian=view.findViewById(R.id.num_jian);
        ImageView jia=view.findViewById(R.id.num_jia);
        editText=view.findViewById(R.id.num_text);
        jian.setOnClickListener(this);
        jia.setOnClickListener(this);
        addView(view);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s.toString();
                num = Integer.valueOf(s1);
                getitem.setNum(num);
                if (listener!=null){
                    listener.callBack();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private int num;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.num_jian:
                if (num>1){
                    num--;
                }else{
                    Toast.makeText(context, "我是有底线的啊", Toast.LENGTH_SHORT).show();
                }
                editText.setText(num+"");
                //list.get(position).setNum(num);
                getitem.setNum(num);
                listener.callBack();
                //pinAdapter.notifyItemChanged(position);
                break;
            case R.id.num_jia:
                num++;
                editText.setText(num+"");
                //list.get(position).setNum(num);
                getitem.setNum(num);
                listener.callBack();
                //pinAdapter.notifyItemChanged(position);
                break;
            default:break;
        }
    }

    private List<ShopBean.DataBean.ListBean> list=new ArrayList<>();
    private int position;
    private PinAdapter pinAdapter;
    //传递的数据
    public void setData(PinAdapter pinAdapter, List<ShopBean.DataBean.ListBean> list,int i){
        this.list=list;
        this.pinAdapter=pinAdapter;
        position=i;
        num=list.get(i).getNum();
        editText.setText(num+"");
    }
    private ShopBean.DataBean.ListBean getitem;
    //第二种传递数据
    public void setnum(ShopBean.DataBean.ListBean getitem){
        this.getitem=getitem;
        num = getitem.getNum();
        editText.setText(num+"");
    };
    private CallBackListener listener;
    public void setOnCallBack(CallBackListener listener){
        this.listener=listener;
    }
    public interface CallBackListener{
        void callBack();
    }
}
