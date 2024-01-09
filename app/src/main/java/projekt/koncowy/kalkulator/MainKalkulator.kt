package projekt.koncowy.kalkulator


import android.media.Image
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import projekt.koncowy.kalkulator.ui.theme.PurpleGrey40




@Composable
fun Mainkalkulator(navController: NavHostController, onAction: (KalkulatorAkcje) -> Unit, viewModel: KalkulatorViewModel) {


    var stan = viewModel.stan

    val ButtonSpacing = 6.dp
    val nawias = remember {
        mutableStateOf(1f)
    }
    val log_nawias = remember {
        mutableStateOf(1f)
    }


    val Gradient2 = Color(0xFFB6B6B6)
    val Gradient3 = Color(0xFFFAFAFA)
    val Gradient4 = Color(0xFF797979)





    val DarkGreen = Color(0xFF0ECF00)
    val BitDarkerGrey = Color(0xFFA8A8A8)
    val colorStops = arrayOf(
        0.1f to Gradient3,
        0.45f to Gradient2,
        0.7f to Gradient4
    )


    Box(modifier = Modifier
        .background(Brush.linearGradient(colorStops = colorStops))


        .fillMaxSize()
        .padding(8.dp)){



        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(ButtonSpacing)
        ) {
        Box{


            if(nawias.value == 1f && log_nawias.value== 1f) {
                Text(
                    text = stan.numer1 + "\n\n" + (stan.dzialanie?.symbol ?: "") + stan.numer2,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                        .alpha(nawias.value),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Black,
                    maxLines = 3
                )
            }

            if(nawias.value == 0f && log_nawias.value== 1f) {
                Text(

                    text = (stan.dzialanie?.symbol ?: "") + "(" + stan.numer1 + ")",

                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                        .alpha(1 - nawias.value),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Black,
                    maxLines = 2
                )
            }
            if (nawias.value == 1f && log_nawias.value== 0f){
            Text(
                text =  (stan.dzialanie?.symbol ?: "") +"("+ stan.numer1+ ")" + "[" + stan.numer2 + "]",
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .alpha(1 - log_nawias.value),
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = Black,
                maxLines = 2
            )

        }}









            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ButtonSpacing)
            ){





                KalkulatorButton(
                    symbol = "Sin(rad)",
                    modifier = Modifier
                        .background(color = Color.Red)
                        .aspectRatio(2f)
                        .weight(1f),
                    onClick = {

                        if(stan.numer1 != "") {
                            onAction(KalkulatorAkcje.Dzialanie(KalkulatorDzialanie.Sinus))
                            nawias.value = 0f
                            log_nawias.value = 1f
                        }

                    }
                )       // END BUTTON






                KalkulatorButton(
                    symbol = "Cos(rad)",
                    modifier = Modifier
                        .background(color = Color.Red)
                        .aspectRatio(2f)
                        .weight(1f),
                    onClick = {
                        if(stan.numer1 != "") {
                            onAction(KalkulatorAkcje.Dzialanie(KalkulatorDzialanie.Cosinus))
                            nawias.value = 0f
                            log_nawias.value = 1f
                        }

                    }
                )       // END BUTTON




                KalkulatorButton(
                    symbol = "Tg(rad)",
                    modifier = Modifier
                        .background(color = Color.Red)
                        .aspectRatio(2f)
                        .weight(1f),
                    onClick = {
                        if(stan.numer1 != "") {
                            onAction(KalkulatorAkcje.Dzialanie(KalkulatorDzialanie.Tangens))
                            nawias.value = 0f
                            log_nawias.value = 1f
                        }

                    }
                )       // END BUTTON

            }           // END ROW TRYGONOMETRY



            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ButtonSpacing)
            ){



                KalkulatorButton(
                    symbol = "log(n1)[n2]",
                    modifier = Modifier
                        .background(color = Color.Red)
                        .aspectRatio(4f)
                        .weight(2f),
                    onClick = {
                        if(stan.numer1 != "") {
                            onAction(KalkulatorAkcje.Dzialanie(KalkulatorDzialanie.Logarytm))
                            nawias.value = 1f
                            log_nawias.value = 0f
                        }

                    }
                )       // END BUTTON


                KalkulatorButton(
                    symbol = "%",
                    modifier = Modifier
                        .background(color = Color(DarkGreen.value))
                        .aspectRatio(2f)
                        .weight(1f),
                    onClick = {

                        if(stan.numer1 != "") {
                            onAction(KalkulatorAkcje.Dzialanie(KalkulatorDzialanie.Procent))
                            nawias.value = 1f
                            log_nawias.value = 1f
                        }

                    }
                )       // END BUTTON






                KalkulatorButton(
                    symbol = "^",
                    modifier = Modifier
                        .background(color = Color(DarkGreen.value))
                        .aspectRatio(2f)
                        .weight(1f),
                    onClick = {
                        if(stan.numer1 != "") {
                            onAction(KalkulatorAkcje.Dzialanie(KalkulatorDzialanie.Potega))
                            nawias.value = 1f
                            log_nawias.value = 1f
                        }

                    }
                )       // END BUTTON



            }           // END ROW po konsach












            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ButtonSpacing)
            ){





                KalkulatorButton(
                    symbol = "AC",
                    modifier = Modifier
                        .background(color = Color.Magenta)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.AC)
                        nawias.value = 1f
                        log_nawias.value = 1f

                    }
                )       // END BUTTON






                KalkulatorButton(
                    symbol = "C",
                    modifier = Modifier
                        .background(color = Color.Cyan)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {


                        if (stan.dzialanie == KalkulatorDzialanie.Sqrt || stan.dzialanie == KalkulatorDzialanie.Sinus || stan.dzialanie == KalkulatorDzialanie.Cosinus || stan.dzialanie == KalkulatorDzialanie.Tangens && nawias.value == 0f){
                            stan.numer2 = ""
                            nawias.value = 1f
                            onAction(KalkulatorAkcje.C)
                        } else if(stan.dzialanie == KalkulatorDzialanie.Logarytm && log_nawias.value == 0f){
                            if(stan.numer2 == ""){
                                log_nawias.value = 1f
                                onAction(KalkulatorAkcje.C)
                            } else {
                                onAction(KalkulatorAkcje.C)
                            }
                        }
                        else if(stan.numer1  == "NaN" || stan.numer1 == "Infinity" || stan.numer1 == "-Infinity"){
                                onAction(KalkulatorAkcje.AC)
                        }
                        else {
                            onAction(KalkulatorAkcje.C)
                        }

                    }
                )       // END BUTTON


                KalkulatorButton(

                    symbol = "√",
                    modifier = Modifier
                        .background(color = Color(DarkGreen.value))
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        if(stan.numer1 != "") {
                            onAction(KalkulatorAkcje.Dzialanie(KalkulatorDzialanie.Sqrt))
                            nawias.value = 0f
                            log_nawias.value = 1f
                        }
                    }
                )       // END BUTTON

                KalkulatorButton(
                    symbol = "/",
                    modifier = Modifier
                        .background(color = Color(DarkGreen.value))
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Dzialanie(KalkulatorDzialanie.Dzielenie))
                        nawias.value = 1f
                        log_nawias.value = 1f

                    }
                )       // END BUTTON

            }           // END ROW 1
















            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ButtonSpacing)
            ){

                KalkulatorButton(
                    symbol = "7",
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Liczba(7))


                    }
                )       // END BUTTON





                KalkulatorButton(
                    symbol = "8",
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Liczba(8))


                    }
                )       // END BUTTON






                KalkulatorButton(
                    symbol = "9",
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Liczba(9))


                    }
                )       // END BUTTON










                KalkulatorButton(
                    symbol = "*",
                    modifier = Modifier
                        .background(color = Color(DarkGreen.value))
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Dzialanie(KalkulatorDzialanie.Mnozenie))
                        nawias.value = 1f
                        log_nawias.value = 1f

                    }
                )       // END BUTTON





            }                         // END ROW 2





            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ButtonSpacing)
            ){

                KalkulatorButton(
                    symbol = "4",
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Liczba(4))


                    }
                )       // END BUTTON





                KalkulatorButton(
                    symbol = "5",
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Liczba(5))


                    }
                )       // END BUTTON






                KalkulatorButton(
                    symbol = "6",
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Liczba(6))


                    }
                )       // END BUTTON







                KalkulatorButton(
                    symbol = "-",
                    modifier = Modifier
                        .background(color = Color(DarkGreen.value))
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Dzialanie(KalkulatorDzialanie.Odejmij))
                        nawias.value = 1f
                        log_nawias.value = 1f

                    }
                )       // END BUTTON


            }                         // END ROW 3



            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ButtonSpacing)
            ){

                KalkulatorButton(
                    symbol = "1",
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Liczba(1))


                    }
                )       // END BUTTON





                KalkulatorButton(
                    symbol = "2",
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Liczba(2))


                    }
                )       // END BUTTON






                KalkulatorButton(
                    symbol = "3",
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Liczba(3))


                    }
                )       // END BUTTON







                KalkulatorButton(
                    symbol = "+",
                    modifier = Modifier
                        .background(color = Color(DarkGreen.value))
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Dzialanie(KalkulatorDzialanie.Dodaj))
                        nawias.value = 1f
                        log_nawias.value = 1f

                    }
                )       // END BUTTON


            }                         // END ROW 4


            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ButtonSpacing)
            ){

                KalkulatorButton(
                    symbol = "+/-",
                    modifier = Modifier
                        .background(color = Color(DarkGreen.value))
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Znak)


                    }
                )       // END BUTTON




                KalkulatorButton(
                    symbol = "0",
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Liczba(0))


                    }
                )       // END BUTTON





                KalkulatorButton(
                    symbol = ".",
                    modifier = Modifier
                        .background(color = Color(BitDarkerGrey.value))
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        onAction(KalkulatorAkcje.Przecinek)


                    }
                )       // END BUTTON









                KalkulatorButton(
                    symbol = "=",
                    modifier = Modifier
                        .background(color = Color(DarkGreen.value))
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {

                        if (stan.numer2 != "" && nawias.value == 0f ) {                             // petla if zabezpiecza dzialanie kalkulatora
                            stan.numer2 = ""                                                        // jezeli po akcji wymagajacej tylko 1 liczby
                            onAction(KalkulatorAkcje.Oblicz)                                        // wpiszemy 2 liczbe (ktorej nie widac)
                            nawias.value = 1f                                                       // to program sie zawiesi
                        }

                        else if(log_nawias.value == 0f && stan.numer2 ==""){
                            stan.numer2 = "0"
                            onAction(KalkulatorAkcje.Oblicz)
                            log_nawias.value = 1f
                        }
                        else {                                                                    // petla sprawdza czy nawias = 0f- czyli czy warunek dla 1 liczby
                            onAction(KalkulatorAkcje.Oblicz)                                        // potem sprawdza czy numer2 jest pusty -> jezlei tak to wykonuje obliczenia
                            nawias.value = 1f                                                       // jezeli nie to najpierw zeruje a dopiero potem robi obliczenia
                            log_nawias.value = 1f
                        }
                    }
                )       // END BUTTON


            }                         // END ROW 5


        }
    }
    
    
    

}
