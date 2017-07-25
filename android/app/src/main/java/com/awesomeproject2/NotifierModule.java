package com.awesomeproject2;

import com.awesomeproject2.lib.Notifier;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * TODO Jemay: What does this class do?
 *
 * @author Jeremy Tecson
 */
public class NotifierModule extends ReactContextBaseJavaModule {

  public NotifierModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override public String getName() {
    return "NotifierModule";
  }

  @ReactMethod public void scheduleNotification(String title, String content, int timeInMillis,
      int notificationId) {
    Notifier.scheduleNotification(getReactApplicationContext(), title, content, timeInMillis,
        R.raw.test, notificationId);
  }
}
