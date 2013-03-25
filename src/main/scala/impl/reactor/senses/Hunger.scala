package impl.reactor.senses

import impl.analyser.{PathFinder, DirectionCalculator, ViewAnalyser}
import impl.data.{EntitiesTypes, XY, DirectionPreferences}

class Hunger(viewAnalyser:ViewAnalyser) {

  def getPreferences():DirectionPreferences = {

    val directionPreferences = new DirectionPreferences()

    val goodPlantsRelative: List[XY] = viewAnalyser.goodPlants ::: viewAnalyser.goodBeasts

    goodPlantsRelative.foreach(plantPositionRelative => {
      val pathFinder = new PathFinder(viewAnalyser)
      val pathSize = PathFinder.calculateRequiredSteps(plantPositionRelative)
      val nextStep = pathFinder.findNextStepTo(plantPositionRelative)
      //val path = pathFinder.findShortestPathTo(plantPositionRelative, distanceMap)

        //println("For food in "+plantPositionRelative+ "  \tdirection "+nextStep+"   \t distance:"+pathSize)

        val pathCost = if (EntitiesTypes.isGoodBeast(viewAnalyser.getViewPointFromRelative(plantPositionRelative)))
          math.pow(pathSize * 1.5, 1.5)
            else
          math.pow(pathSize, 1.5)

        val nutritionPrize = if (EntitiesTypes.isGoodBeast(viewAnalyser.getViewPointFromRelative(plantPositionRelative)))
          200
        else
          100

          directionPreferences.increasePreference(nextStep, nutritionPrize / pathCost) // w zaleznosci od odleglosci
    })

   // println("Direction preferences: "+directionPreferences)

    directionPreferences
  }

}
