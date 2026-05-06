package com.remotecontrol;

import com.remotecontrol.commands.Command;
import com.remotecontrol.mediator.Colleague;
import com.remotecontrol.mediator.RemoteControlMediator;

public class RemoteControl implements Colleague {

    private RemoteControlMediator mediator;
    private final String name;

    public RemoteControl(String name) {
        this.name = name;
    }

    @Override
    public String getName() { return name; }

    @Override
    public void setMediator(RemoteControlMediator mediator) { this.mediator = mediator; }

    public void sendCommand(Command command) {
        if (mediator == null) {
            throw new IllegalStateException("RemoteControl não está vinculado a nenhum mediador.");
        }
        mediator.send(command, this);
    }

    @Override
    public void receive(Command command) {

    }
}
