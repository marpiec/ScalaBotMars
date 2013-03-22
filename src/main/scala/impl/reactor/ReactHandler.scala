package impl.reactor

import impl.function.ReactFunction
import impl.command.{Move}
import impl.data.{EntitiesTypes, XY}
import impl.analyser.{DistanceMapCreator, DirectionCalculator, ViewAnalyser}

class ReactHandler(val reactFunction: ReactFunction) {



  def respond() = {

    val viewAnalyser = new ViewAnalyser(reactFunction.view)

    println(reactFunction.viewToFormattedString())

    val distanceMap = new DistanceMapCreator(viewAnalyser).createDistanceMap()

    val hungerPreferences = new Hunger(viewAnalyser, distanceMap).getPreferences()

    val fearPreferences = new Fear(viewAnalyser, distanceMap).getPreferences()
    val cabinFeverPreferences = new CabinFever(viewAnalyser, distanceMap).getPreferences()


    val preferences = hungerPreferences.sumPreferences(fearPreferences, 0.5).sumPreferences(cabinFeverPreferences, 2.0)


    var targetPoint:Char = '_'
    var step:XY = null
    var triesCount = 0
    do {
      val direction = preferences.findBestDirection()

      preferences.decreasePreferenceSharp(direction, 100)

      println("Best direction "+direction+" "+DirectionCalculator.getNextStepIntoDirection(direction))

      step = DirectionCalculator.getNextStepIntoDirection(direction)
      targetPoint = viewAnalyser.getViewPointRelative(step.x, step.y)

      triesCount+=0
    } while ((EntitiesTypes.isWall(targetPoint) || EntitiesTypes.isBadBeast(targetPoint) || EntitiesTypes.isBadPlant(targetPoint)) && triesCount < DirectionCalculator.DIRECTIONS_COUNT)

    new Move(step)


  }
}
