package com.remotecontrol.commands;

public class TurnOffCommand extends BaseCommand {
    public TurnOffCommand(String targetDevice) {
        super(targetDevice, "TURN_OFF", "");
    }
}
