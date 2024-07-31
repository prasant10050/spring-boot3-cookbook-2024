package com.packt.football;

import com.packt.football.exceptions.AlreadyExistsException;
import com.packt.football.exceptions.NotFoundException;
import com.packt.football.model.Player;
import com.packt.football.services.FootballService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FootballServiceTests {

    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public FootballService footballService() {
            return new FootballService();
        }
    }

    @Autowired
    private FootballService footballService;

    @Test
    public void testListPlayers() {
        List<Player> players = footballService.listPlayers();
        assertFalse(players.isEmpty());
    }

    @Test
    public void testGetPlayer_exist() {
        Player player = footballService.getPlayer("1884823");
        assertNotNull(player);
    }

    @Test
    public void testGetPlayer_notExist() {
        assertThrows(NotFoundException.class, () -> footballService.getPlayer("9999999"));
    }
    @Test
    public void testAddPlayer_ok() {
        Player player = new Player("888888", 99, "Test Player", "Test Position", null);
        footballService.addPlayer(player);
        Player player1 = footballService.getPlayer(player.id());
        assertNotNull(player1);
        assertEquals(player,player1);
    }
    @Test
    public void testAddPlayer_duplicate() {
        Player player = new Player("777777", 99, "Test Player", "Test Position", null);
        footballService.addPlayer(player);
        Player player1 = footballService.getPlayer(player.id());
        assertNotNull(player1);
        assertEquals(player,player1);
        assertThrows(AlreadyExistsException.class,()-> footballService.addPlayer(player));
    }
    @Test
    public void testUpdatePlayer_exists() {
        Player player = new Player("666666", 99, "Test Player", "Test Position", null);
        footballService.addPlayer(player);
        Player player1 = footballService.getPlayer(player.id());
        assertNotNull(player1);
        assertEquals(player,player1);
        // ACT
        Player player2 = new Player("666666", 99, "Test Player 2", "Test Position 2", null);
        footballService.updatePlayer(player2);
        Player player3 = footballService.getPlayer(player2.id());
        assertNotNull(player3);
        assertEquals(player2,player3);

    }
    @Test
    public void testUpdatePlayer_notExists() {
        // ACT & ASSERT
        Player player = new Player("55555555", 99, "Test Player 2", "Test Position 2", null);
        assertThrows(NotFoundException.class, () -> footballService.updatePlayer(player));

    }
}
