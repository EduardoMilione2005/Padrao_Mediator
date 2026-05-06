package com.remotecontrol;

import com.remotecontrol.commands.*;
import com.remotecontrol.devices.SmartLight;
import com.remotecontrol.devices.SoundSystem;
import com.remotecontrol.mediator.SmartHomeMediator;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SoundSystem e SmartLight — Testes dos Dispositivos")
class SoundSystemAndSmartLightTest {

    private SmartHomeMediator mediator;
    private RemoteControl remote;
    private SoundSystem som;
    private SmartLight luz;

    @BeforeEach
    void setUp() {
        mediator = new SmartHomeMediator();
        remote   = new RemoteControl("Controle");
        som      = new SoundSystem("SOM");
        luz      = new SmartLight("LUZ");

        mediator.registerDevice("Controle", remote);
        mediator.registerDevice("SOM", som);
        mediator.registerDevice("LUZ", luz);
    }

    // ── SoundSystem ──────────────────────────────

    @Test
    @DisplayName("SoundSystem deve ligar corretamente")
    void somDeveLigar() {
        remote.sendCommand(new TurnOnCommand("SOM"));
        assertTrue(som.isOn());
    }

    @Test
    @DisplayName("SoundSystem deve desligar corretamente")
    void somDeveDesligar() {
        remote.sendCommand(new TurnOnCommand("SOM"));
        remote.sendCommand(new TurnOffCommand("SOM"));
        assertFalse(som.isOn());
    }

    @Test
    @DisplayName("SoundSystem deve ajustar volume")
    void somDeveAjustarVolume() {
        remote.sendCommand(new SetVolumeCommand("SOM", 55));
        assertEquals(55, som.getVolume());
    }

    @Test
    @DisplayName("SoundSystem deve ignorar SetChannel (ação desconhecida)")
    void somDeveIgnorarSetChannel() {
        remote.sendCommand(new SetChannelCommand("SOM", 3));
        // ação "SET_CHANNEL" não afeta SoundSystem
        assertEquals(1, som.getReceivedCommands().size());
        assertEquals("SET_CHANNEL", som.getReceivedCommands().get(0));
        assertEquals(30, som.getVolume()); // volume padrão inalterado
    }

    // ── SmartLight ───────────────────────────────

    @Test
    @DisplayName("SmartLight deve ligar corretamente")
    void luzDeveLigar() {
        remote.sendCommand(new TurnOnCommand("LUZ"));
        assertTrue(luz.isOn());
    }

    @Test
    @DisplayName("SmartLight deve desligar corretamente")
    void luzDeveDesligar() {
        remote.sendCommand(new TurnOnCommand("LUZ"));
        remote.sendCommand(new TurnOffCommand("LUZ"));
        assertFalse(luz.isOn());
    }

    @Test
    @DisplayName("SmartLight deve ajustar brilho")
    void luzDeveAjustarBrilho() {
        remote.sendCommand(new SetBrightnessCommand("LUZ", 40));
        assertEquals(40, luz.getBrightness());
    }

    @Test
    @DisplayName("brilho inválido deve lançar IllegalArgumentException")
    void brilhoInvalidoDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> new SetBrightnessCommand("LUZ", -1));
        assertThrows(IllegalArgumentException.class,
                () -> new SetBrightnessCommand("LUZ", 101));
    }

    @Test
    @DisplayName("SmartLight deve ter brilho padrão 100")
    void luzDeveIniciarBrilhoMaximo() {
        assertEquals(100, luz.getBrightness());
    }
}
