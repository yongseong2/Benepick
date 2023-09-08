package com.benepick.EventListener;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;
import com.benepick.MainApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;

public class EventService extends Service implements SensorEventListener {
  private EventListenerModule eventListenerModule;
  private SensorManager sensorManager;
  private int lastShakeValue = -1;
  private long lastShakeTime = 0;
  private static final int SHAKE_THRESHOLD = 800;

    public EventService() {
        super();
    }

    private ReactContext getReactContext() {
        ReactInstanceManager reactInstanceManager = ((MainApplication) getApplication()).getReactNativeHost().getReactInstanceManager();
        return reactInstanceManager.getCurrentReactContext();
    }

  @Override
  public void onCreate() {
      super.onCreate();
      sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
      startListening();
      showToast("Start Listener");
      return START_STICKY;
  }

  @Override
  public void onDestroy() {
      stopListening();
      super.onDestroy();
  }

  @Override
  public IBinder onBind(Intent intent) {
      return null;
  }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long currentTime = System.currentTimeMillis();

            if ((currentTime - lastShakeTime) > 100) {
                long timeDiff = currentTime - lastShakeTime;
                lastShakeTime = currentTime;

                double speed = Math.abs(x + y + z - lastShakeValue) / timeDiff * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    EventListenerModule eventListenerModule = new EventListenerModule((ReactApplicationContext) getReactContext());
                    eventListenerModule.sendTrigger("Success");
                }

                lastShakeValue = (int) (x + y + z);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }

    private void startListening() {
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void stopListening() {
        sensorManager.unregisterListener(this);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}