package com.remotecontrol.mediator;

import com.remotecontrol.commands.Command;

public interface RemoteControlMediator {

    void registerDevice(String name, Colleague device);

    void send(Command command, Colleague sender);

    boolean hasDevice(String name);
}
