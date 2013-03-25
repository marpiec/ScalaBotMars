package impl.analyser

import impl.data.{EntitiesTypes, XY, DirectionPreferences}

/**
 * 
 */
object DirectionAdvisor {

  def findBestMoveFormPreferences(preferences: DirectionPreferences, viewAnalyser: ViewAnalyser) = {
    var targetPoint: Char = '_'
    var step: XY = null
    var triesCount = 0
    do {
      step = preferences.findBestStep()
      targetPoint = viewAnalyser.getViewPointRelative(step.x, step.y)

      triesCount += 1
      preferences.decreasePreferenceSharp(step, 100000)
    } while (EntitiesTypes.notSafeEntity(targetPoint) && triesCount < DirectionCalculator.DIRECTIONS_COUNT)
    step
  }

}
