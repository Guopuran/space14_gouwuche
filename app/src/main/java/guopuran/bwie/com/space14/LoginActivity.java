package guopuran.bwie.com.space14;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import guopuran.bwie.com.space14.bean.ShopBean;
import guopuran.bwie.com.space14.presenter.IpresenterImpl;
import guopuran.bwie.com.space14.view.IView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private String url="http://www.zhaoapi.cn/product/getCarts";
    private IpresenterImpl mIpresenterImpl;
    private RecyclerView login_recy;
    private CheckBox login_box;
    private TextView login_text;
    private JiaAdapter jiaAdapter;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initPresenter();
        initView();
        initUrl();
    }

    private void initView() {
        //获取资源ID
        login_recy = findViewById(R.id.login_recy);
        login_box = findViewById(R.id.login_box_xuan);
        login_text = findViewById(R.id.login_text_zongjia);
        login_button = findViewById(R.id.login_button_jiesuan);
        login_box.setOnClickListener(this);
        //管理器
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        login_recy.setLayoutManager(linearLayoutManager);
        login_recy.addItemDecoration(new DividerItemDecoration(this,OrientationHelper.VERTICAL));
        jiaAdapter = new JiaAdapter(this);
        login_recy.setAdapter(jiaAdapter);
        jiaAdapter.setListener(new JiaAdapter.ShopCallBackListener() {
            @Override
            public void callBack(List<ShopBean.DataBean> list) {
                //在这里重新遍历已经改变状态后的数据
                //这里不能break跳出，因为还有需要计算后面点击商品的价格和数量，所以必须跑完整个循环
                double totalPrice=0;
                //勾选商品的数量，不是该商品购买的数量
                int num=0;
                //所有商品总数，和上面的数量做比对，如果两者相等，则说明全选
                int totalNum=0;
                for (int a=0;a<list.size();a++){
                    //获取商家里的商品
                    List<ShopBean.DataBean.ListBean> listAll = list.get(a).getList();
                    for (int i=0;i<listAll.size();i++){
                        totalNum=totalNum+listAll.get(i).getNum();
                        if (listAll.get(i).isCheck()){
                            totalPrice=totalPrice+listAll.get(i).getPrice()*listAll.get(i).getNum();
                            num=num+listAll.get(i).getNum();
                        }
                    }

                }
                if (num<totalNum){
                    login_box.setChecked(false);
                }else{
                    login_box.setChecked(true);
                }
                login_text.setText("合计:"+totalPrice);
                login_button.setText("去结算("+num+")");
            }
        });

    }

    //互绑
    private void initPresenter() {
        mIpresenterImpl=new IpresenterImpl(this);
    }

    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIpresenterImpl.Deatch();
    }
    public void initUrl(){
        Map<String,String> params=new HashMap<>();
        params.put("uid","90");
        mIpresenterImpl.requestter(url,params,ShopBean.class);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.login_box_xuan:
                checkall(login_box.isChecked());
                jiaAdapter.notifyDataSetChanged();
                break;
            default:break;
        }
    }
    List<ShopBean.DataBean> list=new ArrayList<>();
    private void checkall(boolean checked) {

        double totalPrice=0;
        int num=0;
        for (int a=0;a<list.size();a++){
            //遍历商家，改变状态
            list.get(a).setIscheck(checked);
            List<ShopBean.DataBean.ListBean> listAll = list.get(a).getList();
            for (int i=0;i<listAll.size();i++){
                //遍历商品，改变状态
                listAll.get(i).setCheck(checked);
                totalPrice=totalPrice+(listAll.get(i).getPrice()*listAll.get(i).getNum());
                num=num+listAll.get(i).getNum();
            }
        }
        if (checked){
            login_text.setText("合计:"+totalPrice);
            login_button.setText("去结算("+num+")");
        }else{
            login_text.setText("合计:0.0");
            login_button.setText("去结算(0)");
        }
    }

    @Override
    public void getdata(Object object) {
        if (object instanceof ShopBean){
            ShopBean bean= (ShopBean) object;
            bean.getData().remove(0);
            list = bean.getData();
            jiaAdapter.setList(list);
        }
    }
}
