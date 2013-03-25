package impl.reactor.senses

import impl.analyser.{PathFinder, ViewAnalyser}
import impl.data.{EntitiesTypes, XY, DirectionPreferences}

class Fear(val viewAnalyser: ViewAnalyser) {

  def calculatePreferences(): DirectionPreferences = {

    val preferences = new DirectionPreferences()

    val badEntities: List[XY] = viewAnalyser.badPlants ::: viewAnalyser.badBeasts ::: viewAnalyser.enemyMiniBots ::: viewAnalyser.enemyBots

    badEntities.foreach(entityPositionRelative => {
      val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, entityPositionRelative)

      val pathCost = if (EntitiesTypes.isBadBeast(viewAnalyser.getViewPointFromRelative(entityPositionRelative)))
        math.pow(pathLength * 1.5, 1.5)
      else if (EntitiesTypes.isBadPlant(viewAnalyser.getViewPointFromRelative(entityPositionRelative)))
        pathLength
      else if (EntitiesTypes.isEnemyBot(viewAnalyser.getViewPointFromRelative(entityPositionRelative)))
        math.pow(pathLength, 0.5)
      else if (EntitiesTypes.isEnemyMiniBot(viewAnalyser.getViewPointFromRelative(entityPositionRelative)))
        math.pow(pathLength, 0.5)
      else
        1.0

      val nutritionPrize = if (EntitiesTypes.isBadBeast(viewAnalyser.getViewPointFromRelative(entityPositionRelative)))
        if (pathLength > 7) 0 else 200
      else if (EntitiesTypes.isBadPlant(viewAnalyser.getViewPointFromRelative(entityPositionRelative))) {
        if (pathLength > 1) 0 else 150
      } else if (EntitiesTypes.isEnemyBot(viewAnalyser.getViewPointFromRelative(entityPositionRelative)))
        1000
      else if (EntitiesTypes.isEnemyMiniBot(viewAnalyser.getViewPointFromRelative(entityPositionRelative)))
        1000
      else
        1

      if (EntitiesTypes.isBadPlant(viewAnalyser.getViewPointFromRelative(entityPositionRelative))) {
        preferences.decreasePreferenceSharp(nextStep, nutritionPrize / pathCost)
      } else {
        preferences.decreasePreference(nextStep, nutritionPrize / pathCost)
      }

    })

    //println("Direction fear: "+directionPreferences)

    preferences
  }

}
