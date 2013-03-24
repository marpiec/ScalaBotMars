package impl.reactor.senses

import impl.analyser.{DirectionCalculator, PathFinder, ViewAnalyser}
import impl.data.{EntitiesTypes, XY, DirectionPreferences}

/**
 * @author Marcin Pieciukiewicz
 */
class Loner(val viewAnalyser:ViewAnalyser) {

  def getPreferences():DirectionPreferences = {

    val preferences = new DirectionPreferences()

    val myMiniBotsRelative: List[XY] = viewAnalyser.myMiniBots

    myMiniBotsRelative.foreach(myMiniBotRelative => {
      val pathFinder = new PathFinder(viewAnalyser)
      val pathSize = PathFinder.calculateRequiredSteps(myMiniBotRelative)
      val nextStep = pathFinder.findNextStepTo(myMiniBotRelative)

      preferences.decreasePreference(DirectionCalculator.getDirection(nextStep.x, nextStep.y), pathSize)

    })

    preferences
  }

}