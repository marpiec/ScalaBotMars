package impl.reactor

import impl.analyser.{BotCounter, DirectionAdvisor, ViewAnalyser}
import impl.servercommunication.command._
import impl.data.{Directions, MiniBotRoles, DirectionPreferences, XY}
import impl.servercommunication.function.ReactFunction
import senses._
import impl.configuration.Parameters
import impl.servercommunication.CustomStatus

/**
 * @author Marcin Pieciukiewicz
 */
class HunterMiniBotReactHandler(reactFunction: ReactFunction, viewAnalyser: ViewAnalyser) {

  private val timeFromCreation = reactFunction.timeFromCreation
  val lastSteps = reactFunction.lastSteps

  def respond(): Commands = {

    val numberOfBots = BotCounter.registerBot(reactFunction.time)

    if (viewAnalyser.nearestEnemyDistance < 2 || viewAnalyser.enemiesCount > 0 && math.random < Parameters.PROBABILITY_TO_CHANGE_INTO_MISSILE && reactFunction.timeFromCreation > 2) {
      new SetCommand(Map(CustomStatus.ROLE -> MiniBotRoles.MISSILE)) :: new MissileMiniBotReactHandler(reactFunction, viewAnalyser).respond()
    } else {

      var multiplePreferences = List[DirectionPreferences]()

      multiplePreferences ::= new Loner(viewAnalyser).calculatePreferences() * Parameters.HUNTER_LONER
      multiplePreferences ::= new Hunger(viewAnalyser).calculatePreferences() * Parameters.HUNTER_HUNGER
      multiplePreferences ::= new Fear(viewAnalyser).calculatePreferences() * Parameters.HUNTER_FEAR
      val cabinFeverPreferences: DirectionPreferences = new CabinFever(viewAnalyser).calculatePreferences()
      multiplePreferences ::= cabinFeverPreferences * Parameters.HUNTER_CABIN_FEVER
      //multiplePreferences ::= new Explorer(viewAnalyser, reactFunction).calculatePreferences() * Parameters.HUNTER_EXPLORER

      if (timeFromCreation >= 20) {
        multiplePreferences ::= new GoHome(viewAnalyser, reactFunction).calculatePreferences() * Parameters.HUNTER_GO_HOME
      }

      val preferences = multiplePreferences.foldLeft(new DirectionPreferences)(_ + _)


      val step: XY = DirectionAdvisor.findBestMoveFromPreferences(preferences, viewAnalyser, true)
      val newLastSteps = step :: lastSteps

      val newDestination = new LifeGuide(viewAnalyser, reactFunction, cabinFeverPreferences).chooseDestination()

      val destinationChangeTime = if (reactFunction.destination == newDestination) reactFunction.destinationChangeTime
      else reactFunction.time

      var commands = new Commands(new Move(step))
      commands ::= new SetCommand(Map(CustomStatus.TIME_FROM_CREATION -> (timeFromCreation + 1).toString, CustomStatus.LAST_STEPS -> newLastSteps.toString, CustomStatus.DESTINATION -> newDestination.toString))
      if (timeFromCreation > 10 && reactFunction.energy > 150 && (viewAnalyser.myMiniBots.size < 10 || viewAnalyser.enemyMiniBots.size > 0 || viewAnalyser.enemyBots.size > 0)) {
        val canCreate = BotCounter.createBot(reactFunction.time, 1)
        if (canCreate) {
          val spawnStep = Directions.getStepForDirectionModulo(Directions.getDirectionFor(preferences.findBestStep()) + 2)
          commands ::= new Spawn(spawnStep, "hunter", 100, MiniBotRoles.HUNTER)
        }
      }
      commands
    }


  }


}
