package impl.analyser

import impl.data.{Directions, EntitiesTypes, XY, DirectionPreferences}

/**
 *
 */
object DirectionAdvisor {

  val DIRECTIONS_COUNT = Directions.DIRECTIONS_COUNT
  val VERY_LARGE_PREFERENCE_CHANGE = 1000000

  def findBestMoveFormPreferences(preferences: DirectionPreferences, viewAnalyser: ViewAnalyser) = {
    var targetPoint: Char = '_'
    var step: XY = null
    var triesCount = 0
    do {
      step = preferences.findBestStep()
      targetPoint = viewAnalyser.getViewPointRelative(step.x, step.y)

      triesCount += 1
      preferences.decreasePreferenceSharp(step, VERY_LARGE_PREFERENCE_CHANGE)
    } while (EntitiesTypes.notSafeEntity(targetPoint) && triesCount < DIRECTIONS_COUNT)
    step
  }

}
