package com.remotecontrol;

import com.remotecontrol.commands.*;
import com.remotecontrol.devices.Television;
import com.remotecontrol.mediator.SmartHomeMediator;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Television — Testes do Dispositivo")
class TelevisionTest {

    private SmartHomeMediator mediator;
    private RemoteControl remote;
    private Television tv;

    @BeforeEach
    void setUp() {
        mediator = new SmartHomeMediator();
        remote   = new RemoteControl("Controle");
        tv       = new Television("TV");

        mediator.registerDevice("Controle", remote);
        mediator.registerDevice("TV", tv);
    }

    @Test
    @DisplayName("TV deve ligar ao receber TurnOnCommand")
    void tvDeveIniciarDesligada() {
        assertFalse(tv.isOn());
        remote.sendCommand(new TurnOnCommand("TV"));
        assertTrue(tv.isOn());
    }

    @Test
    @DisplayName("TV deve desligar ao receber TurnOffCommand")
    void tvDeveDesligar() {
        remote.sendCommand(new TurnOnCommand("TV"));
        remote.sendCommand(new TurnOffCommand("TV"));
        assertFalse(tv.isOn());
    }

    @Test
    @DisplayName("TV deve alterar volume corretamente")
    void tvDeveAlterarVolume() {
        remote.sendCommand(new SetVolumeCommand("TV", 75));
        assertEquals(75, tv.getVolume());
    }

    @Test
    @DisplayName("TV deve alterar canal corretamente")
    void tvDeveAlterarCanal() {
        remote.sendCommand(new SetChannelCommand("TV", 10));
        assertEquals(10, tv.getChannel());
    }

    @Test
    @DisplayName("TV deve registrar todos os comandos recebidos")
    void tvDeveRegistrarComandosRecebidos() {
        remote.sendCommand(new TurnOnCommand("TV"));
        remote.sendCommand(new SetVolumeCommand("TV", 50));
        remote.sendCommand(new SetChannelCommand("TV", 5));

        assertEquals(3, tv.getReceivedCommands().size());
        assertTrue(tv.getReceivedCommands().contains("TURN_ON"));
        assertTrue(tv.getReceivedCommands().contains("SET_VOLUME"));
        assertTrue(tv.getReceivedCommands().contains("SET_CHANNEL"));
    }

    @Test
    @DisplayName("volume inválido deve lançar IllegalArgumentException")
    void volumeInvalidoDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> new SetVolumeCommand("TV", 101));
        assertThrows(IllegalArgumentException.class,
                () -> new SetVolumeCommand("TV", -1));
    }

    @Test
    @DisplayName("canal inválido deve lançar IllegalArgumentException")
    void canalInvalidoDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> new SetChannelCommand("TV", 0));
    }

    @Test
    @DisplayName("TV deve manter volume padrão sem SetVolumeCommand")
    void tvDeveManterVolumeDefault() {
        assertEquals(20, tv.getVolume());
    }
}
