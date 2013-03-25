package impl.reactor

import impl.servercommunication.function.ReactFunction
import impl.servercommunication.command.{Commands, Move}
import impl.data.{DirectionPreferences, XY}
import impl.analyser._
import senses.{MissileDefence, Hunger, Fear, CabinFever}

class BotReactHandler(reactFunction: ReactFunction) {

  val viewAnalyser = new ViewAnalyser(reactFunction.view)

  def respond() = {

    //println(reactFunction.viewToFormattedString())

    var multiplePreferences = List[DirectionPreferences]()

    multiplePreferences ::= new Hunger(viewAnalyser).calculatePreferences() * 1.0
    multiplePreferences ::= new Fear(viewAnalyser).calculatePreferences() * 1.0
    multiplePreferences ::= new CabinFever(viewAnalyser).calculatePreferences() * 1.0

    val preferences = multiplePreferences.foldLeft(new DirectionPreferences)(_ + _)

    val missileDefence: MissileDefence = new MissileDefence(viewAnalyser)
    val antiMissileCommands = missileDefence.calculateCommands()

    val step: XY = DirectionAdvisor.findBestMoveFormPreferences(preferences, viewAnalyser)

    var commands = new Commands(new Move(step))
    commands = commands ::: antiMissileCommands
    commands

  }

}
