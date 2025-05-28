package ftrr.kadkviz.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ftrr.kadkviz.Kviz
import ftrr.kadkviz.presentation.cards.KvizCard
import ftrr.kadkviz.ui.theme.primaryContainerLight

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryContainerLight)
            .padding(16.dp)
    ) {
        LazyColumn {
            items(mockKvizList) {
                KvizCard(kviz = it)
            }
        }
    }
}

val mockKvizList = listOf(
    Kviz(
        name = "Požeški Pub Kviz",
        location = "Trg sv. Trojstva",
        date = "12.06.",
        time = "19:00",
        description = "Pridružite nam se na uzbudljivom pub kvizu u srcu Požege! Provjerite svoje znanje i osvojite vrijedne nagrade.",
        entryFee = "Besplatno",
        teamSize = "2-5 članova"
    ),
    Kviz(
        name = "Kviz u Azimutu",
        location = "Obala omladinaca",
        date = "13.06.",
        time = "19:30",
        description = "Tradicionalni kviz znanja u popularnom kafiću Azimut. Prijavite svoju ekipu na vrijeme!",
        entryFee = "5 EUR po ekipi",
        teamSize = "Max 4 člana"
    ),
    Kviz(
        name = "KSFF Pub Kviz",
        location = "Ivana Lučića 3",
        date = "15.06.",
        time = "20:00",
        description = "Studentski pub kviz u organizaciji Kluba studenata filozofskog fakulteta. Pokaži što znaš!",
        entryFee = "2 EUR po osobi",
        teamSize = "Do 6 članova"
    )
)