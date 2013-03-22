package impl.reactor

import impl.analyser.{PathFinder, DirectionCalculator, ViewAnalyser}
import impl.data.{EntitiesTypes, XY, DirectionPreferences}

class Hunger(val viewAnalyser:ViewAnalyser, distanceMap:Array[Array[Int]]) {

  def getPreferences():DirectionPreferences = {

    val directionPreferences = new DirectionPreferences()

    val goodPlantsRelative: List[XY] = viewAnalyser.goodPlants ::: viewAnalyser.goodBeasts

    goodPlantsRelative.foreach(plantPositionRelative => {
      val pathFinder = new PathFinder(viewAnalyser)
      val path = pathFinder.findShortestPathTo(plantPositionRelative, distanceMap)
      if (path.nonEmpty) {
        val firstStep = path.head
        println("For food in "+plantPositionRelative+ "  \tdirection "+firstStep+"   \t distance:"+path.size)

        val pathCost = if (EntitiesTypes.isGoodBeast(viewAnalyser.getViewPointFromRelative(plantPositionRelative)))
          math.pow(path.size * 1.5, 1.5)
            else
          math.pow(path.size, 1.5)

        val nutritionPrize = if (EntitiesTypes.isGoodBeast(viewAnalyser.getViewPointFromRelative(plantPositionRelative)))
          200
        else
          100

          directionPreferences.increasePreference(DirectionCalculator.getDirection(firstStep.x, firstStep.y), nutritionPrize / pathCost) // w zaleznosci od odleglosci
      }
    })

    println("Direction preferences: "+directionPreferences)

    directionPreferences
  }



}
