package projekt.koncowy.kalkulator

// moj kalkulator ma budowe taka:
// numer1 dzialanie numer2 np
// 1 + 2


data class KalkulatorStan(
    val numer1: String = "",                                            // wartosci domyslne
    var numer2: String = "",
    val dzialanie: KalkulatorDzialanie? = null
)
