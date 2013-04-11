package impl.reactor.senses

import impl.analyser.{PathFinder, ViewAnalyser}
import impl.data.{XY, DirectionPreferences}
import impl.configuration.PrizesFunctions
import impl.languageutil.Logger

class Fear(val viewAnalyser: ViewAnalyser) {

  def calculatePreferences(): DirectionPreferences = {

    val preferences = new DirectionPreferences()

    val badEntities: List[XY] = viewAnalyser.badBeasts ::: viewAnalyser.enemyMiniBots ::: viewAnalyser.enemyBots

    badEntities.foreach(entityPositionRelative => {
      val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, entityPositionRelative)
      val stepPrize = PrizesFunctions.fear(viewAnalyser.getViewPointRelative(entityPositionRelative), pathLength)
      preferences.increasePreference(nextStep, stepPrize)
    })

    Logger.log("Fear:       \t" + preferences)

    preferences
  }

}
