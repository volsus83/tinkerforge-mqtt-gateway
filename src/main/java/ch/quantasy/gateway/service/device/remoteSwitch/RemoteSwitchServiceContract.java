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
package ch.quantasy.gateway.service.device.remoteSwitch;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.TinkerforgeDeviceClass;
import ch.quantasy.tinkerforge.device.remoteSwitch.RemoteSwitchDevice;
import java.util.Map;

/**
 *
 * @author reto
 */
public class RemoteSwitchServiceContract extends DeviceServiceContract {

    public final String SWITCHING_DONE;
    public final String EVENT_SWITCHING_DONE;

    public final String REPEATS;
    public final String INTENT_REPEATS;
    public final String STATUS_REPEATS;

    public final String SWITCH_SOCKET_A;
    public final String INTENT_SWITCH_SOCKET_A;

    public final String SWITCH_SOCKET_B;
    public final String INTENT_SWITCH_SOCKET_B;

    public final String SWITCH_SOCKET_C;
    public final String INTENT_SWITCH_SOCKET_C;

    public final String DIM_SOCKET_B;
    public final String INTENT_DIM_SOCKET_B;

    public RemoteSwitchServiceContract(RemoteSwitchDevice device) {
        this(device.getUid(), TinkerforgeDeviceClass.getDevice(device.getDevice()).toString());
    }

    public RemoteSwitchServiceContract(String id) {
        this(id, TinkerforgeDeviceClass.RemoteSwitch.toString());
    }

    public RemoteSwitchServiceContract(String id, String device) {
        super(id, device);

        SWITCHING_DONE = "switchingDone";
        EVENT_SWITCHING_DONE = EVENT + "/" + SWITCHING_DONE;

        REPEATS = "repeats";
        INTENT_REPEATS = INTENT + "/" + REPEATS;
        STATUS_REPEATS = STATUS + "/" + REPEATS;

        SWITCH_SOCKET_A = "switchSocketA";
        INTENT_SWITCH_SOCKET_A = INTENT + "/" + SWITCH_SOCKET_A;
        SWITCH_SOCKET_B = "switchSocketB";
        INTENT_SWITCH_SOCKET_B = INTENT + "/" + SWITCH_SOCKET_B;
        SWITCH_SOCKET_C = "switchSocketC";
        INTENT_SWITCH_SOCKET_C = INTENT + "/" + SWITCH_SOCKET_C;
        DIM_SOCKET_B = "dimSocketB";
        INTENT_DIM_SOCKET_B = INTENT + "/" + DIM_SOCKET_B;

    }

    @Override
    protected void descirbeMore(Map<String, String> descriptions) {

        descriptions.put(INTENT_REPEATS, "[0.." + Short.MAX_VALUE + "]");

        descriptions.put(INTENT_SWITCH_SOCKET_A, "houseCode: [0..31]\n receiverCode: [0..31]\n switchingValue: [switchOn|switchOff]");
        descriptions.put(INTENT_SWITCH_SOCKET_B, "address: [0..67108863]\n unit: [0..15]\n switchingValue: [switchOn|switchOff]");
        descriptions.put(INTENT_SWITCH_SOCKET_C, "systemCode: ['A'..'P']\n deviceCode: [1..16]\n switchingValue: [switchOn|switchOff]");
        descriptions.put(INTENT_DIM_SOCKET_B, "address: [0..67108863]\n unit: [0..15]\n dimValue: [0..15]");

        descriptions.put(EVENT_SWITCHING_DONE, "- timestamp: [0.." + Long.MAX_VALUE + "]\n  value:\n    [houseCode: [0..31]\n    receiverCode:    [0..31]\n    switchingValue: [ON|OFF] | address: [0..67108863]\n    unit: [0..15]\n    switchingValue: [ON|OFF] | systemCode: ['A'..'P']\n    deviceCode: [1..16]\n    switchingValue: [ON|OFF] | address:    [0..67108863]\n    unit: [0..15]\n    dimValue: [0..15]]");
        descriptions.put(STATUS_REPEATS, "[0.." + Short.MAX_VALUE + "]");
    }
}
