/*
 *Copyright (C) 2013 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cyanogenmod.settings.device;

import java.io.IOException;
import android.content.Context;
import android.util.AttributeSet;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.ListPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceManager;

public class PanelColorTemperature extends ListPreference implements OnPreferenceChangeListener {

    public PanelColorTemperature(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnPreferenceChangeListener(this);
    }

    private static final String FILE = "/sys/class/lcd/panel/panel_colors";

    public static boolean isSupported() {
        return Utils.fileExists(FILE);
    }

    /**
     * Restore panel temp setting from SharedPreferences. (Write to kernel.)
     * @param context       The context to read the SharedPreferences from
     */
    public static void restore(Context context) {
        if (!isSupported()) {
            return;
        }

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Utils.writeValue(FILE, sharedPrefs.getString(DisplaySettings.KEY_PANEL_COLOR_TEMPERATURE, "2"));
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Utils.writeValue(FILE, (String) newValue);
        return true;
    }

}

