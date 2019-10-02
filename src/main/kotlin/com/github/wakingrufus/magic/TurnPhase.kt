package com.github.wakingrufus.magic

enum class TurnPhase {
    START, UNTAP, UPKEEP, DRAW, FIRST_MAIN, COMBAT, SECOND_MAIN, END, DRAW_HAND, MULLIGAN
}


data class Turn(val phases: List<TurnPhase>)

val StartGameTurn = Turn(listOf(TurnPhase.DRAW_HAND, TurnPhase.MULLIGAN))
val NormalTurn = Turn(listOf(TurnPhase.START, TurnPhase.UNTAP, TurnPhase.UPKEEP, TurnPhase.DRAW, TurnPhase.FIRST_MAIN, TurnPhase.COMBAT, TurnPhase.SECOND_MAIN, TurnPhase.END))