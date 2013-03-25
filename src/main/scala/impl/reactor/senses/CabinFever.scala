package impl.reactor.senses

import impl.analyser.{PathFinder, ViewAnalyser}
import impl.data.{EntitiesTypes, XY, DirectionPreferences}

/**
 *
 */
class CabinFever(viewAnalyser: ViewAnalyser) {

  val viewSize = viewAnalyser.viewSize

  val HORIZON = 10


  def calculatePreferences(): DirectionPreferences = {

    val preferences = new DirectionPreferences()

    for (y <- -HORIZON until HORIZON) {
      increasePreferencesForTarget(new XY(HORIZON, y), preferences)
    }

    for (y <- -HORIZON until HORIZON) {
      increasePreferencesForTarget(new XY(-HORIZON, y), preferences)
    }

    for (x <- -HORIZON + 1 until HORIZON - 1) {
      increasePreferencesForTarget(new XY(x, HORIZON), preferences)
    }

    for (x <- -HORIZON + 1 until HORIZON - 1) {
      increasePreferencesForTarget(new XY(x, -HORIZON), preferences)
    }

    preferences
  }


  def increasePreferencesForTarget(relativeTarget: XY, directionPreferences: DirectionPreferences) {
    val targetViewPoint = viewAnalyser.getViewPointRelative(relativeTarget.x, relativeTarget.y)
    if (!EntitiesTypes.isInvisible(targetViewPoint)) {
      val step: XY = PathFinder.findNextStepTo(viewAnalyser, relativeTarget)
      directionPreferences.increasePreference(step, 0.01 * PathFinder.calculateRequiredSteps(relativeTarget))
    }
  }
}
