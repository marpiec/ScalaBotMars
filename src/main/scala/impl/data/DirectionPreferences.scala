package impl.data

import impl.languageutil.CollectionUtil

/**
 * Direction preferences list contains values for each degree starting from north, clock wise.
 */
class DirectionPreferences {

  val DIRECTIONS_COUNT = Directions.DIRECTIONS_COUNT

  val preferences: Array[Double] = Array.fill(DIRECTIONS_COUNT)(0.0)

  def getPreference(direction: Int) = preferences(direction)

  def increasePreference(step: XY, preferenceDiff: Double) {
    val direction = Directions.getDirectionFor(step)
    preferences(normalizeModulo(direction)) += preferenceDiff
    preferences(normalizeModulo(direction - 1)) += preferenceDiff / 2
    preferences(normalizeModulo(direction + 1)) += preferenceDiff / 2
    preferences(normalizeModulo(direction - 2)) += preferenceDiff / 4
    preferences(normalizeModulo(direction + 2)) += preferenceDiff / 4
  }

  def decreasePreferenceSharp(step: XY, preferenceDiff: Double) {
    decreasePreferenceSharpForDirection(Directions.getDirectionFor(step), preferenceDiff)
  }

  def decreasePreferenceSharpForDirection(direction: Int, preferenceDiff: Double) {
    preferences(normalizeModulo(direction)) -= preferenceDiff
  }

  def findBestStep(): XY = {
    Directions.getStepForDirection(findBestDirection())
  }

  def findBestDirection(): Int = {
    CollectionUtil.findIndexOfMaxElement(preferences)
  }


  def +(other: DirectionPreferences): DirectionPreferences = {
    val newPreferences = new DirectionPreferences()
    for (i <- 0 until DIRECTIONS_COUNT) {
      newPreferences.preferences(i) = preferences(i) + other.preferences(i)
    }
    newPreferences
  }

  def *(factor: Double) = {
    val newPreferences = new DirectionPreferences()
    for (i <- 0 until DIRECTIONS_COUNT) {
      newPreferences.preferences(i) = preferences(i) * factor
    }
    newPreferences
  }

  private def normalizeModulo(direction: Int) = {
    if (direction < 0) {
      direction + DIRECTIONS_COUNT
    } else if (direction >= DIRECTIONS_COUNT) {
      direction - DIRECTIONS_COUNT
    } else {
      direction
    }
  }

  override def toString: String = {
    val sb = new StringBuilder()
    preferences.foreach(sb.append(_).append(", "))
    sb.substring(0, sb.length - 2)
  }
}
