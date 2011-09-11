package com.zidar.alarm;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import com.zidar.alarm.R;

/**
 * Android Accelerometer Sensor Manager Archetype
 * @author antoine vianey
 * under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 */
public class AccelerometerManager {
	
	private static Sensor sSensor;
	private static SensorManager sSensorManager;
	// you could use an OrientationListener array instead
	// if you plans to use more than one listener
	private static AccelerometerListener sListener;
	
	/** indicates whether or not Accelerometer Sensor is supported */
	private static Boolean sSupported;
	/** indicates whether or not Accelerometer Sensor is running */
	private static boolean sRunning = false;
	
	/**
	 * Returns true if the manager is listening to orientation changes
	 */
	public static boolean isListening() {
		return sRunning;
	}
	
	/**
	 * Unregisters listeners
	 */
	public static void stopListening() {
		sRunning = false;
		try {
			if (sSensorManager != null && sensorEventListener != null) {
				sSensorManager.unregisterListener(sensorEventListener);
			}
		} catch (Exception e) {
			Log.e("stopListening", e.toString());
		}
	}
	
	/**
	 * Returns true if at least one Accelerometer sensor is available
	 */
	public static boolean isSupported() {
		if (sSupported == null) {
			if (AccelerometerMonitorActivity.getContext() != null) {
				sSensorManager = (SensorManager) AccelerometerMonitorActivity.getContext().
					getSystemService(Context.SENSOR_SERVICE);
				List<Sensor> sensors = sSensorManager.getSensorList(
						Sensor.TYPE_ACCELEROMETER);
				sSupported = new Boolean(sensors.size() > 0);
			} else {
				sSupported = Boolean.FALSE;
			}
		}
		return sSupported;
	}
	
	/**
	 * Registers a listener and start listening
	 * @param listener
	 * 			callback for accelerometer events
	 */
	public static void startListening(AccelerometerListener listener) {
		sSensorManager = (SensorManager) AccelerometerMonitorActivity.getContext().
			getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensors = sSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (sensors.size() > 0) {
			sSensor = sensors.get(0);
			sRunning = sSensorManager.registerListener(
					sensorEventListener, sSensor, 
					SensorManager.SENSOR_DELAY_NORMAL);
			sListener = listener;
		}
	}
	
	/**
	 * Configures threshold and interval
	 * And registers a listener and start listening
	 * @param accelerometerListener
	 * 			callback for accelerometer events
	 * @param threshold
	 * 			minimum acceleration variation for considering shaking
	 * @param interval
	 * 			minimum interval between to shake events
	 */
	public static void startListening(
			AccelerometerListener accelerometerListener, 
			int threshold, int interval) {
		startListening(accelerometerListener);
	}
	
	/**
	 * The listener that listen to events from the accelerometer listener
	 */
	private static SensorEventListener sensorEventListener = 
		new SensorEventListener() {
		
		
		private float x = 0;
		private float y = 0;
		private float z = 0;
		
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
		
		public void onSensorChanged(SensorEvent event) {
			
			x = event.values[0];
			y = event.values[1];
			z = event.values[2];
			
			// trigger change event
			sListener.onAccelerationChanged(x, y, z);
		}
		
	};
	
}
