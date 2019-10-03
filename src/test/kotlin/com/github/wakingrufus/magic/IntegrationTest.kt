package com.github.wakingrufus.magic

import com.github.wakingrufus.magic.card.CardType
import com.github.wakingrufus.magic.sets.Core2020
import com.github.wakingrufus.magic.state.GameState
import mu.KLogging
import org.junit.jupiter.api.Test

internal class IntegrationTest {
    companion object : KLogging()

    @Test
    fun test() {
        val player1 = Player("player 1")
        val player2 = Player("player 2")
        val deck1 = deck(standard) {
            +4 x Core2020.GoblinAssailant
            +23 x Core2020.Mountain
        }
        val startingGameState = GameState(listOf(player1 to deck1, player2 to deck1))
        val startGame = startingGameState.startGame()
        logger.info { "starting state: $startGame" }
        val p1t1 = startGame.applyToPlayer(startGame.priority) { it.draw(7) }
                .also { logger.info { "player 1 starting hand: ${it.playerStateMap[it.priority]?.hand}" } }
                .let {
                    it.playerStateMap[startGame.priority]?.hand?.first { it.card.types.contains(CardType.LAND) }
                            ?.let { land -> startGame.cast(startGame.priority, land) }
                }
                .also { logger.info { it } }
                ?.let { it.resolve() } ?: startGame
        logger.info { p1t1 }
        val afterP1T1 = p1t1.passPriority()
        val p2t1 = afterP1T1.applyToPlayer(startGame.priority) { it.draw(7) }
                .also { logger.info { it } }
                .let {
                    it.playerStateMap[startGame.priority]?.hand?.first { it.card.types.contains(CardType.LAND) }
                            ?.let { land -> startGame.cast(startGame.priority, land) }
                }
                .also { logger.info { it } }
                ?.let { it.resolve() } ?: startGame
        logger.info { p2t1 }
        //   gameState.action(gameState.players[0].first, StartGame())
    }
}