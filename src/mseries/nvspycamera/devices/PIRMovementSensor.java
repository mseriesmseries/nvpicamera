/*
 *   Copyright (c) 2016 Martin Newstead (java@mseries.plus.com).  All Rights Reserved.
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

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.pi4j.io.gpio.GpioPinDigital;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class PIRMovementSensor implements ApplicationEventPublisherAware {

	private GpioPinDigitalInput pin;
	private ApplicationEventPublisher publisher;

	public PIRMovementSensor(GpioPinDigital pin) {
		this.pin = (GpioPinDigitalInput) pin;

		// = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, "PIR",
		// PinPullResistance.PULL_DOWN);



		this.pin.addListener(new GpioPinListenerDigital() {

			@Override
			public void handleGpioPinDigitalStateChangeEvent(
					GpioPinDigitalStateChangeEvent event) {
				publisher.publishEvent(new SensorEvent(this, event.getPin()
						.getName(), event.getState()));
			}
		});
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
}
