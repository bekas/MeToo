/**
 * 
 */
package com.metoo.ui.views;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

/**
 * Интерфейс подписчика на событие длинного нажатия
 * @author theurgist
 */
public interface IOnLongPressListener {
    public void onLongpress(GeoPoint longpressLocation);
}