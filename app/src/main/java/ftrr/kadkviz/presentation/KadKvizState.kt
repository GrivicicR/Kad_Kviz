package ftrr.kadkviz.presentation

import ftrr.kadkviz.data.local.KvizEntity

data class KadKvizState(
    val triviaList: List<KvizEntity> = emptyList(),
)