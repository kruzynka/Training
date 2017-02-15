def name = 'Bonifacy'
//odpowiednik lang String, użycie '' nie umożliwia interpolacji, zwróci zmienną z $
def text = 'Ala ma kota o imieniu ${name}'

//multi lang
// gdzie \ znak końca linii
// gdzie \u20AC znak euro
def email = '''\
Szanowny Panie,
Piszę do Pana w sprawie... \n
Cena wynosi 40 \u20AC
'''

// użycie "" pozwoli na iterpolacę:
def interpolatedText = "Ala ma kota o imieniu ${name}, który ma ${1 + 5} lat \n"

//użycie mapy:
def parameters = [name: 'Natalia']

//closer, blok kodu, który może być przypisywany do zmiennej lub zwracany
def age = 5
def lazyText = "value == ${ -> age}" // brak -> przyjąłby przypisanie pierwszej wartości dla zmiennej
age = 6 //przypisujemy nową wartość

def regexp = /d{2}-d{3}/ // nie musimy espacowac /, normlnie musielibyśmy to używać \ przed /
def regexp2 = $/d{2}-d{3}/$ //escaptuje $


println(text)
println(email)
println(interpolatedText)
println("Witaj $parameters.name")
//specyficzne dla Groovy, nie potrzeba ()
println "Witaj $parameters.name"
//przypisanie do zmiennej:
println(lazyText)
//multiline:
println("""
Abc
${age}
""")


