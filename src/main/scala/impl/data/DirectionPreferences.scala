package impl.data

import impl.languageutil.CollectionUtil
import java.text.DecimalFormat

object DirectionPreferences {
  val decimalFormat = new DecimalFormat("##0.###")
}

/**
 * Direction preferences list contains values for each degree starting from north, clock wise.
 */
class DirectionPreferences {


  val DIRECTIONS_COUNT = Step.DIRECTIONS_COUNT

  val preferences: Array[Double] = Array.fill(DIRECTIONS_COUNT)(0.0)

  def getPreference(direction: Int) = preferences(direction)

  def increasePreference(step: Step, preferenceDiff: Double) {
    val direction = step.direction
    preferences(normalizeModulo(direction)) += preferenceDiff
    preferences(normalizeModulo(direction - 1)) += preferenceDiff / 2
    preferences(normalizeModulo(direction + 1)) += preferenceDiff / 2
    preferences(normalizeModulo(direction - 2)) += preferenceDiff / 4
    preferences(normalizeModulo(direction + 2)) += preferenceDiff / 4
  }

  def decreasePreferenceSharp(step: Step, preferenceDiff: Double) {
    decreasePreferenceSharpForDirection(step.direction, preferenceDiff)
  }

  def decreasePreferenceSharpForDirection(direction: Int, preferenceDiff: Double) {
    preferences(normalizeModulo(direction)) -= preferenceDiff
  }

  def findBestStep(): Step = {
    Step(CollectionUtil.findIndexOfMaxElement(preferences))
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
    preferences.foreach(preference => sb.append(DirectionPreferences.decimalFormat.format(preference)).append(" \t"))
    sb.substring(0, sb.length - 2)
  }
}
