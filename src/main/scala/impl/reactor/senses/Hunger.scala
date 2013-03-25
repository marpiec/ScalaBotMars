package impl.reactor.senses

import impl.analyser.{PathFinder, ViewAnalyser}
import impl.data.{XY, DirectionPreferences}
import impl.configuration.PrizesFunctions

class Hunger(viewAnalyser: ViewAnalyser) {

  def calculatePreferences(): DirectionPreferences = {

    val preferences = new DirectionPreferences()

    val goodEntitiesRelative: List[XY] = viewAnalyser.goodPlants ::: viewAnalyser.goodBeasts

    goodEntitiesRelative.foreach(entityPositionRelative => {
      val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, entityPositionRelative)
      val stepPrize = PrizesFunctions.hunger(viewAnalyser.getViewPointFromRelative(entityPositionRelative), pathLength)
      preferences.increasePreference(nextStep, stepPrize)
    })

    // println("Direction preferences: "+directionPreferences)

    preferences
  }

}
