// AquariumViewModel.kt
package projekt.app.aqualed

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class AquariumViewModel : ViewModel() {

    val currentData = mutableStateListOf<String>().apply {
        addAll(List(24) { "R: -, G: -, B: -, Power: -" })
    }

    fun loadPreviousData(myRef: DatabaseReference) {
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(String::class.java)?.split(".") ?: return
                if (data.size >= 4 * 24) {
                    for (hour in 0 until 24) {
                        currentData[hour] = "R: ${data[hour * 4 + 1]}, G: ${data[hour * 4 + 2]}, B: ${data[hour * 4 + 3]}, Power: ${data[hour * 4 + 4]}"
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Value event listener musi rozwazyc kazdy przypadek dlatego musi byc kod, ale w ten aplikacji 2 przypadek nie ma znaczenia
            }
        })
    }
}
