package impl.reactor

import impl.analyser.{DirectionAdvisor, PathFinder, ViewAnalyser}
import impl.servercommunication.command._
import impl.data.{Step, MiniBotRoles, DirectionPreferences, XY}
import impl.servercommunication.function.ReactFunction
import impl.servercommunication.CustomStatus

/**
 * @author Marcin Pieciukiewicz
 */
class MissileMiniBotReactHandler(reactFunction: ReactFunction, viewAnalyser: ViewAnalyser) {

  def respond(): Commands = {

    val enemyMiniBots: List[XY] = viewAnalyser.enemyMiniBots
    val enemyBots: List[XY] = viewAnalyser.enemyBots
    val badBeasts: List[XY] = viewAnalyser.badBeasts

    // Think about explode
    var commandOption = prepareExplodeCommandOption(enemyMiniBots ::: enemyBots ::: badBeasts)

    if (commandOption.isEmpty) {
      // Think about moving towards target
      val allTargets = enemyMiniBots ::: enemyBots ::: badBeasts
      if (allTargets.nonEmpty) {
        commandOption = Option(prepareMoveCommand(allTargets))
      }
    }

    if (commandOption.isDefined) {
      var commands = new Commands(commandOption.get)
      commands ::= new SetCommand(Map(CustomStatus.TIME_FROM_CREATION -> (reactFunction.timeFromCreation + 1).toString))
      commands
    } else {
      //change into hunter
      reactFunction.timeFromCreation = 0
      new SetCommand(Map(CustomStatus.ROLE -> MiniBotRoles.HUNTER)) :: new HunterMiniBotReactHandler(reactFunction, viewAnalyser).respond()
    }
  }

  def prepareExplodeCommandOption(possibleTargets: List[XY]): Option[Command] = {
    var closestPath = Int.MaxValue
    var closestPathFirstStep: Step = null
    possibleTargets.foreach(targetRelativePosition => {
      val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, targetRelativePosition)

      if (pathLength < closestPath) {
        closestPath = pathLength
        closestPathFirstStep = nextStep
      }
    })
    if (closestPath <= 2) {
      Option(new Explode(3))
    } else {
      None
    }
  }

  def prepareMoveCommand(possibleTargets: List[XY]): Command = {
    val preferences = new DirectionPreferences()

    possibleTargets.foreach(targetRelativePosition => {
      val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, targetRelativePosition)

      preferences.increasePreference(nextStep, 100 / pathLength)
    })
    val step = DirectionAdvisor.findBestMoveFromPreferences(preferences, viewAnalyser, true)
    new Move(step)
  }

}
