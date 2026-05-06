package com.remotecontrol.commands;

public class SetBrightnessCommand extends BaseCommand {

    private final int brightness;

    public SetBrightnessCommand(String targetDevice, int brightness) {
        super(targetDevice, "SET_BRIGHTNESS", String.valueOf(brightness));
        if (brightness < 0 || brightness > 100) {
            throw new IllegalArgumentException("Brilho deve estar entre 0 e 100.");
        }
        this.brightness = brightness;
    }

    public int getBrightness() { return brightness; }
}
