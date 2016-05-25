/*
 *   "TiMqWay"
 *
 *    TiMqWay(tm): A gateway to provide an MQTT-View for the Tinkerforge(tm) world (Tinkerforge-MQTT-Gateway).
 *
 *    Copyright (c) 2015 Bern University of Applied Sciences (BFH),
 *    Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *    Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *    Licensed under Dual License consisting of:
 *    1. GNU Affero General Public License (AGPL) v3
 *    and
 *    2. Commercial license
 *
 *
 *    1. This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *    2. Licensees holding valid commercial licenses for TiMqWay may use this file in
 *     accordance with the commercial license agreement provided with the
 *     Software or, alternatively, in accordance with the terms contained in
 *     a written agreement between you and Bern University of Applied Sciences (BFH),
 *     Research Institute for Security in the Information Society (RISIS), Wireless Communications & Secure Internet of Things (WiCom & SIoT),
 *     Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *     For further information contact <e-mail: reto.koenig@bfh.ch>
 *
 *
 */
package ch.quantasy.gateway.service.device.multiTouch;

import ch.quantasy.gateway.service.device.AbstractDeviceService;
import ch.quantasy.tinkerforge.device.multiTouch.MultiTouchDevice;
import ch.quantasy.tinkerforge.device.multiTouch.MultiTouchDeviceCallback;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author reto
 */
public class MultiTouchService extends AbstractDeviceService<MultiTouchDevice, MultiTouchServiceContract> implements MultiTouchDeviceCallback {

    public MultiTouchService(MultiTouchDevice device, URI mqttURI) throws MqttException {
        super(device, new MultiTouchServiceContract(device), mqttURI);
        addDescription(getServiceContract().INTENT_ELECTRODE_CONFIG, "[0..8191]");
        addDescription(getServiceContract().INTENT_ELECTRODE_SENSITIVITY, "[0..8191]");
        addDescription(getServiceContract().INTENT_RECALIBRATE, "[true|false]");
        
        addDescription(getServiceContract().EVENT_TOUCH_STATE, "timestamp: [0.." + Long.MAX_VALUE + "]\n value: [0..8191]\n");
        addDescription(getServiceContract().EVENT_RECALIBRATED, "timestamp: [0.." + Long.MAX_VALUE + "]");
        
        addDescription(getServiceContract().STATUS_ELECTRODE_SENSITIVITY, "[5..201]");
        addDescription(getServiceContract().STATUS_ELECTRODE_CONFIG, "[0..8191]");
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) {
        byte[] payload = mm.getPayload();
        if (payload == null) {
            return;
        }
        try {
            if (string.startsWith(getServiceContract().INTENT_RECALIBRATE)) {
                Boolean recalibrate = getMapper().readValue(payload, Boolean.class);
                getDevice().recalibrate(recalibrate);
            }
            if (string.startsWith(getServiceContract().INTENT_ELECTRODE_CONFIG)) {
                Integer electrodeConfig = getMapper().readValue(payload, Integer.class);
                getDevice().setElectrodeConfig(electrodeConfig);
            }
            if (string.startsWith(getServiceContract().INTENT_ELECTRODE_SENSITIVITY)) {
                Short electrodeConfig = getMapper().readValue(payload, Short.class);
                getDevice().setElectrodeSensitivity(electrodeConfig);
            }

        } catch (Exception ex) {
            Logger.getLogger(MultiTouchService.class.getName()).log(Level.INFO, null, ex);
        }
    }

    @Override
    public void electrodeConfigChanged(Integer electrodeConfig) {
        addStatus(getServiceContract().STATUS_ELECTRODE_CONFIG, electrodeConfig);
    }

    @Override
    public void electrodeSensitivityChanged(Short electrodeSensitivity) {
        addStatus(getServiceContract().STATUS_ELECTRODE_SENSITIVITY, electrodeSensitivity);
    }

    @Override
    public void recalibrated() {
        addEvent(getServiceContract().EVENT_RECALIBRATED, System.currentTimeMillis());
    }

    @Override
    public void touchState(int i) {
        addEvent(getServiceContract().EVENT_TOUCH_STATE, new TouchStateEvent(i));
    }

    public static class TouchStateEvent {

        long timestamp;
        int value;

        public TouchStateEvent(int value) {
            this(value, System.currentTimeMillis());
        }

        public TouchStateEvent(int value, long timeStamp) {
            this.value = value;
            this.timestamp = timeStamp;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getValue() {
            return value;
        }

    }

}