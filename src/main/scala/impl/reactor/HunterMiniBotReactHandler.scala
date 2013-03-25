package impl.reactor

import impl.analyser.{DirectionAdvisor, ViewAnalyser}
import impl.servercommunication.command._
import impl.data.{DirectionPreferences, XY}
import impl.servercommunication.function.ReactFunction
import senses._
import impl.configuration.Parameters

/**
 * @author Marcin Pieciukiewicz
 */
class HunterMiniBotReactHandler(reactFunction: ReactFunction, viewAnalyser: ViewAnalyser) {

  def respond(): Commands = {

    var multiplePreferences = List[DirectionPreferences]()

    multiplePreferences ::= new Loner(viewAnalyser).calculatePreferences() * Parameters.HUNTER_LONER
    multiplePreferences ::= new Hunger(viewAnalyser).calculatePreferences() * Parameters.HUNTER_HUNGER
    multiplePreferences ::= new Fear(viewAnalyser).calculatePreferences() * Parameters.HUNTER_FEAR
    multiplePreferences ::= new CabinFever(viewAnalyser).calculatePreferences() * Parameters.HUNTER_CABIN_FEVER
    multiplePreferences ::= new GoHome(viewAnalyser, reactFunction).calculatePreferences() * Parameters.HUNTER_GO_HOME

    val preferences = multiplePreferences.foldLeft(new DirectionPreferences)(_ + _)

    val step: XY = DirectionAdvisor.findBestMoveFormPreferences(preferences, viewAnalyser)

    new Commands(new Move(step))
  }


}
