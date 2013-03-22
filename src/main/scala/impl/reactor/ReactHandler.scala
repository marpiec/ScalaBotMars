package impl.reactor

import impl.function.ReactFunction
import impl.command.{Move, DebugCommand}
import impl.data.{EntitiesTypes, DirectionPreferences, XY}
import impl.analyser.{DistanceMapCreator, DirectionCalculator, ViewAnalyser}

class ReactHandler(val reactFunction: ReactFunction) {



  def respond() = {
    //DebugCommand.say(reactFunction.viewDistance.toString)
    //println(reactFunction.collisionOption)

    val viewAnalyser = new ViewAnalyser(reactFunction.view)

    println(reactFunction.viewToFormattedString())

    val distanceMap = new DistanceMapCreator(viewAnalyser).createDistanceMap()


    val foodFinder = new FoodFinder(viewAnalyser, distanceMap)

    val preferences: DirectionPreferences = foodFinder.getDirectionPreferences()
    println("preferences: "+preferences)

    val direction = preferences.findBestDirection()

    println("Best direction "+direction+" "+DirectionCalculator.getNextStepIntoDirection(direction))

    val step = DirectionCalculator.getNextStepIntoDirection(direction)

    val targetPoint = viewAnalyser.getViewPointRelative(step.x, step.y)
    if (EntitiesTypes.isEmpty(targetPoint) || EntitiesTypes.isMyMiniBot(targetPoint) || EntitiesTypes.isGoodBeast(targetPoint)
    || EntitiesTypes.isGoodPlant(targetPoint)) {//usunac ten warunek, gdy zaimplementowany bedzie strach
      new Move(step)
    } else {
      new Move(new XY(0,0))
    }


  }
}
