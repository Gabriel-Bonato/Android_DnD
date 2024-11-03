package com.example.myapplication.races

import java.util.Locale
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_table")
data class Character(@PrimaryKey(autoGenerate = true) val id: Int = 0, var attributes: MutableMap<String, Int>, val race: Race) {
    fun applyRaceBonuses() {
        race.bonuses.forEach { (attr, bonus) ->
            attributes[attr] = attributes[attr]!! + bonus
        }
    }

    fun distributePoints(points: Int) {
        var remainingPoints = points
        while (remainingPoints > 0) {
            println("Você tem $remainingPoints pontos restantes.")
            println("Atributos atuais:")
            attributes.forEach { (attr, value) ->
                println("$attr: $value")
            }

            println("Informe o nome do atributo que deseja incrementar:")
            val attribute = readLine()?.capitalize(Locale.ROOT) ?: ""
            if (!attributes.containsKey(attribute)) {
                println("Atributo não encontrado.")
                continue
            }

            println("Quantos pontos deseja adicionar ao atributo $attribute?")
            val inputPoints = readLine()?.toIntOrNull()

            if (inputPoints == null || inputPoints <= 0) {
                println("Entrada inválida.")
                continue
            }

            if (remainingPoints >= inputPoints && (attributes[attribute]!! + inputPoints) <= 15) {
                attributes[attribute] = attributes[attribute]!! + inputPoints
                remainingPoints -= inputPoints
            } else {
                println("Pontos insuficientes ou limite (15) atingido.")
            }
        }
    }

    fun calculateHealthPoints(): Int {
        val constitution = attributes["Constituição"] ?: 0
        val constitutionModifier = (constitution - 10) / 2
        return 10 + constitutionModifier
    }
}
