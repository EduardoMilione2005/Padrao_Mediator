package com.remotecontrol.commands;

public interface Command {

    String getTargetDevice();

    String getAction();

    String getPayload();

    @Override
    String toString();
}
