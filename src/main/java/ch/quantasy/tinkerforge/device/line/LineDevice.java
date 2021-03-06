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
package ch.quantasy.tinkerforge.device.line;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletLine;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LineDevice extends GenericDevice<BrickletLine, LineDeviceCallback> {

    private Long callbackPeriod;
    private Long debouncePeriod;
    private DeviceReflectivityCallbackThreshold reflectivityThreshold;

    public LineDevice(TinkerforgeStack stack, BrickletLine device) throws NotConnectedException, TimeoutException {
        super(stack, device);
    }

    @Override
    protected void addDeviceListeners(BrickletLine device) {
        device.addReflectivityListener(super.getCallback());
        device.addReflectivityReachedListener(super.getCallback());

        if (this.debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
        if (this.callbackPeriod != null) {
            setReflectivityCallbackPeriod(callbackPeriod);
        }
        if (this.reflectivityThreshold != null) {
            setReflectivityCallbackThreshold(reflectivityThreshold);
        }

    }

    @Override
    protected void removeDeviceListeners(BrickletLine device) {
        device.removeReflectivityListener(super.getCallback());
        device.removeReflectivityReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            super.getCallback().debouncePeriodChanged(getDevice().getDebouncePeriod());
            this.debouncePeriod = period;
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LineDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setReflectivityCallbackPeriod(Long period) {
        try {
            getDevice().setReflectivityCallbackPeriod(period);
            this.callbackPeriod = getDevice().getReflectivityCallbackPeriod();
            super.getCallback().reflectivityCallbackPeriodChanged(this.callbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LineDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setReflectivityCallbackThreshold(DeviceReflectivityCallbackThreshold threshold) {
        try {
            getDevice().setReflectivityCallbackThreshold(threshold.option, threshold.min, threshold.max);
            this.reflectivityThreshold = new DeviceReflectivityCallbackThreshold(getDevice().getReflectivityCallbackThreshold());
            super.getCallback().reflectivityThresholdChanged(this.reflectivityThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(LineDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
