/*
 * Copyright (C) 2017 Jeremy Tecson
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.awesomeproject2.lib;

import android.content.Context;
import android.os.PowerManager;

class WakeLocker {

  private static PowerManager.WakeLock wakeLock;

  /***
   * Wakes the screen up.
   *
   * @param context The context
   */
  static void acquire(Context context) {
    if (wakeLock != null) wakeLock.release();

    PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
        PowerManager.ACQUIRE_CAUSES_WAKEUP |
        PowerManager.ON_AFTER_RELEASE, "betacode");

    wakeLock.acquire(50);
  }

  static void release() {
    if (wakeLock != null) wakeLock.release();
    wakeLock = null;
  }

  private WakeLocker() {
    throw new IllegalStateException("Can't be instantiated");
  }
}
