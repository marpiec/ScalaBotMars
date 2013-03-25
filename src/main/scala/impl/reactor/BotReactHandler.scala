package impl.reactor

import impl.servercommunication.function.ReactFunction
import impl.servercommunication.command.{SetCommand, Commands, Move}
import impl.data.{Directions, DirectionPreferences, XY}
import impl.analyser._
import senses._
import impl.configuration.Parameters
import impl.servercommunication.CustomStatus
import impl.servercommunication.command.debug.DrawLine

class BotReactHandler(reactFunction: ReactFunction) {

  val viewAnalyser = new ViewAnalyser(reactFunction.view)
  val lastSteps = reactFunction.lastSteps

  def respond() = {

    //println(reactFunction.viewToFormattedString())

    var multiplePreferences = List[DirectionPreferences]()

    val cabinFeverPreferences = new CabinFever(viewAnalyser).calculatePreferences()

    val newDestination = new LifeGuide(viewAnalyser, reactFunction, cabinFeverPreferences).chooseDestination()

    val destinationChangeTime = if(reactFunction.destination == newDestination) reactFunction.destinationChangeTime
                                else reactFunction.time

    multiplePreferences ::= cabinFeverPreferences * Parameters.BOT_CABIN_FEVER
    multiplePreferences ::= new Hunger(viewAnalyser).calculatePreferences() * Parameters.BOT_HUNGER
    multiplePreferences ::= new Fear(viewAnalyser).calculatePreferences() * Parameters.BOT_FEAR
    multiplePreferences ::= new Explorer(viewAnalyser, reactFunction).calculatePreferences() * Parameters.BOT_EXPLORER

    val preferences = multiplePreferences.foldLeft(new DirectionPreferences)(_ + _)

    val step: XY = DirectionAdvisor.findBestMoveFromPreferences(preferences, viewAnalyser, false)

    val antiMissileCommands = new MissileDefence(viewAnalyser, reactFunction.slaves, reactFunction.maxSlaves).calculateCommands()
    val spawnCommand = if (antiMissileCommands.isEmpty) {
      new ScoutsCreator(viewAnalyser, reactFunction.slaves, reactFunction.maxSlaves).calculateCommands()
    } else {
      antiMissileCommands
    }

    val newLastSteps = step :: lastSteps



    var commands = new Commands(new Move(step))
    if(spawnCommand.isDefined) {
      val spawn = spawnCommand.get
      val spawnStep = spawn.direction
      if(spawnStep == step) {
        val newSpawnStep = Directions.getStepForDirection(Directions.getDirectionFor(spawnStep))

        val spawnPoint = viewAnalyser.getViewPointRelative(newSpawnStep.x, newSpawnStep.y)
        if (DirectionAdvisor.pointNotSafe(spawnPoint, true)) {
          spawn.direction = newSpawnStep
        }

      }

      commands ::= spawn
    }
    commands ::= new SetCommand(Map(CustomStatus.DESTINATION -> newDestination.toString, CustomStatus.LAST_STEPS -> newLastSteps.toString,
                                    CustomStatus.DESTINATION_CHANGE_TIME -> destinationChangeTime.toString))


    if(reactFunction.destination != newDestination) {
      commands ::= new DrawLine(XY.ZERO, newDestination * 5, "#ffffff")
      commands ::= new DrawLine(newDestination * 5, newDestination * 10, "#ff0000")
    }

    commands

  }

}
