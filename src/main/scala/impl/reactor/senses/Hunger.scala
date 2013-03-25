package impl.reactor.senses

import impl.analyser.{PathFinder, ViewAnalyser}
import impl.data.{EntitiesTypes, XY, DirectionPreferences}

class Hunger(viewAnalyser: ViewAnalyser) {

  def getPreferences(): DirectionPreferences = {

    val directionPreferences = new DirectionPreferences()

    val goodEntitiesRelative: List[XY] = viewAnalyser.goodPlants ::: viewAnalyser.goodBeasts

    goodEntitiesRelative.foreach(entityPositionRelative => {

      val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, entityPositionRelative)

      val pathCost = if (EntitiesTypes.isGoodBeast(viewAnalyser.getViewPointFromRelative(entityPositionRelative)))
        math.pow(pathLength * 1.5, 1.5)
      else
        math.pow(pathLength, 1.5)

      val nutritionPrize = if (EntitiesTypes.isGoodBeast(viewAnalyser.getViewPointFromRelative(entityPositionRelative)))
        200
      else
        100

      directionPreferences.increasePreference(nextStep, nutritionPrize / pathCost) // w zaleznosci od odleglosci
    })

    // println("Direction preferences: "+directionPreferences)

    directionPreferences
  }

}
