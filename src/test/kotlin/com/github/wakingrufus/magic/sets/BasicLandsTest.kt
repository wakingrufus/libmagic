package com.github.wakingrufus.magic.sets

import com.github.wakingrufus.magic.Mana
import com.github.wakingrufus.magic.state.StateCard
import com.github.wakingrufus.magic.state.testGameState
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BasicLandsTest {
    @Test
    fun `test Forest`() = runBlocking<Unit> {
        val gameState = testGameState()
        val forest = BasicLands.Forest
        val player = gameState.players[0].first
        val stateCard = StateCard(owner = player, card = forest)
        val gameStateOnStack = stateCard.cast(player).fold(gameState) { gs, stackEffect -> gs.addToStack(stackEffect) }
        val resolved = gameStateOnStack.resolve()
        val permanentState = resolved.playerStateMap[player]?.battlefield?.get(0)
        assertThat(permanentState).isNotNull
        permanentState?.run {
            assertThat(permanent.activatedAbilities).`as`("has an activated ability").hasSize(1)
            val afterActivation = permanentState.permanent.activatedAbilities[0].activate(player, gameState)
            val addManaResolved = afterActivation.resolve()
            val playerState = addManaResolved.playerStateMap[player]
            assertThat(playerState).`as`("player state found").isNotNull
            playerState?.run {
                assertThat(manaPool.manaMap[Mana.G]).isEqualTo(1)
            }
        }

    }
}