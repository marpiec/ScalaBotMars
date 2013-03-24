package impl.reactor

import impl.function.ReactFunction
import impl.command.{Commands, Move}
import impl.data.{DirectionPreferences, EntitiesTypes, XY}
import impl.analyser.{DistanceMapCreator2, DistanceMapCreator, DirectionCalculator, ViewAnalyser}
import impl.command.debug.{Say, DrawLine}
import senses.{MissileDefence, Hunger, Fear, CabinFever}

class BotReactHandler(reactFunction: ReactFunction) {

  val viewAnalyser = new ViewAnalyser(reactFunction.view)

  def respond() = {


    //println(reactFunction.viewToFormattedString())

    var multiplePreferences = List[DirectionPreferences]()

    multiplePreferences ::= new Hunger(viewAnalyser).getPreferences().scale(1.0)
    multiplePreferences ::= new Fear(viewAnalyser).getPreferences().scale(1.0)
    multiplePreferences ::= new CabinFever(viewAnalyser).getPreferences().scale(1.0)


    val missileDefence: MissileDefence = new MissileDefence(viewAnalyser)
    val antiMissileCommands = missileDefence.getCommands()


    val preferences = multiplePreferences.foldLeft(new DirectionPreferences)(_ + _)


    val step: XY = findBestMoveFormPreferences(preferences)

    val commands = new Commands()
    commands.addCommand(new Move(step))
    commands.addMultipleCommands(antiMissileCommands)
    commands

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
