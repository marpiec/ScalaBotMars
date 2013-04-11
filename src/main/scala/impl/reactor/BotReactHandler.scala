package impl.reactor

import impl.servercommunication.function.ReactFunction
import impl.servercommunication.command.{Spawn, SetCommand, Commands, Move}
import impl.data.{Step, DirectionPreferences, XY}
import impl.analyser._
import senses._
import impl.configuration.Parameters
import impl.servercommunication.CustomStatus
import impl.languageutil.Logger

class BotReactHandler(reactFunction: ReactFunction) {

  val viewAnalyser = new ViewAnalyser(reactFunction.view)
  val lastSteps = reactFunction.lastSteps

  def respond() = {

    //println(reactFunction.viewToFormattedString())

    // --- Direction preferences
    var multiplePreferences = List[DirectionPreferences]()

    //Logger.enable()

    val cabinFeverPreferences = new CabinFever(viewAnalyser).calculatePreferences()
    multiplePreferences ::= cabinFeverPreferences * Parameters.BOT_CABIN_FEVER
    multiplePreferences ::= new Hunger(viewAnalyser).calculatePreferences() * Parameters.BOT_HUNGER
    multiplePreferences ::= new Fear(viewAnalyser).calculatePreferences() * Parameters.BOT_FEAR
    multiplePreferences ::= new Explorer(viewAnalyser, reactFunction).calculatePreferences() * Parameters.BOT_EXPLORER

    val preferences = multiplePreferences.foldLeft(new DirectionPreferences)(_ + _)

    Logger.log("All:        \t" + preferences)



    // --- next step

    val step: Step = DirectionAdvisor.findBestMoveFromPreferences(preferences, viewAnalyser, false)


    Logger.log("")
    Logger.disable()

    val newLastSteps = step.xy :: lastSteps

    // --- spawning mini bots

    val antiMissileCommands = new MissileDefence(viewAnalyser, reactFunction.slaves, reactFunction.maxSlaves).calculateCommands()
    val spawnCommand = if (antiMissileCommands.isEmpty) {
      new ScoutsCreator(viewAnalyser, reactFunction.slaves, reactFunction.maxSlaves).calculateCommands()
    } else {
      antiMissileCommands
    }

    // --- Destination

    val newDestination = new LifeGuide(viewAnalyser, reactFunction, cabinFeverPreferences).chooseDestination()

    val destinationChangeTime = if (reactFunction.destination == newDestination) reactFunction.destinationChangeTime
    else reactFunction.time



    var commands = new Commands(new Move(step))

    commands = applyBotMoveToSpawn(spawnCommand, step, commands)

    commands ::= new SetCommand(Map(CustomStatus.DESTINATION -> newDestination.toString, CustomStatus.LAST_STEPS -> newLastSteps.toString,
      CustomStatus.DESTINATION_CHANGE_TIME -> destinationChangeTime.toString))

    commands

  }


  def applyBotMoveToSpawn(spawnCommand: Option[Spawn], masterBotStep: Step, commands: Commands): Commands = {
    if (spawnCommand.isDefined) {
      val spawn = spawnCommand.get
      val spawnStep = spawn.direction
      if (spawnStep == masterBotStep) {

        if (trySimilarSpawnPoint(spawnStep, spawn, 1)) {
          return spawn :: commands
        } else if (trySimilarSpawnPoint(spawnStep, spawn, -1)) {
          return spawn :: commands
        }
      } else {
        return spawn :: commands
      }
    }
    return commands
  }

  def trySimilarSpawnPoint(spawnStep: Step, spawn: Spawn, delta: Int): Boolean = {
    val newSpawnStep = spawnStep.rotate(delta)
    val spawnPoint = viewAnalyser.getViewPointRelative(newSpawnStep.xy)
    if (DirectionAdvisor.pointNotSafe(spawnPoint, true)) {
      spawn.direction = newSpawnStep
      true
    } else {
      false
    }
  }
}
