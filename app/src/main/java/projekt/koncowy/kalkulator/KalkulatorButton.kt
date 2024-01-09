package projekt.koncowy.kalkulator


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// to jest plik w ktorym zdefiniowany jest wyglada przyciskow

@Composable
fun KalkulatorButton(

    symbol : String,
    modifier: Modifier,
    onClick: ()-> Unit

)



{

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier

            .clip(RoundedCornerShape(25.dp, 0.dp, 25.dp, 0.dp))
            .clickable { onClick() }
            .then(modifier)

    ){
        Text(
            text = symbol,
            fontWeight = FontWeight.SemiBold,
            fontSize = 27.sp,
            color = Color.Black
        )
    }
}