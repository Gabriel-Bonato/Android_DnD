package com.example.myapplication.characterCreator

import com.example.myapplication.races.Character
import com.example.myapplication.races.Race

interface CharacterCreator {
    fun createCharacter(race: Race): Character
}