package com.remotecontrol.commands;

public class TurnOnCommand extends BaseCommand {
    public TurnOnCommand(String targetDevice) {
        super(targetDevice, "TURN_ON", "");
    }
}
