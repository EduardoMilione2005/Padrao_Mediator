package com.remotecontrol;

import com.remotecontrol.commands.*;
import com.remotecontrol.devices.*;
import com.remotecontrol.mediator.SmartHomeMediator;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RemoteControl — Testes de Integração Multi-Dispositivo")
class RemoteControlIntegrationTest {

    private SmartHomeMediator mediator;
    private RemoteControl remote;
    private Television tv;
    private SoundSystem som;
    private SmartLight luz;

    @BeforeEach
    void setUp() {
        mediator = new SmartHomeMediator();
        remote   = new RemoteControl("Controle");
        tv       = new Television("TV");
        som      = new SoundSystem("SOM");
        luz      = new SmartLight("LUZ");

        mediator.registerDevice("Controle", remote);
        mediator.registerDevice("TV", tv);
        mediator.registerDevice("SOM", som);
        mediator.registerDevice("LUZ", luz);
    }

    @Test
    @DisplayName("controle sem mediador deve lançar IllegalStateException")
    void controleSemMediadorDeveLancarExcecao() {
        RemoteControl semMediator = new RemoteControl("Órfão");
        assertThrows(IllegalStateException.class,
                () -> semMediator.sendCommand(new TurnOnCommand("TV")));
    }

    @Test
    @DisplayName("comando deve afetar apenas o dispositivo-alvo")
    void comandoDeveAfenarSomenteAlvo() {
        remote.sendCommand(new TurnOnCommand("TV"));

        assertTrue(tv.isOn());
        assertFalse(som.isOn());
        assertFalse(luz.isOn());
    }

    @Test
    @DisplayName("cenário completo: ligar home theater e ajustar configurações")
    void cenarioHomeTheater() {
        remote.sendCommand(new TurnOnCommand("TV"));
        remote.sendCommand(new TurnOnCommand("SOM"));
        remote.sendCommand(new TurnOnCommand("LUZ"));
        remote.sendCommand(new SetVolumeCommand("SOM", 60));
        remote.sendCommand(new SetChannelCommand("TV", 13));
        remote.sendCommand(new SetBrightnessCommand("LUZ", 30));

        assertTrue(tv.isOn());
        assertTrue(som.isOn());
        assertTrue(luz.isOn());
        assertEquals(60, som.getVolume());
        assertEquals(13, tv.getChannel());
        assertEquals(30, luz.getBrightness());
    }

    @Test
    @DisplayName("cenário: desligar todos os dispositivos")
    void cenarioDesligarTudo() {
        remote.sendCommand(new TurnOnCommand("TV"));
        remote.sendCommand(new TurnOnCommand("SOM"));
        remote.sendCommand(new TurnOnCommand("LUZ"));

        remote.sendCommand(new TurnOffCommand("TV"));
        remote.sendCommand(new TurnOffCommand("SOM"));
        remote.sendCommand(new TurnOffCommand("LUZ"));

        assertFalse(tv.isOn());
        assertFalse(som.isOn());
        assertFalse(luz.isOn());
    }

    @Test
    @DisplayName("múltiplos ajustes de volume em sequência devem manter o último valor")
    void multiplosAjustesDeVolumeDevemManterUltimo() {
        remote.sendCommand(new SetVolumeCommand("TV", 10));
        remote.sendCommand(new SetVolumeCommand("TV", 50));
        remote.sendCommand(new SetVolumeCommand("TV", 90));

        assertEquals(90, tv.getVolume());
    }

    @Test
    @DisplayName("log do mediador deve conter entradas para cada comando enviado")
    void logDeveConterEntradasParaCadaComando() {
        remote.sendCommand(new TurnOnCommand("TV"));
        remote.sendCommand(new SetVolumeCommand("SOM", 40));

        assertEquals(2, mediator.getLog().size());
    }

    @Test
    @DisplayName("comando para dispositivo inexistente não deve afetar outros dispositivos")
    void comandoParaInexistenteNaoDeveAfetar() {
        remote.sendCommand(new TurnOnCommand("PROJETOR"));

        assertFalse(tv.isOn());
        assertFalse(som.isOn());
        assertFalse(luz.isOn());
    }

    @Test
    @DisplayName("dois controles remotos independentes devem funcionar via mesmo mediador")
    void doisControlesDevemFuncionar() {
        RemoteControl remote2 = new RemoteControl("Controle2");
        mediator.registerDevice("Controle2", remote2);

        remote.sendCommand(new TurnOnCommand("TV"));
        remote2.sendCommand(new TurnOnCommand("SOM"));

        assertTrue(tv.isOn());
        assertTrue(som.isOn());
    }
}
