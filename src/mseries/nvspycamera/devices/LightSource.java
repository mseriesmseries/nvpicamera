/*
 *   Copyright (c) 2017 Martin Newstead (java@mseries.plus.com).  All Rights Reserved.
 *
 *   The author makes no representations or warranties about the suitability of the
 *   software, either express or implied, including but not limited to the
 *   implied warranties of merchantability, fitness for a particular
 *   purpose, or non-infringement. The author shall not be liable for any damages
 *   suffered by licensee as a result of using, modifying or distributing
 *   this software or its derivatives.
 *
 *   The author requests that he be notified of any application, applet, or other binary that
 *   makes use of this code and that some acknowledgement is given. Comments, questions and
 *   requests for change will be welcomed.
 */
package mseries.nvspycamera.devices;

import mseries.nvspycamera.utils.SensorEvent;

import org.springframework.context.ApplicationListener;

import com.pi4j.io.gpio.GpioPinDigital;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class LightSource implements ApplicationListener<SensorEvent> {

	private Timer ignoreTimer = new Timer();
	private boolean suspend = false;
	private long sleepTime=120000;
	private GpioPinDigitalOutput pin;

	public LightSource(GpioPinDigital pin) {
		this.pin = (GpioPinDigitalOutput) pin;
	}
	@Override
	public void onApplicationEvent(SensorEvent event) {

		if (suspend) {
			ignoreTimer.setSleepTime(sleepTime);
			ignoreTimer.start();
		}

		if (ignoreTimer.isIgnore()) {
			return;
		}
		
		// Now do something with the event
		// Switch the light on
		pin.setState(true);

	}

	class Timer extends Thread {

		private long sleepTime = 60000;
		private boolean ignore = false;

		public void run() {
			ignore = true;
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				ignore = false;
			}
		}

		public boolean isIgnore() {
			return ignore;
		}

		public long getSleepTime() {
			return sleepTime;
		}

		public void setSleepTime(long sleepTime) {
			this.sleepTime = sleepTime;
		}

	}

	public boolean isSuspend() {
		return suspend;
	}

	public void setSuspend(boolean suspend) {
		this.suspend = suspend;
	}

	public long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}
}
