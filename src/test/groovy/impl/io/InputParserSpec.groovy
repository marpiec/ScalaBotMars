package impl.io

import impl.servercommunication.InputParser
import impl.data.XY
import impl.servercommunication.function.GoodbyeFunction
import impl.servercommunication.function.WelcomeFunction
import impl.servercommunication.function.ReactFunction
import spock.lang.Specification

class InputParserSpec extends Specification {

    def WELCOME_EXAMPLE = "Welcome(name=Mars,path=C:\\mar\\scala\\scalatron\\Scalatron\\bots\\Mars,apocalypse=5000,round=0)"
    def REACT_EXAMPLE = "React(generation=0,time=0,view=?______________________________??_____________________________???____________________________????___________________________?????__________________________??????_________________________?????WW________________________??????W________________________??????W________________________??????W__________________________WWWWW_____________________________________________________??___________________________W???___________________________W???___________________________W???__________m____M___________W???___________________________W???____________________________________________________________________________________________________________________________WWWWWW_________________________?????__________________________???____________________________?____________________________________________________________________________________________WWWWWW_________________________??????_________________________?????__________________________????___________________________,energy=1000,name=Mars,collision=1:-1)"
    def GOODBYE_EXAMPLE = "Goodbye(energy=-4212)"

    def "Correct parsing of Welcome function"() {
        when:
        def parser = new InputParser(WELCOME_EXAMPLE)

        then:
        parser.isGoodbyeFunction() == false
        parser.isReactFunction()   == false
        parser.isWelcomeFunction() == true

        parser.result() instanceof WelcomeFunction

        def welcome = (WelcomeFunction) parser.result()

        welcome.apocalypse == 5000
        welcome.maxSlaves == 0
        welcome.name == "Mars"
        welcome.round == 0
    }

    def "Correct parsing of React function"() {

        when:
        def parser = new InputParser(REACT_EXAMPLE)

        then:
        parser.isGoodbyeFunction() == false
        parser.isReactFunction()   == true
        parser.isWelcomeFunction() == false

        parser.result() instanceof ReactFunction

        def react = (ReactFunction) parser.result()

        react.name == "Mars"
        react.generation == 0
        react.time == 0
        react.energy == 1000
        react.slaves == 0
        react.masterOption.isEmpty()
        react.collisionOption.isDefined()
        react.collisionOption.get() == new XY(1, -1)
        react.view == "?______________________________??_____________________________???____________________________????___________________________?????__________________________??????_________________________?????WW________________________??????W________________________??????W________________________??????W__________________________WWWWW_____________________________________________________??___________________________W???___________________________W???___________________________W???__________m____M___________W???___________________________W???____________________________________________________________________________________________________________________________WWWWWW_________________________?????__________________________???____________________________?____________________________________________________________________________________________WWWWWW_________________________??????_________________________?????__________________________????___________________________"

    }

    def "Correct parsing of Goodbye function"() {
        when:
        def parser = new InputParser(GOODBYE_EXAMPLE)

        then:
        parser.isGoodbyeFunction() == true
        parser.isReactFunction()   == false
        parser.isWelcomeFunction() == false

        parser.result() instanceof GoodbyeFunction

        def goodbye = (GoodbyeFunction) parser.result()

        goodbye.energy == -4212
    }

    def "Correct parsing of view"() {
        given:
        def parser = new InputParser(REACT_EXAMPLE)
        def react = (ReactFunction) parser.result()

        expect:
        react.viewDistance == 15

    }

}
