package projekt.koncowy.kalkulator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun Start(navController: NavHostController) {

    val Gradient2 = Color(0xFFB6B6B6)
    val Gradient3 = Color(0xFFFAFAFA)
    val Gradient4 = Color(0xFF797979)
    val colorStops = arrayOf(
        0.1f to Gradient3,
        0.45f to Gradient2,
        0.7f to Gradient4
    )
    Box (

        modifier = Modifier.fillMaxSize().background(Brush.linearGradient(colorStops = colorStops))
            .clickable { navController.navigate(route = "MainKalkulator") }

    ){

            Text(text = "Projekt końcowy \n Kalkulator \n Mateusz Kot \n 268704",
                modifier = Modifier.fillMaxSize()
                .padding(50.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold)

    }
}