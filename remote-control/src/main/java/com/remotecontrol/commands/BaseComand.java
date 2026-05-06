package com.remotecontrol.commands;

public abstract class BaseCommand implements Command {

    private final String targetDevice;
    private final String action;
    private final String payload;

    protected BaseCommand(String targetDevice, String action, String payload) {
        if (targetDevice == null || targetDevice.isBlank()) {
            throw new IllegalArgumentException("targetDevice não pode ser nulo ou vazio.");
        }
        if (action == null || action.isBlank()) {
            throw new IllegalArgumentException("action não pode ser nula ou vazia.");
        }
        this.targetDevice = targetDevice;
        this.action = action;
        this.payload = payload != null ? payload : "";
    }

    @Override
    public String getTargetDevice() { return targetDevice; }

    @Override
    public String getAction() { return action; }

    @Override
    public String getPayload() { return payload; }

    @Override
    public String toString() {
        return String.format("Command{target='%s', action='%s', payload='%s'}", targetDevice, action, payload);
    }
}
