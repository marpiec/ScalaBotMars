package impl.reactor

import impl.servercommunication.function.ReactFunction
import impl.servercommunication.command.{Spawn, SetCommand, Commands, Move}
import impl.data.{Directions, DirectionPreferences, XY}
import impl.analyser._
import senses._
import impl.configuration.Parameters
import impl.servercommunication.CustomStatus

class BotReactHandler(reactFunction: ReactFunction) {

  val viewAnalyser = new ViewAnalyser(reactFunction.view)
  val lastSteps = reactFunction.lastSteps

  def respond() = {

    //println(reactFunction.viewToFormattedString())

    // --- Direction preferences
    var multiplePreferences = List[DirectionPreferences]()

    val cabinFeverPreferences = new CabinFever(viewAnalyser).calculatePreferences()
    multiplePreferences ::= cabinFeverPreferences * Parameters.BOT_CABIN_FEVER
    multiplePreferences ::= new Hunger(viewAnalyser).calculatePreferences() * Parameters.BOT_HUNGER
    multiplePreferences ::= new Fear(viewAnalyser).calculatePreferences() * Parameters.BOT_FEAR
    multiplePreferences ::= new Explorer(viewAnalyser, reactFunction).calculatePreferences() * Parameters.BOT_EXPLORER

    val preferences = multiplePreferences.foldLeft(new DirectionPreferences)(_ + _)

    // --- next step

    val step: XY = DirectionAdvisor.findBestMoveFromPreferences(preferences, viewAnalyser, false)

    val newLastSteps = step :: lastSteps

    // --- spawning mini bots

    val antiMissileCommands = new MissileDefence(viewAnalyser, reactFunction.slaves, reactFunction.maxSlaves).calculateCommands()
    val spawnCommand = if (antiMissileCommands.isEmpty) {
      new ScoutsCreator(viewAnalyser, reactFunction.slaves, reactFunction.maxSlaves).calculateCommands()
    } else {
      antiMissileCommands
    }

    // --- Destination

    val newDestination = new LifeGuide(viewAnalyser, reactFunction, cabinFeverPreferences).chooseDestination()

    val destinationChangeTime = if(reactFunction.destination == newDestination) reactFunction.destinationChangeTime
                                else reactFunction.time



    var commands = new Commands(new Move(step))

    commands = applyBotMoveToSpawn(spawnCommand, step, commands)
    commands ::= new SetCommand(Map(CustomStatus.DESTINATION -> newDestination.toString, CustomStatus.LAST_STEPS -> newLastSteps.toString,
                                    CustomStatus.DESTINATION_CHANGE_TIME -> destinationChangeTime.toString))

    commands

  }


  def applyBotMoveToSpawn(spawnCommand: Option[Spawn], step: XY, commands: Commands):Commands = {
    if (spawnCommand.isDefined) {
      val spawn = spawnCommand.get
      val spawnStep = spawn.direction
      if (spawnStep == step) {
        val newSpawnStep = Directions.getStepForDirectionModulo(Directions.getDirectionFor(spawnStep) + 1)

        val spawnPoint = viewAnalyser.getViewPointRelative(newSpawnStep.x, newSpawnStep.y)
        if (DirectionAdvisor.pointNotSafe(spawnPoint, true)) {
          spawn.direction = newSpawnStep
        }

      }
      spawn :: commands
    } else {
      commands
    }
  }
}
