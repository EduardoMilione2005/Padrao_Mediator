package com.remotecontrol;

import com.remotecontrol.commands.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Commands — Testes Unitários de Validação")
class CommandTest {

    @Test
    @DisplayName("TurnOnCommand deve ter action TURN_ON e target correto")
    void turnOnCommandDeveEstarCorreto() {
        TurnOnCommand cmd = new TurnOnCommand("TV");
        assertEquals("TV", cmd.getTargetDevice());
        assertEquals("TURN_ON", cmd.getAction());
        assertEquals("", cmd.getPayload());
    }

    @Test
    @DisplayName("TurnOffCommand deve ter action TURN_OFF")
    void turnOffCommandDeveEstarCorreto() {
        TurnOffCommand cmd = new TurnOffCommand("SOM");
        assertEquals("SOM", cmd.getTargetDevice());
        assertEquals("TURN_OFF", cmd.getAction());
    }

    @Test
    @DisplayName("SetVolumeCommand deve armazenar volume corretamente")
    void setVolumeCommandDeveArmazenarVolume() {
        SetVolumeCommand cmd = new SetVolumeCommand("TV", 80);
        assertEquals(80, cmd.getVolume());
        assertEquals("80", cmd.getPayload());
        assertEquals("SET_VOLUME", cmd.getAction());
    }

    @Test
    @DisplayName("SetVolumeCommand com volume 0 deve ser válido")
    void volumeZeroDeveSerValido() {
        assertDoesNotThrow(() -> new SetVolumeCommand("TV", 0));
    }

    @Test
    @DisplayName("SetVolumeCommand com volume 100 deve ser válido")
    void volumeMaximoDeveSerValido() {
        assertDoesNotThrow(() -> new SetVolumeCommand("TV", 100));
    }

    @Test
    @DisplayName("SetChannelCommand deve armazenar canal corretamente")
    void setChannelCommandDeveArmazenarCanal() {
        SetChannelCommand cmd = new SetChannelCommand("TV", 7);
        assertEquals(7, cmd.getChannel());
        assertEquals("7", cmd.getPayload());
        assertEquals("SET_CHANNEL", cmd.getAction());
    }

    @Test
    @DisplayName("SetBrightnessCommand deve armazenar brilho corretamente")
    void setBrightnessCommandDeveArmazenarBrilho() {
        SetBrightnessCommand cmd = new SetBrightnessCommand("LUZ", 55);
        assertEquals(55, cmd.getBrightness());
        assertEquals("55", cmd.getPayload());
        assertEquals("SET_BRIGHTNESS", cmd.getAction());
    }

    @Test
    @DisplayName("BaseCommand deve lançar exceção para target nulo")
    void baseCommandTargetNuloDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> new TurnOnCommand(null));
    }

    @Test
    @DisplayName("BaseCommand deve lançar exceção para target vazio")
    void baseCommandTargetVazioDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> new TurnOnCommand("  "));
    }

    @Test
    @DisplayName("toString de BaseCommand deve conter target, action e payload")
    void toStringDeveConterCampos() {
        SetVolumeCommand cmd = new SetVolumeCommand("TV", 42);
        String str = cmd.toString();
        assertTrue(str.contains("TV"));
        assertTrue(str.contains("SET_VOLUME"));
        assertTrue(str.contains("42"));
    }
}
