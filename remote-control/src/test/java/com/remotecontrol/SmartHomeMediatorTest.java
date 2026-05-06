package com.remotecontrol;

import com.remotecontrol.commands.*;
import com.remotecontrol.devices.*;
import com.remotecontrol.mediator.SmartHomeMediator;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SmartHomeMediator — Testes do Mediador")
class SmartHomeMediatorTest {

    private SmartHomeMediator mediator;

    @BeforeEach
    void setUp() {
        mediator = new SmartHomeMediator();
    }


    @Test
    @DisplayName("deve registrar dispositivo com sucesso")
    void deveRegistrarDispositivo() {
        Television tv = new Television("TV");
        mediator.registerDevice("TV", tv);
        assertTrue(mediator.hasDevice("TV"));
    }

    @Test
    @DisplayName("deve lançar exceção ao registrar dispositivo nulo")
    void deveLancarExcecaoParaDispositivoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> mediator.registerDevice("TV", null));
    }

    @Test
    @DisplayName("deve lançar exceção ao registrar nome vazio")
    void deveLancarExcecaoParaNomeVazio() {
        Television tv = new Television("TV");
        assertThrows(IllegalArgumentException.class,
                () -> mediator.registerDevice("  ", tv));
    }

    @Test
    @DisplayName("hasDevice deve retornar false para dispositivo não registrado")
    void hasDeviceDeveRetornarFalseParaNaoRegistrado() {
        assertFalse(mediator.hasDevice("INEXISTENTE"));
    }

    @Test
    @DisplayName("mediador deve rotear comando para dispositivo correto")
    void deverodarComandoParaDispositivoCorreto() {
        Television tv  = new Television("TV");
        SoundSystem som = new SoundSystem("SOM");
        mediator.registerDevice("TV", tv);
        mediator.registerDevice("SOM", som);

        mediator.send(new TurnOnCommand("TV"), null);

        assertTrue(tv.isOn());
        assertFalse(som.isOn());
    }

    @Test
    @DisplayName("deve registrar log quando dispositivo-alvo não existe")
    void deveRegistrarLogParaDispositivoInexistente() {
        Television tv = new Television("TV");
        mediator.registerDevice("TV", tv);

        mediator.send(new TurnOnCommand("INEXISTENTE"), null);

        assertTrue(mediator.getLog().stream().anyMatch(l -> l.contains("não encontrado")));
    }

    @Test
    @DisplayName("deve lançar exceção ao enviar comando nulo")
    void deveLancarExcecaoComandoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> mediator.send(null, null));
    }

    @Test
    @DisplayName("deve ignorar quando remetente tenta enviar para si mesmo")
    void deveIgnorarAutoEnvio() {
        Television tv = new Television("TV");
        mediator.registerDevice("TV", tv);

        mediator.send(new TurnOnCommand("TV"), tv);

        assertFalse(tv.isOn());
        assertTrue(mediator.getLog().stream().anyMatch(l -> l.contains("AVISO")));
    }

    @Test
    @DisplayName("log deve ser limpo após clearLog")
    void deveContarLogAposClear() {
        Television tv = new Television("TV");
        mediator.registerDevice("TV", tv);
        mediator.send(new TurnOnCommand("TV"), null);

        mediator.clearLog();

        assertTrue(mediator.getLog().isEmpty());
    }
}
