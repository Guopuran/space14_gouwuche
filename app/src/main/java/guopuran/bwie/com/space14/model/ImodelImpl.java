package guopuran.bwie.com.space14.model;

import java.util.Map;

import guopuran.bwie.com.space14.util.ICallBack;
import guopuran.bwie.com.space14.util.MyCallBack;
import guopuran.bwie.com.space14.util.OkUtil;

public class ImodelImpl implements Imodel{
    @Override
    public void requestmodel(String url, Map<String, String> params, Class clazz, final MyCallBack callBack) {

        OkUtil.getInstance().postenqueue(url, params, clazz, new ICallBack() {
            @Override
            public void success(Object object) {
                callBack.getdata(object);
            }

            @Override
            public void falied(Exception e) {

            }
        });
    }
}
