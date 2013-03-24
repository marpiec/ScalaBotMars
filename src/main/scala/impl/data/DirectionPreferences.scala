package impl.data

import impl.analyser.DirectionCalculator

/**
 * Direction preferences list contains values for each degree starting from north, clock wise.
 */
class DirectionPreferences {

  val DIRECTIONS_COUNT = DirectionCalculator.DIRECTIONS_COUNT

  var preferences: Array[Double] = Array.fill(DIRECTIONS_COUNT)(0.0)

  def getPreference(direction: Int) = preferences(direction)

  def setPreference(direction: Int, preference: Double) {
    preferences(normalize(direction)) = preference
  }

  def increasePreference(direction: Int, preferenceDiff: Double) {
    preferences(normalize(direction)) += preferenceDiff
    preferences(normalize(direction-1)) += preferenceDiff/2
    preferences(normalize(direction+1)) += preferenceDiff/2
    preferences(normalize(direction-2)) += preferenceDiff/4
    preferences(normalize(direction+2)) += preferenceDiff/4
  }

  def decreasePreference(direction: Int, preferenceDiff: Double) {
    preferences(normalize(direction)) -= preferenceDiff
    preferences(normalize(direction-1)) -= preferenceDiff/2
    preferences(normalize(direction+1)) -= preferenceDiff/2
    preferences(normalize(direction-2)) -= preferenceDiff/4
    preferences(normalize(direction+2)) -= preferenceDiff/4
  }

  def decreasePreferenceSharp(direction: Int, preferenceDiff: Double) {
    preferences(normalize(direction)) -= preferenceDiff
  }

  def findBestDirection():Int = {
    var bestDirection = -1
    var bestDirectionValue = - Double.MaxValue
    for (i <- 0 until DIRECTIONS_COUNT) {
      val preference = preferences(i)
      if (preference > bestDirectionValue) {
        bestDirection = i
        bestDirectionValue = preference
      }
    }
    bestDirection
  }

  def +(other:DirectionPreferences): DirectionPreferences = {
    val newPreferences = new DirectionPreferences()
    for (i <- 0 until DIRECTIONS_COUNT) {
      newPreferences.preferences(i) = preferences(i) + other.preferences(i)
    }
    newPreferences
  }

  def scale(factor:Double) = {
    val newPreferences = new DirectionPreferences()
    for (i <- 0 until DIRECTIONS_COUNT) {
      newPreferences.preferences(i) = preferences(i)*factor
    }
    newPreferences
  }

  private def normalize(direction:Int) = {
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
