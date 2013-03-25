package impl.reactor

import impl.servercommunication.function.ReactFunction
import impl.servercommunication.command.{Commands, Move}
import impl.data.{DirectionPreferences, XY}
import impl.analyser._
import senses.{MissileDefence, Hunger, Fear, CabinFever}
import impl.configuration.Parameters

class BotReactHandler(reactFunction: ReactFunction) {

  val viewAnalyser = new ViewAnalyser(reactFunction.view)

  def respond() = {

    //println(reactFunction.viewToFormattedString())

    var multiplePreferences = List[DirectionPreferences]()

    multiplePreferences ::= new Hunger(viewAnalyser).calculatePreferences() * Parameters.BOT_HUNGER
    multiplePreferences ::= new Fear(viewAnalyser).calculatePreferences() * Parameters.BOT_FEAR
    multiplePreferences ::= new CabinFever(viewAnalyser).calculatePreferences() * Parameters.BOT_CABIN_FEVER

    val preferences = multiplePreferences.foldLeft(new DirectionPreferences)(_ + _)

    val missileDefence: MissileDefence = new MissileDefence(viewAnalyser)
    val antiMissileCommands = missileDefence.calculateCommands()

    val step: XY = DirectionAdvisor.findBestMoveFormPreferences(preferences, viewAnalyser)

    var commands = new Commands(new Move(step))
    commands = commands ::: antiMissileCommands
    commands

  }

}
