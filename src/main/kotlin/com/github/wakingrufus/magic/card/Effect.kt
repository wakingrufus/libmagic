package com.github.wakingrufus.magic.card

import com.github.wakingrufus.magic.Mana
import com.github.wakingrufus.magic.Player
import com.github.wakingrufus.magic.state.GameState
import com.github.wakingrufus.magic.state.gameStateLogger

class Effect(val passPriority: Boolean = true,
             val resolve: (Player, GameState) -> GameState)

fun addMana(mana: Mana): Effect {
    return Effect(false) { player, gameState ->
        gameStateLogger.debug { "adding 1 $mana to mana pool for player ${player.name}" }
        gameState.addMana(player, mana)
    }
}

fun addMana(mana: List<Mana>): Effect {
    return Effect(false) { player, gameState ->
        gameStateLogger.debug { "adding ${mana.joinToString("")} to mana pool for player ${player.name}" }
        mana.fold(gameState) { gs, m ->
            gs.addMana(player, m)
        }
    }
}

fun putIntoPlay(permanent: Permanent): Effect {
    return Effect(false) { player, gameState ->
        gameState.putIntoPlay(player, player, permanent, null, false)
    }
}