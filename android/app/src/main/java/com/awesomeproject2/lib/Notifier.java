/*
 * Copyright (C) 2017 Jeremy Tecson
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.awesomeproject2.lib;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.RawRes;
import com.awesomeproject2.R;

/**
 * Schedules a local notification. <br> <br>
 *
 * The ff. happens when the local notification is triggered. <br>
 * 1. Wake up the screen. <br>
 * 2. Vibrate the phone. <br>
 * 3. Sets the ringer volume to 50%. <br>
 *
 * Note: This is not an all-purpose class that handles all cases that can happen for a local
 * notification.
 *
 * @author Jeremy Tecson
 */
public class Notifier {
  private Notifier() {
    // No instances.
  }

  public static void scheduleNotification(Context ctx, String title, String content,
      int timeInMillis, @RawRes int sound, int notificationId) {
    Context context = ctx.getApplicationContext();

    // Initialize notification
    Notification notification = getNotification(context, title, content, sound);
    Intent notificationIntent = new Intent(context, NotificationReceiver.class);
    notificationIntent.putExtra(NotificationReceiver.NOTIFICATION_ID, notificationId);
    notificationIntent.putExtra(NotificationReceiver.NOTIFICATION, notification);
    PendingIntent pendingIntent =
        PendingIntent.getBroadcast(context, notificationId, notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT);

    // Schedule notification
    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, timeInMillis, pendingIntent);
  }

  private static Notification getNotification(Context context, String title, String content,
      @RawRes int sound) {
    Uri soundUri = getSoundUriFromRaw(context.getPackageName(), sound);
    return new Notification.Builder(context) //
        .setContentTitle(title)
        .setContentText(content)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setCategory(Notification.CATEGORY_ALARM)
        .setVibrate(new long[] { 1000, 1000 })
        .setLights(Color.RED, 3000, 3000)
        .setSound(soundUri)
        .build();
  }

  private static Uri getSoundUriFromRaw(String packageName, @RawRes int soundRes) {
    return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + "/" + soundRes);
  }
}
