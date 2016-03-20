package com.blstream.kaczynska.longopsbackgroundapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Toast;


public class OperationService extends Service {

    private final IBinder mbinder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service is created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mbinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service is stopped", Toast.LENGTH_SHORT).show();
    }

    //    public long countTime(final long timePeriod) {
    public void countTime(final Operation operation) {
        CountDownTimer countDownTimer = new CountDownTimer(operation.getDurationTime(), 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                sendUpdateTimeMsg(operation.getId(), millisUntilFinished);
            }

            @Override
            public void onFinish() {
                sendUpdateTimeMsg(operation.getId(), 0);
            }
        }.start();
    }

    private void sendUpdateTimeMsg(int operationId, long remainingTime) {
        Intent intent = new Intent(MainActivity.RECEIVEMSG);
        // You can also include some extra data.
        intent.putExtra("operationId", String.valueOf(operationId));
        intent.putExtra("remainingTime", String.valueOf(remainingTime));
        sendBroadcast(intent);
    }

    protected class LocalBinder extends Binder {
        public OperationService getService() {
            return OperationService.this;
        }
    }
}
