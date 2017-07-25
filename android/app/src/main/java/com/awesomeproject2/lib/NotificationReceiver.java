/*
 * Copyright (C) 2017 Jeremy Tecson
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.awesomeproject2.lib;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

/**
 * A {@link BroadcastReceiver} that receives the scheduled notification. <br> <br>
 *
 * When received, this receiver <br>
 * 1. Wakes the screen up <br>
 * 2. Changes the ringer volume to 50% <br>
 * 3. Show the notification. <br>
 *
 * @author Jeremy Tecson
 */
public class NotificationReceiver extends BroadcastReceiver {
  public static String NOTIFICATION = "notification";
  public static String NOTIFICATION_ID = "notification-id";

  public void onReceive(Context context, Intent intent) {

    // Wakes the screen up
    WakeLocker.acquire(context);

    // Change the ringer volume to 50%
    final AudioManager mgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    int volume = mgr.getStreamMaxVolume(AudioManager.STREAM_RING) / 2;
    changeRingerVolume(context, volume);

    // Show notification
    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    Notification notification = intent.getParcelableExtra(NOTIFICATION);
    int id = intent.getIntExtra(NOTIFICATION_ID, 0);
    notificationManager.notify(id, notification);

    // It is IMPORTANT to release the wake lock
    WakeLocker.release();
  }

  /**
   * Changes the ringer volume to 50%.
   *
   * @param context The context.
   */
  void changeRingerVolume(Context context, int volume) {
    final AudioManager mgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    mgr.setStreamVolume(AudioManager.STREAM_RING, volume, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
  }
}
