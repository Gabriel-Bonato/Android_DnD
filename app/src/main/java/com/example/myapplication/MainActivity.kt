package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.DAO.CharacterDAO
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.races.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var characterDAO: CharacterDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(this)
        characterDAO = db.characterDAO()

        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CharacterCreationScreen(
                        modifier = Modifier.padding(innerPadding),
                        onSaveCharacter = { character ->
                            saveCharacter(character, characterDAO)
                        },
                        onUpdateCharacter = { character ->
                            updateCharacter(character, characterDAO)
                        },
                        onDeleteCharacter = { character ->
                            deleteCharacter(character, characterDAO)
                        }
                    )
                }
            }
        }
    }
}

private fun saveCharacter(character: Character, characterDAO: CharacterDAO) {
    CoroutineScope(Dispatchers.IO).launch {
        characterDAO.insert(character)
    }
}

private fun updateCharacter(character: Character, characterDAO: CharacterDAO) {
    CoroutineScope(Dispatchers.IO).launch {
        characterDAO.update(character)
    }
}

private fun deleteCharacter(character: Character, characterDAO: CharacterDAO) {
    CoroutineScope(Dispatchers.IO).launch {
        characterDAO.delete(character)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterCreationScreen(
    modifier: Modifier = Modifier,
    onSaveCharacter: (Character) -> Unit = {},
    onUpdateCharacter: (Character) -> Unit = {},
    onDeleteCharacter: (Character) -> Unit = {}
) {
    var strength by remember { mutableStateOf("8") }
    var dexterity by remember { mutableStateOf("8") }
    var constitution by remember { mutableStateOf("8") }
    var intelligence by remember { mutableStateOf("8") }
    var wisdom by remember { mutableStateOf("8") }
    var charisma by remember { mutableStateOf("8") }
    var selectedRace by remember { mutableStateOf("Humano") }
    var hitPoints by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    val races = listOf("Humano", "Elfo", "Anão", "Halfling", "Gnomo", "Draconato")

    // Calcular a soma dos pontos distribuídos
    val initialAttributes = getInitialAttributesForRace(selectedRace)
    val totalPoints = listOf(
        strength.toIntOrNull() ?: 8,
        dexterity.toIntOrNull() ?: 8,
        constitution.toIntOrNull() ?: 8,
        intelligence.toIntOrNull() ?: 8,
        wisdom.toIntOrNull() ?: 8,
        charisma.toIntOrNull() ?: 8
    ).sum() - initialAttributes.values.sum()

    val darkPurple = Color(0xFF4A148C)
    val lightPurple = Color(0xFFD1C4E9)

    Column(modifier = modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                TextField(
                    value = strength,
                    onValueChange = { strength = it },
                    label = { Text("Força") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = lightPurple,
                        focusedIndicatorColor = darkPurple,
                        unfocusedIndicatorColor = darkPurple
                    ),
                    shape = MaterialTheme.shapes.medium
                )
                TextField(
                    value = dexterity,
                    onValueChange = { dexterity = it },
                    label = { Text("Destreza") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = lightPurple,
                        focusedIndicatorColor = darkPurple,
                        unfocusedIndicatorColor = darkPurple
                    ),
                    shape = MaterialTheme.shapes.medium
                )
                TextField(
                    value = constitution,
                    onValueChange = { constitution = it },
                    label = { Text("Constituição") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = lightPurple,
                        focusedIndicatorColor = darkPurple,
                        unfocusedIndicatorColor = darkPurple
                    ),
                    shape = MaterialTheme.shapes.medium
                )
            }
            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                TextField(
                    value = intelligence,
                    onValueChange = { intelligence = it },
                    label = { Text("Inteligência") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = lightPurple,
                        focusedIndicatorColor = darkPurple,
                        unfocusedIndicatorColor = darkPurple
                    ),
                    shape = MaterialTheme.shapes.medium
                )
                TextField(
                    value = wisdom,
                    onValueChange = { wisdom = it },
                    label = { Text("Sabedoria") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = lightPurple,
                        focusedIndicatorColor = darkPurple,
                        unfocusedIndicatorColor = darkPurple
                    ),
                    shape = MaterialTheme.shapes.medium
                )
                TextField(
                    value = charisma,
                    onValueChange = { charisma = it },
                    label = { Text("Carisma") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = lightPurple,
                        focusedIndicatorColor = darkPurple,
                        unfocusedIndicatorColor = darkPurple
                    ),
                    shape = MaterialTheme.shapes.medium
                )
            }
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedRace,
                onValueChange = {},
                readOnly = true,
                label = { Text("Selecione a Raça") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = lightPurple,
                    focusedIndicatorColor = darkPurple,
                    unfocusedIndicatorColor = darkPurple
                ),
                shape = MaterialTheme.shapes.medium
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                races.forEach { race ->
                    DropdownMenuItem(
                        text = { Text(text = race) },
                        onClick = {
                            selectedRace = race
                            expanded = false
                            val initialAttributes = getInitialAttributesForRace(race)
                            strength = initialAttributes["Força"].toString()
                            dexterity = initialAttributes["Destreza"].toString()
                            constitution = initialAttributes["Constituição"].toString()
                            intelligence = initialAttributes["Inteligência"].toString()
                            wisdom = initialAttributes["Sabedoria"].toString()
                            charisma = initialAttributes["Carisma"].toString()
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                val valid = validateAttributes(strength, dexterity, constitution, intelligence, wisdom, charisma, initialAttributes)
                if (valid) {
                    val race = getRace(selectedRace)
                    val character = createCharacter(race, strength.toInt(), dexterity.toInt(), constitution.toInt(), intelligence.toInt(), wisdom.toInt(), charisma.toInt())
                    hitPoints = character.calculateHealthPoints()
                    errorMessage = ""
                    successMessage = "Personagem criado com êxito!"
                } else {
                    errorMessage = "Erro: Distribuição de pontos inválida. Verifique que a soma é 27 e cada atributo está entre 8 e 15."
                    successMessage = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = darkPurple)
        ) {
            Text("Calcular Pontos de Vida", color = Color.White, fontSize = 16.sp)
        }

        // Botão para Salvar Personagem
        Button(
            onClick = {
                val race = getRace(selectedRace)
                val character = createCharacter(race, strength.toInt(), dexterity.toInt(), constitution.toInt(), intelligence.toInt(), wisdom.toInt(), charisma.toInt())
                onSaveCharacter(character)
                successMessage = "Personagem salvo com sucesso!"
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {
            Text("Salvar Personagem", color = Color.White)
        }

        // Botão para Atualizar Personagem
        Button(
            onClick = {
                val race = getRace(selectedRace)
                val character = createCharacter(race, strength.toInt(), dexterity.toInt(), constitution.toInt(), intelligence.toInt(), wisdom.toInt(), charisma.toInt())
                onUpdateCharacter(character)
                successMessage = "Personagem atualizado com sucesso!"
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
        ) {
            Text("Atualizar Personagem", color = Color.Black)
        }

        // Botão para Deletar Personagem
        Button(
            onClick = {
                val race = getRace(selectedRace)
                val character = createCharacter(race, strength.toInt(), dexterity.toInt(), constitution.toInt(), intelligence.toInt(), wisdom.toInt(), charisma.toInt())
                onDeleteCharacter(character)
                successMessage = "Personagem deletado com sucesso!"
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Deletar Personagem", color = Color.White)
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (successMessage.isNotEmpty()) {
            Text(
                text = successMessage,
                color = Color.Green,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Text(
            text = "Pontos de Vida: $hitPoints",
            style = MaterialTheme.typography.bodyLarge,
            color = darkPurple,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Total dos Pontos Distribuídos: $totalPoints",
            style = MaterialTheme.typography.bodyLarge,
            color = if (totalPoints == 27) darkPurple else Color.Red,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

fun validateAttributes(strength: String, dexterity: String, constitution: String, intelligence: String, wisdom: String, charisma: String, initialAttributes: Map<String, Int>): Boolean {
    val attributes = listOf(strength, dexterity, constitution, intelligence, wisdom, charisma)
    if (attributes.any { it.toIntOrNull() == null || it.toInt() !in 8..15 }) {
        return false
    }
    val totalDistributedPoints = attributes.sumOf { it.toInt() } - initialAttributes.values.sum()
    return totalDistributedPoints == 27
}

fun getInitialAttributesForRace(raceName: String): Map<String, Int> {
    return when (raceName) {
        "Humano" -> mapOf("Força" to 8, "Destreza" to 8, "Constituição" to 8, "Inteligência" to 8, "Sabedoria" to 8, "Carisma" to 8)
        "Elfo" -> mapOf("Força" to 8, "Destreza" to 10, "Constituição" to 8, "Inteligência" to 8, "Sabedoria" to 8, "Carisma" to 8)
        "Anão" -> mapOf("Força" to 10, "Destreza" to 8, "Constituição" to 10, "Inteligência" to 8, "Sabedoria" to 8, "Carisma" to 8)
        "Halfling" -> mapOf("Força" to 8, "Destreza" to 10, "Constituição" to 8, "Inteligência" to 8, "Sabedoria" to 8, "Carisma" to 10)
        "Gnomo" -> mapOf("Força" to 8, "Destreza" to 8, "Constituição" to 8, "Inteligência" to 10, "Sabedoria" to 8, "Carisma" to 8)
        "Draconato" -> mapOf("Força" to 10, "Destreza" to 8, "Constituição" to 8, "Inteligência" to 8, "Sabedoria" to 8, "Carisma" to 10)
        else -> mapOf("Força" to 8, "Destreza" to 8, "Constituição" to 8, "Inteligência" to 8, "Sabedoria" to 8, "Carisma" to 8)
    }
}

fun getRace(raceName: String): Race {
    return when (raceName) {
        "Humano" -> Human()
        "Elfo" -> Elf()
        "Anão" -> Dwarf()
        "Halfling" -> Halfling()
        "Gnomo" -> Gnome()
        "Draconato" -> DragonBorn()
        else -> Human()
    }
}

fun createCharacter(race: Race, strength: Int, dexterity: Int, constitution: Int, intelligence: Int, wisdom: Int, charisma: Int): Character {
    val attributes = mutableMapOf(
        "Força" to strength,
        "Destreza" to dexterity,
        "Constituição" to constitution,
        "Inteligência" to intelligence,
        "Sabedoria" to wisdom,
        "Carisma" to charisma
    )
    val character = Character(attributes = attributes, race = race)
    character.applyRaceBonuses()
    return character
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        CharacterCreationScreen()
    }
}