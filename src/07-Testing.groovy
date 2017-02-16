import groovy.json.JsonOutput
import groovy.mock.interceptor.MockFor

import static java.lang.Character.*

/**
 * W Javie możemy wyłączyć asserce, w Groovym tego nie zrobimy
 * W Groovym zostaje rozwinięty każdy człon wyrażenia - w przypadku błędu widzimy drzewko tego co się wydarzyło
 */

def arr = [1, 2, 3]

//assercja zwracająca błąd, zobaczymy drzewko tego co się wydarzyło
//assert (arr << 8) == [4, 5, 6]

/----------------------------------------------------------------------------------------------------------------------/

/**
 * Podmienianie metod w prosty sposób
 */
class L10n {

    //na podstawie klucza zwróci wartość
    String translate(String key) {
        'Test value'
    }

    String test() {
        println 'ddd'
    }
}

/**
 * Prawa strona wyrażenia klucz -> wartość
 * Spróbuje dopasować klucze w obiekcie L10n
 * Przekazujemy 'Other value' -> 'Test value'
 * Zatem asserca będzie słuszna
 */
def service = [translate: { String key -> 'Other value' }] as L10n
service.test()

assert 'Other value' == service.translate('Test key')

//to samo, tylko bezpośrednio przekazujemy bez użycia funkcji translate
def serviceTwo = { String key -> 'Other value' } as L10n
assert 'Other value' == serviceTwo.translate('Test key')

/----------------------------------------------------------------------------------------------------------------------/

/**
 * Mockowanie
 */

class Dog {

    String name
}

def dogMock = new MockFor(Dog)

dogMock.demand.getName {
    'Azor'
}
//podmieniamy zachowanie:
dogMock.use {
    def burek = new Dog()
    assert burek.name == 'Azor'
}
//weryfikuje czy mockowa metoda w ogóle została wywołana (wysypie się, gdy nie będzie):
dogMock.expect.every()

/----------------------------------------------------------------------------------------------------------------------/

/**
 * Może być zapisane również jako String.metaClass.swapCase = { String it ->
 */
String.metaClass.swapCase = { ->
    //klasa pozwalająca na pracę z ciągiem znaków, nie tworzymy nowego obiektu za każdym razem:
    def sb = new StringBuffer()

    /**
     * Jeżeli jest upper to będzie lower, w przeciwnym wypadku (jeżeli jest lower) to będzie upper
     */
    delegate.each {
        /**
         * równoważnie Character.isUpperCase, Character.toLowerCase , Character.toUpperCase
         * estetycznie będzie zrobić import static java.lang.Character.*
         */
        sb << ((isUpperCase(it as char)) ? toLowerCase(it as char) : toUpperCase(it as char))
    }
    sb.toString()
}

def text = 'Hello'
assert text.swapCase() == 'hELLO'

/----------------------------------------------------------------------------------------------------------------------/

class DslExample {

    static please(action) {
        [the: { what ->
            [of: { n -> action(what(n)) }]
        }]
    }

    static show = {
        println it
    }

    static square_root = {
        Math.sqrt(it)
    }

    //static JSON = {JsonOutput.toJson(it)}
    static JSON = {
        def result = [value: JsonOutput.toJson(it)]
        result.saveTo = { new File(it) << result.value }
        return result
    }

    static convert(object) {
        [to: { it(object) }]
    }

    static void main(String[] args) {

        // equivalent to: please(show).the(square_root).of(100)
        please show the square_root of 100

        def dog = new Dog(name: 'burek')

        //println( convert(dog).to(JSON))
        convert dog to JSON saveTo "test.txt"
    }
}

/----------------------------------------------------------------------------------------------------------------------/

