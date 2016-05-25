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
package ch.quantasy.tinkerforge.device.accelerometer;

import com.tinkerforge.BrickletAccelerometer;

/**
 *
 * @author reto
 */
public class DeviceConfiguration {

    public static enum DataRate {
        OFF((short) 0), Hz3((short) 1), Hz6((short) 2), Hz12((short) 3), Hz25((short) 4), Hz50((short) 5), Hz100((short) 6), Hz400((short) 7), Hz800((short) 8), Hz1600((short) 9);
        private short value;

        private DataRate(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static DataRate getDataRateFor(short s) throws IllegalArgumentException {
            for (DataRate range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            throw new IllegalArgumentException("Not supported: " + s);
        }
    }

    public static enum FullScale {
        G2((short) 0), G4((short) 1), G6((short) 2), G8((short) 3), G16((short) 4);
        private short value;

        private FullScale(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static FullScale getFullScaleFor(short s) throws IllegalArgumentException {
            for (FullScale range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    public static enum FilterBandwidth {
        Hz800((short) 0), Hz400((short) 1), Hz200((short) 2), Hz50((short) 3);
        private short value;

        private FilterBandwidth(short value) {
            this.value = value;
        }

        public short getValue() {
            return value;
        }

        public static FilterBandwidth getFilterBandwidthFor(short s) throws IllegalArgumentException {
            for (FilterBandwidth range : values()) {
                if (range.value == s) {
                    return range;
                }
            }
            throw new IllegalArgumentException();
        }
    }
    private DataRate dataRate;
    private FullScale fullScale;
    private FilterBandwidth filterBandwidth;

    public DeviceConfiguration() {
    }

    public DeviceConfiguration(String illuminanceRange, String integrationTime, String filterBandwidth){
        this(DataRate.valueOf(illuminanceRange),FullScale.valueOf(integrationTime),FilterBandwidth.valueOf(filterBandwidth));
    }
    public DeviceConfiguration(DataRate illuminanceRange, FullScale integrationTime, FilterBandwidth filterBandwidth) {
        this.dataRate = illuminanceRange;
        this.fullScale = integrationTime;
        this.filterBandwidth = filterBandwidth;
    }

    public DeviceConfiguration(short dataRate, short fullScale, short filterBandwidth) throws IllegalArgumentException {
        this(DataRate.getDataRateFor(dataRate), FullScale.getFullScaleFor(fullScale), FilterBandwidth.getFilterBandwidthFor(filterBandwidth));
    }

    public DeviceConfiguration(BrickletAccelerometer.Configuration configuration) {
        this(configuration.dataRate, configuration.fullScale, configuration.filterBandwidth);
    }

    public DataRate getDataRate() {
        return dataRate;
    }

    public FullScale getFullScale() {
        return fullScale;
    }

    public FilterBandwidth getFilterBandwidth() {
        return filterBandwidth;
    }

}