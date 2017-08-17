package com.jianwuchen.androidwidgetdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jianwuchen.widgetlib.RoundAngleFrameLayout;
import java.util.Random;

public class RoundAngleFrameLayoutActivity extends AppCompatActivity {

    @BindView(R.id.roundAngleFrameLayout) RoundAngleFrameLayout roundAngleFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_angle_frame_layout);
        ButterKnife.bind(this);
    }

    public void randomChange(View view) {
        roundAngleFrameLayout.setRoundType(getRandomBooleam(), getRandomBooleam(),
                getRandomBooleam(), getRandomBooleam());
    }

    private boolean getRandomBooleam() {
        return new Random().nextBoolean();
    }

    public void randomSize(View view) {
        roundAngleFrameLayout.setRadius(new Random().nextInt(50));
    }
}
