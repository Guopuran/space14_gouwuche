package guopuran.bwie.com.space14.custom;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import guopuran.bwie.com.space14.LoginActivity;
import guopuran.bwie.com.space14.R;

public class CustomTitle extends LinearLayout {
    private Context context;
    public CustomTitle(Context context) {
        super(context);
        init(context);
    }

    public CustomTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        this.context=context;
        View view = inflate(context, R.layout.title, null);
        final ImageView image_queding=view.findViewById(R.id.title_queding);
        final ImageView image_fanhui=view.findViewById(R.id.title_fanhui);
        final EditText editText=view.findViewById(R.id.title_edit);
        addView(view);
        image_queding.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener!=null){
                    onButtonClickListener.onButtonClick(editText.getText().toString());
                }
            }
        });
        image_fanhui.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,LoginActivity.class);
                context.startActivity(intent);
            }
        });
    }
    public OnButtonClickListener onButtonClickListener;
    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener){
        this.onButtonClickListener=onButtonClickListener;
    }
    public interface OnButtonClickListener{
        void onButtonClick(String str);
    }
}
