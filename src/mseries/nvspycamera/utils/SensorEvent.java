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
package mseries.nvspycamera.utils;

import org.springframework.context.ApplicationEvent;

import com.pi4j.io.gpio.PinState;

public class SensorEvent extends ApplicationEvent {

	public SensorEvent(Object source, String name, PinState pinState) {
		super(source);
	}
	

}
