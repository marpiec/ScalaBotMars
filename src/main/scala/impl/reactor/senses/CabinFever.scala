package impl.reactor.senses

import impl.analyser.{PathFinder, ViewAnalyser}
import impl.data.{EntitiesTypes, XY, DirectionPreferences}

/**
 *
 */
class CabinFever(viewAnalyser: ViewAnalyser) {

  val viewSize = viewAnalyser.viewSize

  val HORIZON = 10

  val pathFinder = new PathFinder(viewAnalyser)

  def getPreferences(): DirectionPreferences = {

    val directionPreferences = new DirectionPreferences()

    for (y <- -HORIZON until HORIZON) {
      increasePreferencesForTarget(new XY(HORIZON, y), directionPreferences)
    }

    for (y <- -HORIZON until HORIZON) {
      increasePreferencesForTarget(new XY(-HORIZON, y), directionPreferences)
    }

    for (x <- -HORIZON + 1 until HORIZON - 1) {
      increasePreferencesForTarget(new XY(x, HORIZON), directionPreferences)
    }

    for (x <- -HORIZON + 1 until HORIZON - 1) {
      increasePreferencesForTarget(new XY(x, -HORIZON), directionPreferences)
    }

    directionPreferences
  }


  def increasePreferencesForTarget(relativeTarget: XY, directionPreferences: DirectionPreferences) {
    val targetViewPoint = viewAnalyser.getViewPointRelative(relativeTarget.x, relativeTarget.y)
    if (!EntitiesTypes.isInvisible(targetViewPoint)) {
      val step: XY = pathFinder.findNextStepTo(relativeTarget)
      directionPreferences.increasePreference(step, 0.01 * PathFinder.calculateRequiredSteps(relativeTarget))
    }
  }
}
