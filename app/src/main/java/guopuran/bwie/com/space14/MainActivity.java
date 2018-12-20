package guopuran.bwie.com.space14;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import guopuran.bwie.com.space14.custom.CustomTitle;
import guopuran.bwie.com.space14.custom.CustomView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }


    private void initView() {
        CustomTitle customTitle=findViewById(R.id.main_title);
        final CustomView customView01= findViewById(R.id.main_liushi);
        final CustomView customView02=findViewById(R.id.main_liushi2);
        customTitle.setOnButtonClickListener(new CustomTitle.OnButtonClickListener() {
            @Override
            public void onButtonClick(String str) {
                TextView textView=new TextView(MainActivity.this);
                textView.setText(str);
                textView.setTextColor(Color.parseColor("#cccfff"));
                textView.setBackgroundResource(R.drawable.bd_bg);
                customView02.addView(textView);
            }
        });
        for (int i=0;i<30;i++){
            TextView text=new TextView(MainActivity.this);
            text.setText("数据"+i);
            text.setTextColor(Color.parseColor("#cccfff"));
            text.setBackgroundResource(R.drawable.bd_bg);
            customView01.addView(text);
        }
    }
}
