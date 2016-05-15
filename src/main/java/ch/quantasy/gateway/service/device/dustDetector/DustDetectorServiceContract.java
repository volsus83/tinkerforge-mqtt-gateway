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
package ch.quantasy.gateway.service.device.dustDetector;

import ch.quantasy.gateway.service.device.DeviceServiceContract;
import ch.quantasy.tinkerforge.device.dustDetector.DustDetectorDevice;

/**
 *
 * @author reto
 */
public class DustDetectorServiceContract extends DeviceServiceContract {

    public final String REACHED;
    public final String PERIOD;
    public final String CALLBACK_PERIOD;
    public final String THRESHOLD;

    public final String DUST_DENSITY;
    public final String STATUS_CO2_CONCENTRATION;
    public final String STATUS_DUST_DENSITY_THRESHOLD;
    public final String STATUS_DUST_DENSITY_CALLBACK_PERIOD;
    public final String EVENT_DUST_DENSITY;
    public final String EVENT_DUST_DENSITY_REACHED;
    public final String INTENT_CO2_CONCENTRATION;
    public final String INTENT_DUST_DENSITY_THRESHOLD;
    public final String INTENT_DUST_DENSITY_CALLBACK_PERIOD;

    public final String DEBOUNCE;
    public final String STATUS_DEBOUNCE;
    public final String EVENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE;
    public final String INTENT_DEBOUNCE_PERIOD;
    public final String STATUS_DEBOUNCE_PERIOD;
    
    public final String MOVING_AVERAGE;
    public final String INTENT_MOVING_AVERAGE;
    public final String STATUS_MOVING_AVERAGE;


    public DustDetectorServiceContract(DustDetectorDevice device) {
        super(device);
        PERIOD = "period";
        CALLBACK_PERIOD = "callbackPeriod";
        THRESHOLD = "threshold";

        REACHED = "reached";

        DUST_DENSITY = "dustDensity";
        STATUS_CO2_CONCENTRATION = STATUS + "/" + DUST_DENSITY;
        STATUS_DUST_DENSITY_THRESHOLD = STATUS_CO2_CONCENTRATION + "/" + THRESHOLD;
        STATUS_DUST_DENSITY_CALLBACK_PERIOD = STATUS_CO2_CONCENTRATION + "/" + CALLBACK_PERIOD;
        EVENT_DUST_DENSITY = EVENT + "/" + DUST_DENSITY;
        EVENT_DUST_DENSITY_REACHED = EVENT_DUST_DENSITY + "/" + REACHED;
        INTENT_CO2_CONCENTRATION = INTENT + "/" + DUST_DENSITY;
        INTENT_DUST_DENSITY_THRESHOLD = INTENT_CO2_CONCENTRATION + "/" + THRESHOLD;
        INTENT_DUST_DENSITY_CALLBACK_PERIOD = INTENT_CO2_CONCENTRATION + "/" + CALLBACK_PERIOD;

        DEBOUNCE = "debounce";
        STATUS_DEBOUNCE = STATUS + "/" + DEBOUNCE;
        STATUS_DEBOUNCE_PERIOD = STATUS_DEBOUNCE + "/" + PERIOD;
        EVENT_DEBOUNCE = EVENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE = INTENT + "/" + DEBOUNCE;
        INTENT_DEBOUNCE_PERIOD = INTENT_DEBOUNCE + "/" + PERIOD;
        
        MOVING_AVERAGE = "movingAverage";
        STATUS_MOVING_AVERAGE = STATUS + "/" + MOVING_AVERAGE;
        INTENT_MOVING_AVERAGE = INTENT + "/" + MOVING_AVERAGE;
    }
}