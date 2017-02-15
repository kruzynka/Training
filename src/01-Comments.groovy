// One-line comment

/*
Wiele linijek
aaa
*/

/**
 * Narzędzie java-doc, definiujemy klasę
 *
 */
class User {

    /** Imię i nazwisko, brak modyfikatora dostępu - od razu jest to publiczne, definiujemy zwykły String */
    String fullName

    /**
     * Definiujemy metodę getInfo
     * Zwrotne informacje o użytkowniku, w Groovym nie musimy wrzucać return
     * Możemy zadeklarować to, ale nie musimy!
     * GString - typ od Grooviego, możemy interpolować wartości w ramach testu
     * @return Pełne informacje o właściwościach
     */
    String getInfo() {

        "Info: ${fullName}"
    }
}

def myUser = new User ()
myUser.fullName = 'Jan'

println(myUser.getInfo())
println(myUser.fullName)



