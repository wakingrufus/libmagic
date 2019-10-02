package com.github.wakingrufus.magic

import com.github.wakingrufus.magic.card.Effect
import com.github.wakingrufus.magic.state.GameState
import com.github.wakingrufus.magic.state.StackEffect

class ActivatedAbility(val cost: List<ManaCost> = emptyList(), val image: String, val ability: Effect) {
    suspend fun activate(player: Player, gameState: GameState): GameState {
        // if(cost.isNotEmpty()) gameState.stack.add()
        return gameState.addToStack(StackEffect(player, null, image, ability))
    }
}

class ActivatedAbilityBuilder(val cost: List<ManaCost> = emptyList(), val ability: Effect) {

    fun build(image: String): ActivatedAbility {
        return ActivatedAbility(cost, image, ability)
    }
}