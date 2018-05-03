package com.legend.ffpmvp.common.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.legend.ffpmvp.R;
import com.legend.ffpmvp.common.bean.HomePlanBean;
import com.legend.ffpmvp.main.model.ICommonModel;
import com.legend.ffpmvp.main.personalcenter.model.BackLogModelImpl;
import com.legend.ffpmvp.main.personalcenter.view.BackLogActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2018/2/5.
 * @description 计划提醒服务
 */

public class PlanNotifyService extends Service {

    private NotificationManager manager;
    private List<HomePlanBean> planBeans = new ArrayList<>();
    private static int id = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        getBackLogSize();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (getBackLogSize() > 0) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            intent = new Intent(this,BackLogActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            id = 1;
            for (HomePlanBean plan : planBeans) {
                //通知消息下拉是显示的文本内容
                builder.setContentText(plan.getContent()+"...！行动才是理想最高贵的表达");
                //通知栏消息下拉时显示的标题
                builder.setContentTitle("来自圈子："+plan.getFrom_circle_name());
                //接收到通知时，按手机的默认设置进行处理，声音，震动，灯
                builder.setDefaults(Notification.DEFAULT_ALL);
                //通知栏显示图标
                builder.setSmallIcon(R.drawable.ic_backlog);
                builder.setContentIntent(pendingIntent);
                Notification notification = builder.build();

                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                manager.notify(++id,notification);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public int getBackLogSize() {
        BackLogModelImpl backLogModel = new BackLogModelImpl();
        backLogModel.getData(0, new ICommonModel.RequestResult() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List beanList) {
                planBeans.clear();
                planBeans.addAll(beanList);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onEnd() {

            }
        });
        return planBeans.size();
    }

}
