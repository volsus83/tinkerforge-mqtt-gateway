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
package ch.quantasy.tinkerforge.device.generic;

import ch.quantasy.tinkerforge.device.TinkerforgeDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public abstract class GenericDevice<D extends Device, C extends DeviceCallback> extends TinkerforgeDevice<D> {

    private C callback;
    private boolean areListenerAdded;

    public GenericDevice(TinkerforgeStack address, D device) throws NotConnectedException, TimeoutException {
        super(address, device);
    }

    protected abstract void addDeviceListeners(D device);

    protected abstract void removeDeviceListeners(D device);

    @Override
    public D updateDevice(D device) throws TimeoutException, NotConnectedException, IllegalArgumentException {
        D oldDevice = super.updateDevice(device);

        removeDeviceListeners(oldDevice);
        areListenerAdded = false;

        addDeviceListeners(device);
        areListenerAdded = true;
        return device;
    }

    @Override
    public void connected() {
        super.connected();
        if (super.getDevice() != null && !areListenerAdded) {
            addDeviceListeners(getDevice());
            areListenerAdded = true;
        }
    }

    @Override
    public void disconnected() {
        super.disconnected();
        if (super.getDevice() != null && areListenerAdded) {
            removeDeviceListeners(getDevice());
            areListenerAdded = false;
        }
    }

    @Override
    public void reconnected() {
        super.reconnected();
        if (super.getDevice() != null && areListenerAdded) {
            removeDeviceListeners(getDevice());
            areListenerAdded = false;
        }
        if (super.getDevice() != null && !areListenerAdded) {
            areListenerAdded = true;
        }
        System.out.println("New Reconnection of DeviceListeners done.");
    }

    public void setCallback(C callback) {
        if (super.getDevice() != null && this.callback != null && areListenerAdded) {
            removeDeviceListeners(getDevice());
            areListenerAdded = false;
        }
        this.callback = callback;
        if (super.getDevice() != null && this.callback != null && !areListenerAdded) {
            addDeviceListeners(getDevice());
            areListenerAdded = true;
        }
    }

    public C getCallback() {
        return callback;
    }
}
