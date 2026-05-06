package com.remotecontrol.mediator;

import com.remotecontrol.commands.Command;

import java.util.HashMap;
import java.util.Map;

public class SmartHomeMediator implements RemoteControlMediator {

    private final Map<String, Colleague> devices = new HashMap<>();
    private final java.util.List<String> log = new java.util.ArrayList<>();

    @Override
    public void registerDevice(String name, Colleague device) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome do dispositivo não pode ser nulo ou vazio.");
        }
        if (device == null) {
            throw new IllegalArgumentException("O dispositivo não pode ser nulo.");
        }
        devices.put(name, device);
        device.setMediator(this);
    }

    @Override
    public void send(Command command, Colleague sender) {
        if (command == null) {
            throw new IllegalArgumentException("Comando não pode ser nulo.");
        }

        String entry = String.format("[%s] -> %s", sender != null ? sender.getName() : "SISTEMA", command);
        log.add(entry);

        String target = command.getTargetDevice();
        Colleague recipient = devices.get(target);

        if (recipient == null) {
            log.add("ERRO: dispositivo '" + target + "' não encontrado.");
            return;
        }

        if (sender != null && recipient.getName().equals(sender.getName())) {
            log.add("AVISO: dispositivo tentou enviar comando para si mesmo.");
            return;
        }

        recipient.receive(command);
    }

    @Override
    public boolean hasDevice(String name) {
        return devices.containsKey(name);
    }

    public java.util.List<String> getLog() {
        return java.util.Collections.unmodifiableList(log);
    }

    public void clearLog() {
        log.clear();
    }
}
