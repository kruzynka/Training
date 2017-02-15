import org.junit.Assert

import java.util.regex.Matcher
import java.util.regex.Pattern

/*--------------------------------------------------------------------------------------------------------------------*/

/** ** potęgowanie w Groovy - różnica do Javy
 * operator elvisowy ?:
 */
def value = 5

//jezeli 1. czesc wyrazenia jest prawda, to zwróci wartość, jeżeli nie to zdwróci drugą wartość
println(value ? value : "Brak danych")
println(value ?: "Brak danych") // to samo przy użyciu ?:

/*----------------------------bezpieczna nawigacja--------------------------------------------------------------------*/

class Person {

    //automatycznie wygenerowany get-er i set-er
    String firstName
    //sztywna definicja get-era i set-era wymusi użycie go
}

def john = new Person()

println(john.firstName.toString()) //będziemy mieć null-a
//println(john.firstName.contains('ALA')) //będziemy mieć NullPointerException
println(john.firstName?.contains('ALA')) //będziemy mieć NullPointerException, użycie ? przerwie i zwróci nulla

println(john.@firstName) //będziemy mieć null-a (omineliśmy get-era), rekomentowany sposób
println(john.getFirstName()) //będziemy mieć null-a (użyliśmy get-era), jeżeli chcemy być spójni z Javą

/*----------------------------------------referencja do metody--------------------------------------------------------*/

/**
 * Dynamiczna metoda, zmienna method dosłownie odwołuje się do toLowerCase, trzyma referencje
 * bez użycia & musielibyśmy ciągle odwoływać się do toLowerCase
 */
def text = 'My teXt'
def method = text.&toLowerCase
println method()
println method.call() //to samo co wyżej

/*------------------------------pattern-------------------------------------------------------------------------------*/

/** Tworzymy obiekt typu pattern, szukamy wartości
 */
def pattern = ~/ala/
println pattern instanceof Pattern

/*------------------------------matcher-------------------------------------------------------------------------------*/
println "MATCHER operator"
def matcher = text =~/text/ //~ wymusza obiekttypu matcher, na ktorym mozemy robic find

/*------------------------------find----------------------------------------------------------------------------------*/
println "FIND operator"
def result = text ==~/text/ //wymusza find
println matcher instanceof Matcher

matcher.find()
//charakterystyczny zapis dla Groovy,
if (matcher) {
    println "New match"
}

if (result) {
    println "New match"
}

/*---------------------------------------spread-----------------------------------------------------------------------*/
println "SPREAD operator, przyklad 1:"
class Car {

    String make
    String model
}

def cars = [
        new Car(make: 'Ford', model: 'Focus'),
        new Car(make: 'Mazda')
]

def makes = cars*.make

assert makes == ['Ford', 'Mazda'] //porównuje lewą i prawą, ale tylko dla make (jakby get.make)
//assert makes == ['Ford','Mazdaa'] //poda elegancko co poszło nie tak

/*-----------------------------------spread---------------------------------------------------------------------------*/
println "SPREAD operator, przyklad 2:"
/**
 * Przeiteruje po zawartości modelu, zwróci tablicę wyników modeli
 */
class Container implements Iterable<Car> {

    def cars = [
            new Car(make: 'Ford', model: 'Focus'),
            new Car(make: 'Mazda')
    ]

    /**
     * Adnotacja, weryfikuje czy na pewno nadpisujemy dane,
     * gdy np bede miala lierowke, operator upewni sie i zwroci mi blad
     * @return iterujemy po autach
     */
    @Override
    Iterator<Car> iterator() {
        return cars.iterator()
    }

    //musi się znaleźć w klasie kontenera, nie może być poza, gdzie wykorzystujemy to
    int function(int x, int y, int z){
        x + y + z
    }
}

def container = new Container()
println container*.model

/*---------------------------------równoważnie dla powyższego, na około-----------------------------------------------*/
println "SPREAD operator, przyklad 3:"
def iterator = container.iterator()
def resultsCars = []
while (iterator.hasNext()){
    resultsCars.add(iterator.next().toString())
}

println resultsCars

/*--------------------------------------------------------------------------------------------------------------------*/
println "Tablica elementów:"
//definicja function znajduje się w kontenerze
def params = [2, 4]
println container.function(*params,6) //* da nam możliwośc wstawieia tego jako tablice, podstawi parametry wyjściowe

def params2 = [5, 6, *params, 9]
println params2

/*--------------------------------------------------------------------------------------------------------------------*/
println "Mapowanie:" //wypelnianie na poziomie mapy, użycie : obowiązkowe
def myMap = [test: 2]
def myMap2 = [test2: 4, *:myMap] //to będzie dokładnie to samo co wyżej (w sensie podstawiene myMap, do kolejne mapy)
println myMap
println myMap2

/*-----------------------------------range----------------------------------------------------------------------------*/
println "Range:"
//wylistuje tablice wartości od 1 do 5
def range = 1..5
def elements = range.collect()
println elements

//tablica wartości od 1 do 4
def range2 = 1..<5
def elements2 = range2.collect()
println elements2

/*--------------------------------spaceship---------------------------------------------------------------------------*/
println "Spaceship:"
println (1 <=> 3) == 0
println 1.compareTo(3) == 0 //to będzie to samo

println 1 <=> 3 //false w bolean
println (3 <=> 1) //true w boolean
println (3 <=> 3) //false w boolean
println 1.compareTo(3) //false w bolean

/*--------------------------------subscript---------------------------------------------------------------------------*/
println "Subscript:"

class Client {
    Long id
    String name
    String role

    def getAt(int i){
        switch (i) {
            case 0: return id
            case 1: return name
            default: return role
        }
    }

    def putAt(int i, def value){
        switch (i) {
            case 0: id = value
                break
            case 1: name = value
                break
            default:
                break
        }
    }

}

def client = new Client(id:1, name: "Marek", role: "Admin")
println client[1]
client[0]=88
println client[0]

/*--------------------------------in----------------------------------------------------------------------------------*/
println "In/contains:"
//odowiednik contains, rezultat w boolean

def letters = ['a', 'b', 'c']
println 'a' in letters

/*--------------------------------------------------------------------------------------------------------------------*/
println "isCase:"
def otherLetters = ['a', 'b', 'c'] as LinkedList
assert letters == otherLetters //true
//assert letters.is(otherLetters) //false, wyrzuci rezultat czemu

/*--------------------------------------------------------------------------------------------------------------------*/
println "asType:"
Integer x = 122
//String txt = (String) x //javova notacja
String txt = x as String //odpowednik dla Groovy
println txt