package com.remotecontrol.mediator;

import com.remotecontrol.commands.Command;

public interface Colleague {

    String getName();

    void setMediator(RemoteControlMediator mediator);

    void receive(Command command);
}
