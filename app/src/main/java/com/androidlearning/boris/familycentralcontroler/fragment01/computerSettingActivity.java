package com.androidlearning.boris.familycentralcontroler.fragment01;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidlearning.boris.familycentralcontroler.Order;
import com.androidlearning.boris.familycentralcontroler.R;

/**
 * Project Name： FamilyCentralControler
 * Description:
 * Author: boris
 * Time: 2017/1/6 16:20
 */

public class computerSettingActivity extends Activity implements View.OnClickListener{

    private ImageButton returnLogo_imgButton;
    private LinearLayout rebootComputer_linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.computersetting_layout01);

        initView();
        initEvent();
    }

    private void initEvent() {
        returnLogo_imgButton.setOnClickListener(this);
        rebootComputer_linearLayout.setOnClickListener(this);
    }

    private void initView() {
        returnLogo_imgButton = (ImageButton) findViewById(R.id.returnLogo_imgButton);
        rebootComputer_linearLayout = (LinearLayout) findViewById(R.id.rebootComputer_linearLayout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.returnLogo_imgButton:
                computerSettingActivity.this.finish();
                break;
            case R.id.rebootComputer_linearLayout:
                boolean state = MyConnector.getInstance().getConnetedState();
                if(state) {
                    new Thread() {
                        @Override
                        public void run() {
                            prepareDataForFragment.getActionStateData(Order.computer_name,
                                    Order.computer_command, "");
                        }
                    }.start();

                } else {
                    Toast.makeText(this, "主机端未打开服务程序\n请先打开服务程序", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
