package impl.reactor

import impl.analyser.{DirectionCalculator, PathFinder, ViewAnalyser}
import impl.command._
import debug.Say
import impl.data.{EntitiesTypes, DirectionPreferences, XY}
import impl.function.ReactFunction
import senses._

/**
 * @author Marcin Pieciukiewicz
 */
class HunterMiniBotReactHandler(reactFunction: ReactFunction, viewAnalyser:ViewAnalyser) {

  def respond():List[Command] = {

    var multiplePreferences = List[DirectionPreferences]()

    multiplePreferences ::= new Loner(viewAnalyser).getPreferences().scale(1.0)
    multiplePreferences ::= new Hunger(viewAnalyser).getPreferences().scale(1.0)
    multiplePreferences ::= new Fear(viewAnalyser).getPreferences().scale(1.0)
    multiplePreferences ::= new CabinFever(viewAnalyser).getPreferences().scale(1.0)
    multiplePreferences ::= new MissMaster(viewAnalyser, reactFunction).getPreferences().scale(1.0)

    val preferences = multiplePreferences.foldLeft(new DirectionPreferences)(_ + _)

    val step: XY = findBestMoveFormPreferences(preferences)

    List(new Move(step))
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
