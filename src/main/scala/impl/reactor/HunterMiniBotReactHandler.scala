package impl.reactor

import impl.analyser.{DirectionAdvisor, ViewAnalyser}
import impl.servercommunication.command._
import impl.data.{DirectionPreferences, XY}
import impl.servercommunication.function.ReactFunction
import senses._

/**
 * @author Marcin Pieciukiewicz
 */
class HunterMiniBotReactHandler(reactFunction: ReactFunction, viewAnalyser: ViewAnalyser) {

  def respond(): Commands = {

    var multiplePreferences = List[DirectionPreferences]()

    multiplePreferences ::= new Loner(viewAnalyser).getPreferences() * 1.0
    multiplePreferences ::= new Hunger(viewAnalyser).getPreferences() * 1.0
    multiplePreferences ::= new Fear(viewAnalyser).getPreferences() * 1.0
    multiplePreferences ::= new CabinFever(viewAnalyser).getPreferences() * 1.0
    multiplePreferences ::= new MissMaster(viewAnalyser, reactFunction).getPreferences() * 1.0

    val preferences = multiplePreferences.foldLeft(new DirectionPreferences)(_ + _)

    val step: XY = DirectionAdvisor.findBestMoveFormPreferences(preferences, viewAnalyser)

    new Commands(new Move(step))
  }


}
