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
package mseries.nvspycamera;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigital;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;

public class PinConfigurer {

	private GpioController gpioController;
	private Map<String, String> pinMap = new HashMap<String, String>();
	private Log logger = LogFactory.getLog(PinConfigurer.class);

	public GpioController getGpioController() {
		return gpioController;
	}

	public void setGpioController(GpioController gpio) {
		this.gpioController = gpio;
	}

	public GpioPinDigital getPinFor(String device) {
		System.out.println("getPinFor");
		String[] parts = pinMap.get(device).split(":");
		return getPin(parts[0], device, parts[1], parts[2]);
	}

	protected GpioPinDigital getPin(String gpioName, String name, String type,
			String value) {
		Pin raspiPin = null;
		GpioPinDigital pin;

		logger.debug(gpioName + ", " + name + ", " + type + ", " + value);
		try {
			Class<?> c = Class.forName("com.pi4j.io.gpio.RaspiPin");
			Field f = c.getDeclaredField(gpioName);
			raspiPin = (Pin) f.get(null);

			switch (type) {
			case "INPUT":
				c = Class.forName("com.pi4j.io.gpio.PinPullResistance");
				f = c.getDeclaredField(value);
				pin = gpioController.provisionDigitalInputPin(raspiPin, name,
						(PinPullResistance) f.get(null));
				pin.setShutdownOptions(true, PinState.LOW,
						PinPullResistance.OFF);
				return pin;

			case "OUTPUT":
				c = Class.forName("com.pi4j.io.gpio.PinState");
				f = c.getDeclaredField(value);
				pin = gpioController.provisionDigitalOutputPin(raspiPin, name,
						(PinState) f.get(null));
				pin.setShutdownOptions(true, PinState.LOW);
				return pin;
			default:
				logger.error("Type code:" + type + " is not valid");
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return null;
	}

	/**
	 * @return the pinMap
	 */
	public Map<String, String> getPinMap() {
		return pinMap;
	}

	/**
	 * @param pinMap
	 *            the pinMap to set
	 */
	public void setPinMap(Map<String, String> pinMap) {
		this.pinMap = pinMap;
	}
}
