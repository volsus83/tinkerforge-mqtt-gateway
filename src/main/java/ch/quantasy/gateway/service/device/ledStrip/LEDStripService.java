/*
 * /*
 *  *   "TiMqWay"
 *  *
 *  *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *  *
 *  *    Copyright (c) 2016 Bern University of Applied Sciences (BFH),
 *  *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *  *    Quellgasse 21, CH-2501 Biel, Switzerland
 *  *
 *  *    Licensed under Dual License consisting of:
 *  *    1. GNU Affero General Public License (AGPL) v3
 *  *    and
 *  *    2. Commercial license
 *  *
 *  *
 *  *    1. This program is free software: you can redistribute it and/or modify
 *  *     it under the terms of the GNU Affero General Public License as published by
 *  *     the Free Software Foundation, either version 3 of the License, or
 *  *     (at your option) any later version.
 *  *
 *  *     This program is distributed in the hope that it will be useful,
 *  *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *     GNU Affero General Public License for more details.
 *  *
 *  *     You should have received a copy of the GNU Affero General Public License
 *  *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  *
 *  *
 *  *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *  *     accordance with the commercial license agreement provided with the
 *  *     Software or, alternatively, in accordance with the terms contained in
 *  *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *  *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *  *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *  *
 *  *
 *  *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *  *
 *  *
 */
package ch.quantasy.gateway.service.device.ledStrip;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import org.eclipse.paho.client.mqttv3.MqttException;
import ch.quantasy.tinkerforge.device.led.LEDStripDevice;
import ch.quantasy.tinkerforge.device.led.LEDStripDeviceCallback;
import ch.quantasy.tinkerforge.device.led.LEDStripDeviceConfig;
import ch.quantasy.tinkerforge.device.led.LEDFrame;

import java.net.URI;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 *
 * @author reto
 */
public class LEDStripService extends AbstractDeviceService<LEDStripDevice, LEDStripServiceContract> implements LEDStripDeviceCallback {

    private LEDFrame frame;
    private Deque<LEDFrame> frames;

    public LEDStripService(LEDStripDevice device, URI mqttURI) throws MqttException {
        super(mqttURI, device, new LEDStripServiceContract(device));
        frames = new ConcurrentLinkedDeque<>();
    }

    @Override
    public void messageReceived(String string, byte[] payload) throws Exception {

        if (string.startsWith(getContract().INTENT_CONFIG)) {
            LEDStripDeviceConfig config = getMapper().readValue(payload, LEDStripDeviceConfig.class);
            getDevice().setConfig(config);
        }
        if (string.startsWith(getContract().INTENT_FRAME)) {
            synchronized (this) {
                frame = (getMapper().readValue(payload, LEDFrame.class));
                if (frame.isValid() && frames != null) {
                    frames.clear();
                    getDevice().readyToPublish(this);
                }
            }
        }
        if (string.startsWith(getContract().INTENT_FRAMES)) {
            LEDFrame[] internalFrames = (getMapper().readValue(payload, LEDFrame[].class));
            synchronized (this) {
                for (LEDFrame frame : internalFrames) {
                    frames.offer(frame);
                }
                getDevice().readyToPublish(this);
            }
        }
    }

    @Override
    public void configurationChanged(LEDStripDeviceConfig config) {
        publishStatus(getContract().STATUS_CONFIG, config);
    }

    @Override
    public LEDFrame getLEDsToPublish() {
        LEDFrame frame = frames.poll();
        if (frame != null) {
            if (!frames.isEmpty()) {
                getDevice().readyToPublish(this);
            }
            return frame;
        } else {
            return this.frame;
        }
    }

    @Override
    public void frameRendered() {
        publishEvent(getContract().EVENT_LEDs_RENDERED, frames.size());

    }

    @Override
    public void isLaging() {
        publishEvent(getContract().EVENT_LAGING, true);

    }

}
