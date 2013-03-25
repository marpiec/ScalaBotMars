package impl.reactor

import impl.servercommunication.function.ReactFunction
import impl.servercommunication.command.{SetCommand, Commands, Move}
import impl.data.{DirectionPreferences, XY}
import impl.analyser._
import senses._
import impl.configuration.Parameters
import impl.servercommunication.CustomStatus

class BotReactHandler(reactFunction: ReactFunction) {

  val viewAnalyser = new ViewAnalyser(reactFunction.view)
  val lastSteps = reactFunction.lastSteps

  def respond() = {

    //println(reactFunction.viewToFormattedString())

    val newDestination = new LifeGuide(viewAnalyser, reactFunction).chooseDestination()

    var multiplePreferences = List[DirectionPreferences]()

    multiplePreferences ::= new Hunger(viewAnalyser).calculatePreferences() * Parameters.BOT_HUNGER
    multiplePreferences ::= new Fear(viewAnalyser).calculatePreferences() * Parameters.BOT_FEAR
    multiplePreferences ::= new CabinFever(viewAnalyser).calculatePreferences() * Parameters.BOT_CABIN_FEVER

    val preferences = multiplePreferences.foldLeft(new DirectionPreferences)(_ + _)

    val step: XY = DirectionAdvisor.findBestMoveFormPreferences(preferences, viewAnalyser, false)

    val antiMissileCommands = new MissileDefence(viewAnalyser, reactFunction.slaves, reactFunction.maxSlaves).calculateCommands()
    val spawnCommands = if (antiMissileCommands.isEmpty) {
      new ScoutsCreator(viewAnalyser, reactFunction.slaves, reactFunction.maxSlaves).calculateCommands()
    } else {
      antiMissileCommands
    }

    val newLastSteps = step :: lastSteps

    var commands = new Commands(new Move(step))
    commands :::= spawnCommands
    commands ::= new SetCommand(Map(CustomStatus.DESTINATION -> newDestination.toString, CustomStatus.LAST_STEPS -> newLastSteps.toString))

    commands

  }

}
