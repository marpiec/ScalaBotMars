package impl.reactor

import impl.function.ReactFunction
import impl.command.{Move, DebugCommand}
import impl.data.{EntitiesTypes, DirectionPreferences, XY}
import impl.analyser.{DistanceMapCreator, DirectionCalculator, ViewAnalyser}

class ReactHandler(val reactFunction: ReactFunction) {



  def respond() = {

    val viewAnalyser = new ViewAnalyser(reactFunction.view)

    println(reactFunction.viewToFormattedString())

    val distanceMap = new DistanceMapCreator(viewAnalyser).createDistanceMap()

    val hungerPreferences = new Hunger(viewAnalyser, distanceMap).getPreferences()

    val fearPreferences = new Fear(viewAnalyser, distanceMap).getPreferences()

    val preferences = hungerPreferences.sumPreferences(fearPreferences)


    var targetPoint:Char = '_'
    var step:XY = null
    do {
      val direction = preferences.findBestDirection()

      preferences.decreasePreference(direction, 100)

      println("Best direction "+direction+" "+DirectionCalculator.getNextStepIntoDirection(direction))

      step = DirectionCalculator.getNextStepIntoDirection(direction)
      targetPoint = viewAnalyser.getViewPointRelative(step.x, step.y)

    } while (EntitiesTypes.isWall(targetPoint) || EntitiesTypes.isBadBeast(targetPoint) || EntitiesTypes.isBadPlant(targetPoint))

    new Move(step)


  }
}
