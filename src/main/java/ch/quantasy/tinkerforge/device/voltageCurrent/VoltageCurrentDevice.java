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
package ch.quantasy.tinkerforge.device.voltageCurrent;

import ch.quantasy.tinkerforge.device.generic.GenericDevice;
import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import com.tinkerforge.BrickletVoltageCurrent;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class VoltageCurrentDevice extends GenericDevice<BrickletVoltageCurrent, VoltageCurrentDeviceCallback> {

    private DeviceConfiguration configuration;
    private Long currentCallbackPeriod;
    private Long debouncePeriod;
    private DeviceVoltageCurrentCallbackThreshold voltageThreshold;
    private Long voltageCallbackPeriod;
    private Long powerCallbackPeriod;
    private DeviceVoltageCurrentCallbackThreshold powerThreshold;
    private DeviceVoltageCurrentCallbackThreshold currentThreshold;
    private DeviceCalibration calibration;

    public VoltageCurrentDevice(TinkerforgeStack stack, BrickletVoltageCurrent device) throws NotConnectedException, TimeoutException {
        super(stack, device);
    }

    @Override
    protected void addDeviceListeners(BrickletVoltageCurrent device) {
        device.addCurrentListener(super.getCallback());
        device.addCurrentReachedListener(super.getCallback());
        device.addPowerListener(super.getCallback());
        device.addPowerReachedListener(super.getCallback());
        device.addVoltageListener(super.getCallback());
        device.addVoltageReachedListener(super.getCallback());
        if (calibration != null) {
            setCalibration(calibration);
        }
        if (configuration != null) {
            setConfiguration(configuration);
        }
       
        if (debouncePeriod != null) {
            setDebouncePeriod(debouncePeriod);
        }
         if (currentCallbackPeriod != null) {
            setCurrentCallbackPeriod(this.currentCallbackPeriod);
        }
        if (currentThreshold != null) {
            setCurrentCallbackThreshold(currentThreshold);
        }
        if (voltageCallbackPeriod != null) {
            setVoltageCallbackPeriod(this.voltageCallbackPeriod);
        }
        if (voltageThreshold != null) {
            setVoltageCallbackThreshold(voltageThreshold);
        }
        if (powerCallbackPeriod != null) {
            setPowerCallbackPeriod(this.powerCallbackPeriod);
        }
        if (powerThreshold != null) {
            setPowerCallbackThreshold(powerThreshold);
        }
       

    }

    @Override
    protected void removeDeviceListeners(BrickletVoltageCurrent device) {
        device.removeCurrentListener(super.getCallback());
        device.removeCurrentReachedListener(super.getCallback());
        device.removePowerListener(super.getCallback());
        device.removePowerReachedListener(super.getCallback());
        device.removeVoltageListener(super.getCallback());
        device.removeVoltageReachedListener(super.getCallback());
    }

    public void setDebouncePeriod(Long period) {
        try {
            getDevice().setDebouncePeriod(period);
            this.debouncePeriod = getDevice().getDebouncePeriod();
            super.getCallback().debouncePeriodChanged(this.debouncePeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCurrentCallbackPeriod(Long period) {
        try {
            getDevice().setCurrentCallbackPeriod(period);
            this.currentCallbackPeriod = getDevice().getCurrentCallbackPeriod();
            super.getCallback().currentCallbackPeriodChanged(this.currentCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCurrentCallbackThreshold(DeviceVoltageCurrentCallbackThreshold threshold) {
        try {
            getDevice().setCurrentCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.currentThreshold = new DeviceVoltageCurrentCallbackThreshold(getDevice().getCurrentCallbackThreshold());
            super.getCallback().currentCallbackThresholdChanged(this.currentThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setVoltageCallbackPeriod(Long period) {
        try {
            getDevice().setVoltageCallbackPeriod(period);
            this.voltageCallbackPeriod = getDevice().getCurrentCallbackPeriod();
            super.getCallback().voltageCallbackPeriodChanged(this.voltageCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setVoltageCallbackThreshold(DeviceVoltageCurrentCallbackThreshold threshold) {
        try {
            getDevice().setVoltageCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.voltageThreshold = new DeviceVoltageCurrentCallbackThreshold(getDevice().getVoltageCallbackThreshold());
            super.getCallback().voltageCallbackThresholdChanged(this.voltageThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPowerCallbackPeriod(Long period) {
        try {
            getDevice().setPowerCallbackPeriod(period);
            this.powerCallbackPeriod = getDevice().getPowerCallbackPeriod();
            super.getCallback().powerCallbackPeriodChanged(this.powerCallbackPeriod);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPowerCallbackThreshold(DeviceVoltageCurrentCallbackThreshold threshold) {
        try {
            getDevice().setPowerCallbackThreshold(threshold.getOption(), threshold.getMin(), threshold.getMax());
            this.powerThreshold = new DeviceVoltageCurrentCallbackThreshold(getDevice().getPowerCallbackThreshold());
            super.getCallback().powerCallbackThresholdChanged(this.powerThreshold);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setConfiguration(DeviceConfiguration configuration) {
        try {
            getDevice().setConfiguration(configuration.getAveraging().getValue(), configuration.getVoltageConversionTime().getValue(), configuration.getCurrentConversionTime().getValue());
            this.configuration = new DeviceConfiguration(getDevice().getConfiguration());
            super.getCallback().configurationChanged(this.configuration);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCalibration(DeviceCalibration calibration) {
        try {
            getDevice().setCalibration(calibration.getGainMultiplier(), calibration.getGainDivisor());
            this.calibration = new DeviceCalibration(getDevice().getCalibration());
            super.getCallback().calibrationChanged(this.calibration);
        } catch (TimeoutException | NotConnectedException ex) {
            Logger.getLogger(VoltageCurrentDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
}
