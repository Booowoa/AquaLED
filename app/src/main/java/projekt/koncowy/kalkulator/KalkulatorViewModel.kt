package projekt.koncowy.kalkulator

import android.health.connect.datatypes.units.Power
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// plik w ktorym wykonywane sa faktyczne obliczenia mojego kalkulatora oraz jego akcje


class  KalkulatorViewModel: ViewModel() {

    var stan by mutableStateOf(KalkulatorStan())
        private set

    fun onAction(action : KalkulatorAkcje){
        when(action){
            is KalkulatorAkcje.Liczba -> wprowadzLiczbe(action.number)
            is KalkulatorAkcje.Przecinek -> wprowadzPrzecinek()
            is KalkulatorAkcje.AC -> stan = KalkulatorStan()
            is KalkulatorAkcje.Dzialanie -> wprowadzDzialanie(action.operation)
            is KalkulatorAkcje.Oblicz -> podajWynik()
            is KalkulatorAkcje.C -> usunOstatniElement()
            is KalkulatorAkcje.Znak -> zamienZnak()
        }
    }

    private fun zamienZnak() {
        if (stan.numer1.isNotBlank() && stan.dzialanie == null && !stan.numer1.contains("-")){
            stan = stan.copy(
                numer1 = "-"+stan.numer1
            )
            return
        }
        if(stan.numer1.isNotBlank() && stan.dzialanie == null && stan.numer1.contains("-")){
            stan = stan.copy(
                numer1 = stan.numer1.drop(1)
            )
            return
        }
        if (stan.numer2.isNotBlank() && stan.dzialanie != null && !stan.numer2.contains("-")){
            stan = stan.copy(
                numer2 = "-"+stan.numer2
            )
            return
        }
        if(stan.numer2.isNotBlank() && stan.dzialanie != null && stan.numer2.contains("-")){
            stan = stan.copy(
                numer2 = stan.numer2.drop(1)
            )
            return
        }
    }

    private fun usunOstatniElement() {
       when{
           stan.numer2.isNotBlank() -> stan = stan.copy(
               numer2 = stan.numer2.dropLast(1)
           )
           stan.dzialanie != null -> stan = stan.copy(
               dzialanie = null
           )
           stan.numer1.isNotBlank() -> stan = stan.copy(
               numer1 = stan.numer1.dropLast(1)
           )
       }
    }                                                                                                  // END OF USUN OSTATNI ELEMENT FUN

    private fun podajWynik() {
        val numer1 = stan.numer1.toDoubleOrNull()
        val numer2 = stan.numer2.toDoubleOrNull()
        if (numer1 != null && numer2 != null){
            val wynik = when (stan.dzialanie){
                is KalkulatorDzialanie.Dodaj -> numer1 + numer2
                is KalkulatorDzialanie.Odejmij -> numer1 - numer2
                is KalkulatorDzialanie.Mnozenie -> numer1 * numer2
                is KalkulatorDzialanie.Dzielenie -> numer1 / numer2
                is KalkulatorDzialanie.Procent -> numer1/100 * numer2
                is KalkulatorDzialanie.Potega -> Math.pow(numer1, numer2)
                is KalkulatorDzialanie.Logarytm -> Math.log(numer2)/Math.log(numer1)        // log(a)[b] = ln(b)/ln(a)

                else -> return
            }
            stan = stan.copy(
                numer1 = wynik.toString().take(15),
                numer2 = "",
                dzialanie = null
            )
        }




        // obliczenia dla dzialan na jednej zmiennej
        else if (numer1 != null && numer2 == null){
            val wynik = when (stan.dzialanie){
                is KalkulatorDzialanie.Sqrt -> Math.pow(numer1, 1.0/2.0)
                is KalkulatorDzialanie.Sinus -> Math.sin(numer1)
                is KalkulatorDzialanie.Cosinus -> Math.cos(numer1)
                is KalkulatorDzialanie.Tangens -> Math.tan(numer1)
                else -> return
            }
            stan = stan.copy(
                numer1 = wynik.toString().take(15),
                numer2 = "",
                dzialanie = null
            )
        }

    }                                                                                                  // END OF PODAJ WYNIK FUN

    private fun wprowadzDzialanie(dzialanie: KalkulatorDzialanie) {
       if (stan.numer1.isNotBlank()){

           stan = stan.copy(dzialanie = dzialanie)

       }
    }                                                                                                   // END OF WPORWADZ DZIALANIE FUN

    private fun wprowadzPrzecinek() {
        if (stan.dzialanie == null && !stan.numer1.contains(".") && stan.numer1.isNotBlank()){
            stan = stan.copy(
                numer1 = stan.numer1 + "."
            )
            return
        }
        if(stan.dzialanie == null && stan.numer1.isBlank()){
            stan = stan.copy(
                numer1 = "0."
            )
            return
        }

        if (!stan.numer2.contains(".") && stan.numer2.isNotBlank()){
            stan = stan.copy(
                numer2 = stan.numer2 + "."
            )
            return
        }
        if (stan.numer2.isBlank() && stan.dzialanie!= null){
            stan = stan.copy(
                numer2 = "0."
            )
            return
        }

    }                                                                                                   // END OF WPROWADZ PRZECINEK FUN

    private fun wprowadzLiczbe(numer: Int) {
        if (stan.dzialanie == null){
            if (stan.numer1.length >= MAX_dlugosc){
                return
            }
            stan = stan.copy(
                numer1 = stan.numer1 + numer                                                         // numer to jest liczba swiezo wprowadzona
            )
            return
        }
        if (stan.numer2.length >= MAX_dlugosc){
            return
        }
        stan = stan.copy(
            numer2 = stan.numer2 + numer
        )

    }                                                                                                   // END OF WPROWADZ LICZBE FUN
    companion object{
        private const val MAX_dlugosc = 8                                                               // okresla maksymalna dlugosc numerow
    }
}