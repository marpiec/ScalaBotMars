package impl.reactor.senses

import impl.analyser.{PathFinder, ViewAnalyser}
import impl.data.{XY, DirectionPreferences}
import impl.configuration.PrizesFunctions
import impl.languageutil.Logger

/**
 * @author Marcin Pieciukiewicz
 */
class Loner(val viewAnalyser: ViewAnalyser) {

  def calculatePreferences(): DirectionPreferences = {

    val preferences = new DirectionPreferences()

    val myMiniBotsRelative: List[XY] = viewAnalyser.myMiniBots

    myMiniBotsRelative.foreach(myMiniBotRelative => {
      val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, myMiniBotRelative)
      if (pathLength > 0) {
        val prize = PrizesFunctions.loner(pathLength)
        preferences.increasePreference(nextStep, prize)
      }
    })


    Logger.log("Loner:      \t" + preferences)

    preferences
  }

}
