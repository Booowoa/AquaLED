package projekt.app.aqualed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import projekt.app.aqualed.ui.theme.KalkulatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KalkulatorTheme {

                Nav()
            }
        }
    }
}

