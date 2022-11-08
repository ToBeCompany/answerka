package com.arbonik.answerka.viewmodels

import com.arbonik.answerka.database.GameRepository
import com.arbonik.answerka.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*


class GameViewModel(
    private val gameRepository : GameRepository
) {
    private val _gameState : MutableStateFlow<GameState> =
        MutableStateFlow(GameState.INIT)
    val gameState : StateFlow<GameState> = _gameState.asStateFlow()

    private val _errorMessage : MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage : StateFlow<String?> = _errorMessage.asStateFlow()

    private val _currentAsk : MutableStateFlow<Ask?> = MutableStateFlow(null)
    val currentAsk : StateFlow<Ask?> = _currentAsk.asStateFlow()

    private val _players : MutableStateFlow<List<Player>> =
        MutableStateFlow(listOf())
    val players : StateFlow<List<Player>> = _players.asStateFlow()

    private val _selectedPlayers : MutableStateFlow<List<Player>> =
        MutableStateFlow(listOf())

    val selectedPlayers = _selectedPlayers
        .combine(_players){ selected, all ->
            all.map {
                SelectablePlayer(it, selected.contains(it))
            }
        }

    private val _currentTask : MutableStateFlow<Task?> = MutableStateFlow(null)
    val currentTask : StateFlow<Task?> = _currentTask.asStateFlow()

    fun addPlayer(player: Player) {
        if (player.name.isNotEmpty())
            _players.value += player
        else
            _errorMessage.tryEmit("Добавьте хотя бы один символ")
    }

    fun removePlayer(player: Player){
        _players.value -= player
    }

    fun changeSelectedPlayer(player: Player){
        if (_selectedPlayers.value.contains(player)){
            unselectedPlayer(player)
        } else {
            selectedPlayer(player)
        }
    }

    fun nextStep(){
        when (_gameState.value){
            is GameState.INIT -> {}
            is GameState.Ask -> {
                if (_selectedPlayers.value.isNotEmpty())
                    loadTask()
                else
                    loadAsk()
            }
            GameState.Start -> {
                if (_players.value.isNotEmpty()){
                    if (_currentAsk.value == null) {
                        loadAsk()
                    } else {
                        // игра просто продолжается без загрузки нового вопроса
                        _gameState.value = GameState.Ask(_currentAsk.value!!)
                    }
                } else {
                    _errorMessage.tryEmit("Добавьте хотя бы одного ирока")
                }
            }
            is GameState.Task -> {
                unselectedAllPlayers()
                _currentTask.value = null
                loadAsk()
            }
        }
    }

    fun startGame(){
        _gameState.value = GameState.Start
    }
    fun pauseGame(){
        _gameState.value = GameState.Start
    }

    fun clearErrorMessage(){
        _errorMessage.value = null
    }

    private fun loadTask(){
        gameRepository.getTasks().random().let { task ->
            _currentTask.value = task
            _gameState.value = GameState.Task(task)
        }
    }

    private fun loadAsk(){
        gameRepository.getAsks().random().let { ask ->
            _currentAsk.value = ask
            _gameState.value = GameState.Ask(ask)
        }
    }

    private fun selectedPlayer(player: Player){
        _selectedPlayers.value += player
    }

    private fun unselectedPlayer(player: Player){
        _selectedPlayers.value -= player
    }

    private fun unselectedAllPlayers(){
        _selectedPlayers.value = listOf()
    }

}

