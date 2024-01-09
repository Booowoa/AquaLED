package projekt.koncowy.kalkulator




// plik w ktorym przechowywany jest symbol dla każdego operatora kalkulatora

sealed class KalkulatorDzialanie(val symbol: String){
    // dzialania na 2 zmiennych
    object Dodaj: KalkulatorDzialanie("+")
    object Odejmij: KalkulatorDzialanie("-")
    object Mnozenie: KalkulatorDzialanie("*")
    object Dzielenie: KalkulatorDzialanie("/")
    object Procent: KalkulatorDzialanie("%")
    object Potega: KalkulatorDzialanie("^")
    object Logarytm: KalkulatorDzialanie("log")

    // dzialania na jednej zmiennej
    object Sqrt: KalkulatorDzialanie("√")
    object Sinus: KalkulatorDzialanie("sin")
    object Cosinus: KalkulatorDzialanie("cos")
    object Tangens: KalkulatorDzialanie("tg")



}
