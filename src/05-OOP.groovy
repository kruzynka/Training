/**
 * Przykład klas dziedziczących
 * użycie abstract zabroni nam utworzenia new Employee
 * klasa abstrakcyjna może zawierać niepełną implementację
 */
abstract class Employee {

    Integer salary

    String getInfo() {
        "Salary: $salary"
    }

    //zdefiniowanie reports wymusi na Employee potrzebe "raportowania"
    abstract String report()
}

/**
 * klasa która dziedziczy po klasie Employee
 * w ramach niej możemy bez problemu nadpisywać metody, rozszerzać, ...
 */
class Tester extends Employee {

    List<String> testingFrameworks = []

    void runTests() {
        println('Testing...')

        //rozszerzenie bazowej klasy:
        Employee testEmployee = new Employee() {

            @Override
            String getInfo(){
                return "Test"
            }

            @Override
            String report() {
                return "New test report"
            }
        }
    }

    @Override
    String getInfo() {
        return super.getInfo() + ", frameworks: $testingFrameworks"
    }

    @Override
    String report() {
        return "New test report"
    }
}

class Programmer extends Employee {

    @Override
    String report() {
        return "New test report"
    }
}

/**
 * Metoda uniwersalna, przygotowana na pracę z każdym pracownikiem, nie będzie trzeba jej zmieniać
 * zmienna liczba argumentów metody
 * @param employees
 */
void printInfo(Employee ... employees) {

    //możemy przeiterować po pracownikach
    employees.each{ println(it.getInfo()) // rowoważne  employee -> println(employee.getInfo())

    }
}


Tester tester = new Tester(salary: 30, testingFrameworks: ['junit'])

println(tester.getInfo())
tester.runTests()
println(tester instanceof Employee)
Employee employee = (Employee) tester
println(employee.getInfo())
employee.runTests()
def employee2 = (Tester) employee

printInfo(tester, new Programmer())