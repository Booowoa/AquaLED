package projekt.app.aqualed

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

val database = FirebaseDatabase.getInstance("adres bazy danych")
val myRef = database.getReference("Message")

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Akwarium(navController: NavHostController, viewModel: AquariumViewModel = viewModel()) {
    val aquariumViewModel: AquariumViewModel = viewModel()
    val currentData = viewModel.currentData
    var hourStartValue by remember { mutableStateOf("") }
    var hourEndValue by remember { mutableStateOf("") }
    var RStartValue by remember { mutableStateOf("") }
    var GStartValue by remember { mutableStateOf("") }
    var BStartValue by remember { mutableStateOf("") }
    var PowerStartValue by remember { mutableStateOf("") }
    var REndValue by remember { mutableStateOf("") }
    var GEndValue by remember { mutableStateOf("") }
    var BEndValue by remember { mutableStateOf("") }
    var PowerEndValue by remember { mutableStateOf("") }
    var StartColorValue by remember { mutableStateOf(Color.Black) }
    var EndColorValue by remember { mutableStateOf(Color.Black) }

    val defaultValues = listOf(                 // godzina:
        "R: 005, G: 005, B: 035, Power: 000",   // 0
        "R: 005, G: 005, B: 035, Power: 000",   // 1
        "R: 005, G: 005, B: 035, Power: 000",   // 2
        "R: 005, G: 005, B: 035, Power: 000",   // 3
        "R: 010, G: 010, B: 035, Power: 005",   // 4
        "R: 020, G: 020, B: 045, Power: 015",   // 5
        "R: 030, G: 030, B: 055, Power: 025",   // 6
        "R: 090, G: 090, B: 105, Power: 115",   // 7//// mocne swiecenie
        "R: 160, G: 160, B: 140, Power: 150",   // 8
        "R: 200, G: 200, B: 190, Power: 200",   // 9
        "R: 225, G: 225, B: 205, Power: 230",   // 10
        "R: 240, G: 240, B: 210, Power: 250",   // 11
        "R: 255, G: 255, B: 235, Power: 255",   // 12
        "R: 255, G: 255, B: 235, Power: 255",   // 13
        "R: 255, G: 255, B: 235, Power: 255",   // 14
        "R: 255, G: 255, B: 235, Power: 255",   // 15
        "R: 220, G: 220, B: 195, Power: 220",   // 16
        "R: 190, G: 190, B: 175, Power: 170",   // 17   /////// 10h mocnego swiecenia
        "R: 070, G: 070, B: 065, Power: 090",   // 18
        "R: 015, G: 015, B: 040, Power: 035",   // 19
        "R: 013, G: 013, B: 037, Power: 010",   // 20
        "R: 010, G: 010, B: 035, Power: 000",   // 21
        "R: 005, G: 005, B: 035, Power: 000",   // 22
        "R: 005, G: 005, B: 035, Power: 000"    // 23
    )

    fun sendData(
        aquariumViewModel: AquariumViewModel,
        myRef: DatabaseReference,
        context: Context
    ) {

        // Funkcja sprawdza czy wszystko zostalo uzupelnione i jezeli nie to wyswietla Toast
        val formattedData = buildString {   // budowana jest ramka danych
            append("0") // zaczynajaca sie od 0

            for ((index, data) in aquariumViewModel.currentData.withIndex()) {
                // Sprawdzanie czy wypelnione zostaly wszystkie dane
                if (data.isBlank() || !data.matches(Regex("""R: \d+, G: \d+, B: \d+, Power: \d+"""))) {
                    // W przypadku niewypelnienia jakiegos pola pojawia sie Toast z pierwsza niewypelniona godzina
                    Toast.makeText(context,"Fill data for hour : ${index} ", Toast.LENGTH_SHORT).show()
                    return // Przerwanie wykonywania funkcji w przypadku niewypelnienia pola
                }

                // Rozdzielanie wartosci R, G, B i Power
                val parts = data.split(", ")
                val rValue = parts[0].substringAfter("R: ").padStart(3, '0')
                val gValue = parts[1].substringAfter("G: ").padStart(3, '0')
                val bValue = parts[2].substringAfter("B: ").padStart(3, '0')
                val powerValue = parts[3].substringAfter("Power: ").padStart(3, '0')

                // sklejenie kolejnych iteracji w jedna dluga ramke
                append(".$rValue.$gValue.$bValue.$powerValue")
            }
        }
        // wyslanie zbudowanej ramki do bazy danych
        myRef.setValue(formattedData)
    }

    // funckja pobiera pierwszy element z bazy danych na podstawie ktorego stwierdzane jest czy istnieje polaczenie z uc czy nie
    fun testConnection(context: Context, myRef: DatabaseReference) {
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(String::class.java)

                if (data != null) {
                    val firstElement = data.split(".").first()

                    if (firstElement == "0")
                    {
                        Toast.makeText(context, "No communication with microcontroller.", Toast.LENGTH_SHORT).show()
                    } else if (firstElement == "1")
                    {
                        Toast.makeText(context, "Data read successfully", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "No connection with database.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun putDefaultValues(){
        for (hour in 0..23){
            currentData[hour] = defaultValues[hour]
        }
    }

    fun clearAll() {
        hourStartValue = ""
        hourEndValue = ""
        RStartValue = ""
        GStartValue = ""
        BStartValue = ""
        PowerStartValue = ""
        REndValue = ""
        GEndValue = ""
        BEndValue = ""
        PowerEndValue = ""

        // Resetowanie boxow zmieniajacych kolory
        StartColorValue = Color.Black
        EndColorValue = Color.Black

        // Resetowanie danych w tabeli
        currentData.forEachIndexed { index, _ ->
            currentData[index] = "R: -, G: -, B: -, Power: -"
        }
    }

    fun updateStartColor() {
        // s jak start
        val rs = RStartValue.toIntOrNull() ?: 0
        val gs = GStartValue.toIntOrNull() ?: 0
        val bs = BStartValue.toIntOrNull() ?: 0
        StartColorValue = Color(rs, gs, bs, 255)
    }

    fun updateEndColor() {
        // e jak end
        val re = REndValue.toIntOrNull() ?: 0
        val ge = GEndValue.toIntOrNull() ?: 0
        val be = BEndValue.toIntOrNull() ?: 0
        EndColorValue = Color(re, ge, be, 255)
    }

    fun checkAndSetValue_Hour(newValue: String, onValueChange: (String) -> Unit) {
        // jesli pole jest puste to na czyszczone jest pole zeby tekst sie resetowal
        if (newValue.isEmpty()) {
            onValueChange("")
            return
        }

        val filteredText = newValue.filter { it.isDigit() }
        if (filteredText.length <= 2) {
            val number = filteredText.toIntOrNull() ?: -1
            if (number in 0..23) {
                onValueChange(filteredText)
            }
        }
    }

    fun checkAndSetValue_RGB_start(newValue: String, onValueChange: (String) -> Unit) {
        val filteredText = newValue.filter { it.isDigit() }
        if (filteredText.length <= 3) {
            val number = filteredText.toIntOrNull() ?: 0
            if (number in 0..255) {
                onValueChange(filteredText)
                updateStartColor()
            }
        }
    }

    fun checkAndSetValue_RGB_end(newValue: String, onValueChange: (String) -> Unit) {
        val filteredText = newValue.filter { it.isDigit() }
        if (filteredText.length <= 3) {
            val number = filteredText.toIntOrNull() ?: 0
            if (number in 0..255) {
                onValueChange(filteredText)
                updateEndColor()
            }
        }
    }

    fun checkAndSetValue_Power_LED_start(newValue: String, onValueChange: (String) -> Unit) {
        val filteredText = newValue.filter { it.isDigit() }
        if (filteredText.length <= 3) {
            val number = filteredText.toIntOrNull() ?: 0
            if (number in 0..255) {
                onValueChange(filteredText)
            }
        }
    }

    fun checkAndSetValue_Power_LED_end(newValue: String, onValueChange: (String) -> Unit) {
        val filteredText = newValue.filter { it.isDigit() }
        if (filteredText.length <= 3) {
            val number = filteredText.toIntOrNull() ?: 0
            if (number in 0..255) {
                onValueChange(filteredText)
            }
        }
    }

    fun updateValueForStart() {
        val startHour = hourStartValue.toIntOrNull() ?: -1
        val rStart = RStartValue.toIntOrNull() ?: 0
        val gStart = GStartValue.toIntOrNull() ?: 0
        val bStart = BStartValue.toIntOrNull() ?: 0
        val powerStart = PowerStartValue.toIntOrNull() ?: 0

        if (startHour in 0..23) {

            currentData[startHour] = "R: $rStart, G: $gStart, B: $bStart, Power: $powerStart"

        }
    }

    // funkcja z zakresu pomiedzy godzina poczatkowa a koncowa interpoluje liniowo wartosci i zapisuje je w tabeli
    fun updateRangeValues() {
        val startHour = hourStartValue.toIntOrNull() ?: -1
        val endHour = hourEndValue.toIntOrNull() ?: -1
        val rStart = RStartValue.toIntOrNull() ?: 0
        val gStart = GStartValue.toIntOrNull() ?: 0
        val bStart = BStartValue.toIntOrNull() ?: 0
        val powerStart = PowerStartValue.toIntOrNull() ?: 0
        val rEnd = REndValue.toIntOrNull() ?: 0
        val gEnd = GEndValue.toIntOrNull() ?: 0
        val bEnd = BEndValue.toIntOrNull() ?: 0
        val powerEnd = PowerEndValue.toIntOrNull() ?: 0

        if (startHour in 0..23) {
            currentData[startHour] = "R: $rStart, G: $gStart, B: $bStart, Power: $powerStart"
        }
        if (endHour in 0..23) {
            currentData[endHour] = "R: $rEnd, G: $gEnd, B: $bEnd, Power: $powerEnd"
        }

        if (startHour in 0..23 && endHour in 0..23) {
            val hours = if (startHour < endHour) {
                (startHour + 1 until endHour).toList()
            } else {
                (startHour + 1..23).toList() + (0 until endHour).toList()
            }

            val hoursDiff = hours.size + 1

            hours.forEachIndexed { index, hour ->
                val fraction = (index + 1).toFloat() / hoursDiff
                val rInterpolated = (rStart + (rEnd - rStart) * fraction).toInt()
                val gInterpolated = (gStart + (gEnd - gStart) * fraction).toInt()
                val bInterpolated = (bStart + (bEnd - bStart) * fraction).toInt()
                val powerInterpolated = (powerStart + (powerEnd - powerStart) * fraction).toInt()

                currentData[hour] = "R: $rInterpolated, G: $gInterpolated, B: $bInterpolated, Power: $powerInterpolated"
            }
        }
    }

    val context = LocalContext.current

    val Gradient2 = Color(0xFFB6B6B6)
    val Gradient3 = Color(0xFFFAFAFA)
    val Gradient4 = Color(0xFF797979)

    val colorStops = arrayOf(
        0.1f to Gradient3,
        0.45f to Gradient2,
        0.7f to Gradient4
    )

    Box(
        modifier = Modifier
            .background(Brush.linearGradient(colorStops = colorStops))
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "The program accepts numbers from 0 to 255 except hours.",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LEDInputBox(
                    value = hourStartValue,
                    onValueChange = { checkAndSetValue_Hour(it) { hourStartValue = it } },
                    label = "Start Hour",
                    boxColor = Color(0xFFC0C0C0),
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(3.8f)
                )

                Box(
                    modifier = Modifier
                        .background(color = StartColorValue)
                        .aspectRatio(3.8f)
                        .border(width = 1.dp, color = Color.Black)
                        .weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LEDInputBox(
                    value = RStartValue,
                    onValueChange = { checkAndSetValue_RGB_start(it) { RStartValue = it } },
                    label = "R",
                    boxColor = Color(0xFFD8736A),
                    modifier = Modifier.weight(1.3f).aspectRatio(1.8f)
                )
                LEDInputBox(
                    value = GStartValue,
                    onValueChange = { checkAndSetValue_RGB_start(it) { GStartValue = it } },
                    label = "G",
                    boxColor = Color(0xFF7EBE46),
                    modifier = Modifier.weight(1.3f).aspectRatio(1.8f)
                )
                LEDInputBox(
                    value = BStartValue,
                    onValueChange = { checkAndSetValue_RGB_start(it) { BStartValue = it } },
                    label = "B",
                    boxColor = Color(0xFF5DADEC),
                    modifier = Modifier.weight(1.3f).aspectRatio(1.8f)
                )
                LEDInputBox(
                    value = PowerStartValue,
                    onValueChange = { checkAndSetValue_Power_LED_start(it) { PowerStartValue = it } },
                    label = "Power",
                    boxColor = Color(0xFFBA55D3),
                    modifier = Modifier.weight(1.3f).aspectRatio(1.8f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LEDInputBox(
                    value = hourEndValue,
                    onValueChange = { checkAndSetValue_Hour(it) { hourEndValue = it } },
                    label = "End Hour",
                    boxColor = Color(0xFFC0C0C0),
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(3.8f)
                )

                Box(
                    modifier = Modifier
                        .background(color = EndColorValue)
                        .aspectRatio(3.8f)
                        .border(width = 1.dp, color = Color.Black)
                        .weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LEDInputBox(
                    value = REndValue,
                    onValueChange = { checkAndSetValue_RGB_end(it) { REndValue = it } },
                    label = "R",
                    boxColor = Color(0xFFD8736A),
                    modifier = Modifier.weight(1.3f).aspectRatio(1.8f)
                )
                LEDInputBox(
                    value = GEndValue,
                    onValueChange = { checkAndSetValue_RGB_end(it) { GEndValue = it } },
                    label = "G",
                    boxColor = Color(0xFF7EBE46),
                    modifier = Modifier.weight(1.3f).aspectRatio(1.8f)
                )
                LEDInputBox(
                    value = BEndValue,
                    onValueChange = { checkAndSetValue_RGB_end(it) { BEndValue = it } },
                    label = "B",
                    boxColor = Color(0xFF5DADEC),
                    modifier = Modifier.weight(1.3f).aspectRatio(1.8f)
                )
                LEDInputBox(
                    value = PowerEndValue,
                    onValueChange = { checkAndSetValue_Power_LED_end(it) { PowerEndValue = it } },
                    label = "Power",
                    boxColor = Color(0xFFBA55D3),
                    modifier = Modifier.weight(1.3f).aspectRatio(1.8f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                MyButton(label = "Update Range", modifier = Modifier.weight(1f).aspectRatio(3.2f)) { updateRangeValues() }

                MyButton(label = "Update Value", modifier = Modifier.weight(1f).aspectRatio(3.2f)) { updateValueForStart() }

                MyButton(label = "Download Data", modifier = Modifier.weight(1f).aspectRatio(3.2f)) { aquariumViewModel.loadPreviousData(myRef) }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                MyButton(label = "Send", modifier = Modifier.weight(1f).aspectRatio(2.4f)) { sendData(aquariumViewModel = aquariumViewModel, myRef = myRef, context) }

                MyButton(label = "Test", modifier = Modifier.weight(1f).aspectRatio(2.4f)) { testConnection(context, myRef) }

                MyButton(label = "Clear", modifier = Modifier.weight(1f).aspectRatio(2.4f)) { clearAll() }

                MyButton(label = "Default", modifier = Modifier.weight(1f).aspectRatio(2.4f)) { putDefaultValues() }

            }

            Column(modifier = Modifier.fillMaxSize()) {

                LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 2.dp)) {
                    // nieprzewijalny pierwszy wiersz
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp)
                        ) {
                            val columnWidth = Modifier.weight(1f)


                            Text(
                                text = "Hour",
                                fontSize = 16.sp,
                                color = Color.Black,
                                modifier = columnWidth
                                    .background(Color(0xFFC0C0C0))
                                    .border(1.dp, Color.Black)
                                    .padding(8.dp)
                            )
                            Text(
                                text = "R",
                                fontSize = 16.sp,
                                color = Color.Black,
                                modifier = columnWidth
                                    .background(Color(0xFFD8736A))
                                    .border(1.dp, Color.Black)
                                    .padding(8.dp)
                            )
                            Text(
                                text = "G",
                                fontSize = 16.sp,
                                color = Color.Black,
                                modifier = columnWidth
                                    .background(Color(0xFF7EBE46))
                                    .border(1.dp, Color.Black)
                                    .padding(8.dp)
                            )
                            Text(
                                text = "B",
                                fontSize = 16.sp,
                                color = Color.Black,
                                modifier = columnWidth
                                    .background(Color(0xFF5DADEC))
                                    .border(1.dp, Color.Black)
                                    .padding(8.dp)
                            )
                            Text(
                                text = "Power",
                                fontSize = 16.sp,
                                color = Color.Black,
                                modifier = columnWidth
                                    .background(Color(0xFFBA55D3))
                                    .border(1.dp, Color.Black)
                                    .padding(8.dp)
                            )
                        }
                    }

                    // Dane wyswietlane w przewijalnej tabeli
                    itemsIndexed(currentData) { hour, data ->   // godzina to index
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp)
                        ) {
                            val columnWidth = Modifier.weight(1f)

                            Text(
                                text = "$hour",
                                fontSize = 16.sp,
                                color = Color.Black,
                                modifier = columnWidth
                                    .background(Color(0xFFC0C0C0))
                                    .border(1.dp, Color.Black)
                                    .padding(4  .dp)
                            )

                            Text(
                                text = data.split(",")[0].substringAfter("R: ").trim(),
                                fontSize = 16.sp,
                                color = Color.Black,
                                modifier = columnWidth
                                    .background(Color(0xFFD8736A))
                                    .border(1.dp, Color.Black)
                                    .padding(4.dp)
                            )

                            Text(
                                text = data.split(",")[1].substringAfter("G: ").trim(),
                                fontSize = 16.sp,
                                color = Color.Black, // Tekst czarny
                                modifier = columnWidth
                                    .background(Color(0xFF7EBE46))
                                    .border(1.dp, Color.Black)
                                    .padding(4.dp)
                            )

                            Text(
                                text = data.split(",")[2].substringAfter("B: ").trim(),
                                fontSize = 16.sp,
                                color = Color.Black,
                                modifier = columnWidth
                                    .background(Color(0xFF5DADEC))
                                    .border(1.dp, Color.Black)
                                    .padding(4.dp)
                            )

                            Text(
                                text = data.split(",")[3].substringAfter("Power: ").trim(),
                                fontSize = 16.sp,
                                color = Color.Black,
                                modifier = columnWidth
                                    .background(Color(0xFFBA55D3))
                                    .border(1.dp, Color.Black)
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

