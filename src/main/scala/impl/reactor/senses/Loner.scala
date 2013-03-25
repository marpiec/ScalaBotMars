package impl.reactor.senses

import impl.analyser.{PathFinder, ViewAnalyser}
import impl.data.{XY, DirectionPreferences}

/**
 * @author Marcin Pieciukiewicz
 */
class Loner(val viewAnalyser: ViewAnalyser) {

  def calculatePreferences(): DirectionPreferences = {

    val preferences = new DirectionPreferences()

    val myMiniBotsRelative: List[XY] = viewAnalyser.myMiniBots

    myMiniBotsRelative.foreach(myMiniBotRelative => {
      val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, myMiniBotRelative)

      preferences.decreasePreference(nextStep, pathLength)

    })

    preferences
  }

}
