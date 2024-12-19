package projekt.app.aqualed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LEDInputBox(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    boxColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, fontSize = 14.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Black) },
            textStyle = androidx.compose.ui.text.TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 2.sp,
                color = Black
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxSize()
                    ,
            colors = TextFieldDefaults.textFieldColors(containerColor = boxColor),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}