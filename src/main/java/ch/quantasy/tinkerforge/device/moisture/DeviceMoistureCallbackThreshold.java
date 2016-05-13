/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerforge.device.moisture;

import com.tinkerforge.BrickletMoisture;

/**
 *
 * @author reto
 */
public class DeviceMoistureCallbackThreshold {

    private char option;
    private int min;
    private int max;

    public DeviceMoistureCallbackThreshold() {
    }

    public DeviceMoistureCallbackThreshold(BrickletMoisture.MoistureCallbackThreshold threshold) {
        this(threshold.option, threshold.min, threshold.max);
    }

    public DeviceMoistureCallbackThreshold(char option, int min, int max) {
        this.option = option;
        this.min = min;
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public char getOption() {
        return option;
    }

}