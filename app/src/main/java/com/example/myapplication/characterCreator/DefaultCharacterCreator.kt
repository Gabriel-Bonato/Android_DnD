package com.example.myapplication.characterCreator

import com.example.myapplication.races.Character
import com.example.myapplication.races.Race

class DefaultCharacterCreator : CharacterCreator {
    override fun createCharacter(race: Race): Character {
        val attributes = mutableMapOf(
            "Força" to 8,
            "Destreza" to 8,
            "Constituição" to 8,
            "Inteligência" to 8,
            "Sabedoria" to 8,
            "Carisma" to 8
        )

        val character = Character(attributes = attributes, race = race)

        val points = 27
        character.distributePoints(points)

        println("Pontos distribuidos. Aplique o seu bônus de raça....")
        character.applyRaceBonuses()

        return character
    }
}