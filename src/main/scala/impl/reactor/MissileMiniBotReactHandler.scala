package impl.reactor

import impl.analyser.{DirectionCalculator, PathFinder, ViewAnalyser}
import impl.command._
import impl.data.{MiniBotRoles, EntitiesTypes, DirectionPreferences, XY}
import impl.command.debug.Say
import impl.function.ReactFunction

/**
 * @author Marcin Pieciukiewicz
 */
class MissileMiniBotReactHandler(reactFunction: ReactFunction, viewAnalyser:ViewAnalyser) {

  def respond():List[Command] = {

    val enemyMiniBots: List[XY] = viewAnalyser.enemyMiniBots
    val enemyBots: List[XY] = viewAnalyser.enemyBots
    val badBeasts: List[XY] = viewAnalyser.badBeasts

    var commandOption:Option[Command] = None

    commandOption = prepareExplodeCommandFor(enemyMiniBots).
      orElse(prepareExplodeCommandFor(enemyBots)).
      orElse(prepareExplodeCommandFor(badBeasts))


    if (commandOption.isEmpty) {

      val allTargets = enemyMiniBots ::: enemyBots ::: badBeasts
      if(allTargets.nonEmpty) {
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
    var closestPathDirection:XY = null
    possibleTargets.foreach(targetRelativePosition => {
      val pathFinder = new PathFinder(viewAnalyser)
      val pathSize = PathFinder.calculateRequiredSteps(targetRelativePosition)
      val nextStep = pathFinder.findNextStepTo(targetRelativePosition)

      if (pathSize < closestPath) {
        closestPath = pathSize
        closestPathDirection = nextStep
      }
    })
    if(closestPath <= 2) {
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

      preferences.increasePreference(DirectionCalculator.getDirection(nextStep.x, nextStep.y), 100 / pathSize)
    })
    val step = findBestMoveFormPreferences(preferences)
    new Move(step)
  }

  private def findBestMoveFormPreferences(preferences: DirectionPreferences) = {
    var targetPoint: Char = '_'
    var step: XY = null
    var triesCount = 0
    do {
      val direction = preferences.findBestDirection()

      preferences.decreasePreferenceSharp(direction, 100)

      //println("Best direction " + direction + " " + DirectionCalculator.getNextStepIntoDirection(direction))

      step = DirectionCalculator.getNextStepIntoDirection(direction)
      targetPoint = viewAnalyser.getViewPointRelative(step.x, step.y)

      triesCount += 1
    } while (targetPointIsNotSafe(targetPoint) && triesCount < DirectionCalculator.DIRECTIONS_COUNT)
    step
  }

  def targetPointIsNotSafe(targetPoint: Char): Boolean = {
    (EntitiesTypes.isWall(targetPoint) || EntitiesTypes.isBadBeast(targetPoint) ||
      EntitiesTypes.isBadPlant(targetPoint))
  }
}
