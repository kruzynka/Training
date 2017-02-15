class Test{

    def name = 'Test class'

    //nadpisze nam def name
    def getName() {
        'Test name'
    }

    def testPint(){
     "Test..."
    }

    /**
     * Metoda z interfejsu Groovy object
     * Daje możliwości dynamicznego wywołania metody
     * Zakomentowanie tej motody nie będzie miało żadnej konsekwencji, trzaktujemy to jako dodatkowy bezpiecznik
     */
    @Override
    Object invokeMethod(String name, Object args) {
        "Invoke method"
    }

    /**
     * specjalnie zamienione z == na !=
     * jeżeli property będzie róże od name, wtedy zwróć name, w przeciwnym razie zwróć podany String
     * @param property
     * @return
     */
    @Override
    Object getProperty(String property) {
        property != 'name' ? name : 'Not found'
    }

    /**
     * Obiekt, który udaje obiekt docelowy
     * @param property
     * @return
     */
    @Override
    void setProperty(String property, Object newValue) {

    }
}

def test = new Test()

assert test.testPint() == 'Test...'
//mimo tego, że nie mamy metody testFake, odpalimy test:
assert test.testFake() == 'Invoke method'

println test.name
//domślni musimy przejść przez get-er:
println test.someProperty

//używając meta klasy:
println(test.getMetaClass().getAttribute(test, 'name'))
assert test.getMetaClass().getAttribute(test, 'name') == 'Test class'

println "-"*50

/----------------------------------------------------------------------------------------------------------------------/

/**
 * Definiujemy zbiór książek tylko o takich Stringach, pozostałe statyczne elementy będziemy modyfikować
 * poprzez metaClass-y
 */
class Book{

    String author
    String title

    def printInfo(){
        println('Book')
    }

    def getPagesCount() {
        45
    }
}

//dodajemy funkcję do kolekcji
Book.metaClass.getPrice << { 45 }
Book.metaClass.isbm = ''
Book.metaClass.setIsbm = { String value ->
    println 'New ISBM'
    isbm = value
}
Book.metaClass.constructor = { String title ->
    new Book(title: title)
}
// nie ma znaczenia czy używamy << czy =
Book.metaClass.static.publisher = { 'Altkom Akademia' }
Book.metaClass.printInfo = { 'Mocked' }
Book.metaClass.getPagesCount = test.&getName //nie wołamy metody, wskazujemy referencje do testu


def book = new Book()
book.isbm = '23456'
book.setIsbm('45678') // bez ' ' nie przejdzie

println book.getPrice()
println book.isbm


//publisher Altkom Akademia dla nowej książi 'Groovy Book'
new Book('Groovy Book')
println Book.publisher()

println book.printInfo()
println book.getPagesCount()

/----------------------------------------------------------------------------------------------------------------------/

/**
 * Dynamiczne wywoływanie metod np. nie znamy metody w czasie wykonywania
 * Dane dostaniemy dopiero w run-timie
 */
class App {

    String name
}

def newName = 'Groovy'

App.metaClass."changeNameToGroovy" = { -> delegate.name = "$newName" }

def app = new App()

//wraca nulla, ponieważ jeszcze nie ma przekazanej wartości Groovy
println app.name

app.changeNameToGroovy()

println app.name

/----------------------------------------------------------------------------------------------------------------------/