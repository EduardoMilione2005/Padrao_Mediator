package com.remotecontrol.commands;

public class SetChannelCommand extends BaseCommand {

    private final int channel;

    public SetChannelCommand(String targetDevice, int channel) {
        super(targetDevice, "SET_CHANNEL", String.valueOf(channel));
        if (channel < 1) {
            throw new IllegalArgumentException("Canal deve ser maior que zero.");
        }
        this.channel = channel;
    }

    public int getChannel() { return channel; }
}
