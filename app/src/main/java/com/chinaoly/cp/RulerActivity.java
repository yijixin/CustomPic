package com.chinaoly.cp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chinaoly.cp.beans.TVideoFile;
import com.chinaoly.cp.view.ScalePanel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Yjx
 */
public class RulerActivity extends AppCompatActivity implements ScalePanel.OnValueChangeListener{

    /**
     * 时间刻度盘
     */
    private ScalePanel scalePanel;
    List<TVideoFile> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruler);

        initData();
        scalePanel = (ScalePanel) findViewById(R.id.scalePanel);
        scalePanel.setValueChangeListener(this);
        Calendar mCalendar = Calendar.getInstance();
        //设置时间块数据
        scalePanel.setTimeData(data);
        //设置当前时间
        scalePanel.setCalendar(mCalendar);
    }

    private void initData() {
        for (int hourOffset = -5; Math.abs(hourOffset) <= 5; hourOffset++) {
            addTimeBloack(hourOffset);
        }
    }

    private void addTimeBloack(int hourOffset) {
        TVideoFile file = new TVideoFile();
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY) + hourOffset);
        startTime.set(Calendar.MINUTE, 0);
        file.startTime = startTime;

        Calendar endTime = Calendar.getInstance();
        endTime.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY) + hourOffset);
        endTime.set(Calendar.MINUTE, 60);
        file.endTime = endTime;
        data.add(file);
    }

    @Override
    public void onValueChange(float value) {

    }

    @Override
    public void onValueChangeEnd(Calendar mCalendar) {

    }
}
