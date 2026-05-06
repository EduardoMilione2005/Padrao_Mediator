package com.remotecontrol.devices;

import com.remotecontrol.commands.Command;
import com.remotecontrol.commands.SetChannelCommand;
import com.remotecontrol.commands.SetVolumeCommand;
import com.remotecontrol.mediator.Colleague;
import com.remotecontrol.mediator.RemoteControlMediator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Television implements Colleague {

    private final String name;
    private RemoteControlMediator mediator;

    private boolean on = false;
    private int volume = 20;
    private int channel = 1;
    private final List<String> receivedCommands = new ArrayList<>();

    public Television(String name) {
        this.name = name;
    }

    @Override
    public String getName() { return name; }

    @Override
    public void setMediator(RemoteControlMediator mediator) { this.mediator = mediator; }

    @Override
    public void receive(Command command) {
        receivedCommands.add(command.getAction());
        switch (command.getAction()) {
            case "TURN_ON"      -> on = true;
            case "TURN_OFF"     -> on = false;
            case "SET_VOLUME"   -> { if (command instanceof SetVolumeCommand c) volume = c.getVolume(); }
            case "SET_CHANNEL"  -> { if (command instanceof SetChannelCommand c) channel = c.getChannel(); }
            default             -> { /* ação não reconhecida — ignorada */ }
        }
    }

    public boolean isOn()       { return on; }
    public int getVolume()      { return volume; }
    public int getChannel()     { return channel; }
    public List<String> getReceivedCommands() { return Collections.unmodifiableList(receivedCommands); }
}
