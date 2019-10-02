package com.github.wakingrufus.magic.state

import com.github.wakingrufus.magic.Deck
import com.github.wakingrufus.magic.Player
import com.github.wakingrufus.magic.sets.Core2020.GoblinAssailant
import org.junit.jupiter.api.Test

fun testGameState(): GameState {
    val player1 = Player("player 1")
    val player2 = Player("player 2")
    val deck1 = Deck(listOf(GoblinAssailant))
    return GameState(listOf(player1 to deck1, player2 to deck1))
}

internal class GameStateTest {
    @Test
    fun test() {
        val gameState = testGameState()
        //   gameState.action(gameState.players[0].first, StartGame())
    }
}