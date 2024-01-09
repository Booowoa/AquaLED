package projekt.koncowy.kalkulator

sealed class KalkulatorAkcje{
    data class Liczba(val number: Int): KalkulatorAkcje()       // wysylanie liczby
    object AC: KalkulatorAkcje()                                // wyczysc ekran
    object C: KalkulatorAkcje()                                 // usuwa ostatni znak
    object Przecinek: KalkulatorAkcje()                         // w zasadzie to kropka bo na przecinku nie dzialaja obliczenia
    object Oblicz: KalkulatorAkcje()                            // to akcja dla przycisku =
    object Znak: KalkulatorAkcje()                              // ToDo zmiana znaku na przeciwny
    data class Dzialanie(val operation: KalkulatorDzialanie): KalkulatorAkcje()              // tutaj sa wszystkie akcje tyjpu: dodawanie, mnozenie, dzielenie itd


}
