package ftrr.kadkviz.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ftrr.kadkviz.data.local.EkipaEntity
import ftrr.kadkviz.data.local.KvizEntity
import ftrr.kadkviz.data.repository.KadKvizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class KadKvizViewModel(context: Context) : ViewModel() {
    private val repository = KadKvizRepository(context)

    private val _state = MutableStateFlow(KadKvizState())
    val state = _state.asStateFlow()

    fun insertKviz(kviz: KvizEntity) = viewModelScope.launch {
        repository.insertKviz(kviz)
    }

    fun addTeamToQuiz(ekipa: EkipaEntity) = viewModelScope.launch {
        repository.addTeamToQuiz(ekipa)
    }

    fun getAllKviz() = viewModelScope.launch {
        _state.update {
            it.copy(
                triviaList = repository.getAllKviz()
            )
        }
    }

    fun getAllTeams() = viewModelScope.launch {
        repository.getAllTeams()
    }

    fun deleteAllKviz() = viewModelScope.launch {
        repository.deleteAllKviz()
    }
}