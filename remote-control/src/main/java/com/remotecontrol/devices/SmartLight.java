package com.remotecontrol.devices;

import com.remotecontrol.commands.Command;
import com.remotecontrol.commands.SetBrightnessCommand;
import com.remotecontrol.mediator.Colleague;
import com.remotecontrol.mediator.RemoteControlMediator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmartLight implements Colleague {

    private final String name;
    private RemoteControlMediator mediator;

    private boolean on = false;
    private int brightness = 100;
    private final List<String> receivedCommands = new ArrayList<>();

    public SmartLight(String name) {
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
            case "TURN_ON"        -> on = true;
            case "TURN_OFF"       -> on = false;
            case "SET_BRIGHTNESS" -> { if (command instanceof SetBrightnessCommand c) brightness = c.getBrightness(); }
            default               -> { }
        }
    }

    public boolean isOn()         { return on; }
    public int getBrightness()    { return brightness; }
    public List<String> getReceivedCommands() { return Collections.unmodifiableList(receivedCommands); }
}
