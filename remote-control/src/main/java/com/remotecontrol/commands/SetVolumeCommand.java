package com.remotecontrol.commands;

public class SetVolumeCommand extends BaseCommand {

    private final int volume;

    public SetVolumeCommand(String targetDevice, int volume) {
        super(targetDevice, "SET_VOLUME", String.valueOf(volume));
        if (volume < 0 || volume > 100) {
            throw new IllegalArgumentException("Volume deve estar entre 0 e 100.");
        }
        this.volume = volume;
    }

    public int getVolume() { return volume; }
}
