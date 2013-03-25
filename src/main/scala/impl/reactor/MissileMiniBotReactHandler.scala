package impl.reactor

import impl.analyser.{DirectionAdvisor, PathFinder, ViewAnalyser}
import impl.servercommunication.command._
import impl.data.{MiniBotRoles, DirectionPreferences, XY}
import impl.servercommunication.function.ReactFunction

/**
 * @author Marcin Pieciukiewicz
 */
class MissileMiniBotReactHandler(reactFunction: ReactFunction, viewAnalyser: ViewAnalyser) {

  def respond(): List[Command] = {

    val enemyMiniBots: List[XY] = viewAnalyser.enemyMiniBots
    val enemyBots: List[XY] = viewAnalyser.enemyBots
    val badBeasts: List[XY] = viewAnalyser.badBeasts

    var commandOption: Option[Command] = None

    commandOption = prepareExplodeCommandFor(enemyMiniBots).
      orElse(prepareExplodeCommandFor(enemyBots)).
      orElse(prepareExplodeCommandFor(badBeasts))


    if (commandOption.isEmpty) {

      val allTargets = enemyMiniBots ::: enemyBots ::: badBeasts
      if (allTargets.nonEmpty) {
        commandOption = Option(prepareMoveCommand(allTargets))
      }

    }

    if (commandOption.isEmpty) {
      new SetCommand(MiniBotRoles.HUNTER) :: new HunterMiniBotReactHandler(reactFunction, viewAnalyser).respond()
    } else {

      List(commandOption.get)
    }

  }

  def prepareExplodeCommandFor(possibleTargets: List[XY]): Option[Command] = {
    var closestPath = Int.MaxValue
    var closestPathDirection: XY = null
    possibleTargets.foreach(targetRelativePosition => {
      val pathFinder = new PathFinder(viewAnalyser)
      val pathSize = PathFinder.calculateRequiredSteps(targetRelativePosition)
      val nextStep = pathFinder.findNextStepTo(targetRelativePosition)

      if (pathSize < closestPath) {
        closestPath = pathSize
        closestPathDirection = nextStep
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

    possibleTargets.foreach(targetPositionRelative => {
      val pathFinder = new PathFinder(viewAnalyser)
      val pathSize = PathFinder.calculateRequiredSteps(targetPositionRelative)
      val nextStep = pathFinder.findNextStepTo(targetPositionRelative)

      preferences.increasePreference(nextStep, 100 / pathSize)
    })
    val step = DirectionAdvisor.findBestMoveFormPreferences(preferences, viewAnalyser)
    new Move(step)
  }

}
