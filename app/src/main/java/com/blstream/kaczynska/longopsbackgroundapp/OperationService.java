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


    protected class LocalBinder extends Binder {
        public OperationService getService() {
            return OperationService.this;
        }
    }

    public long countTime(final long timePeriod) {
        CountDownTimer countDownTimer = new CountDownTimer(timePeriod * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), String.valueOf(timePeriod) + " seconds have passed", Toast.LENGTH_LONG).show();
            }
        }.start();
        return timePeriod;
    }



}
