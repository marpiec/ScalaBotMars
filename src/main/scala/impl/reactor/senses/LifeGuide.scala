package impl.reactor.senses

import impl.analyser.ViewAnalyser
import impl.servercommunication.function.ReactFunction
import impl.data.{Step, DirectionPreferences, XY}
import scala.None

/**
 *
 */
class LifeGuide(viewAnalyser: ViewAnalyser, reactFunction: ReactFunction, cabinFeverPreferences: DirectionPreferences) {

  val currentDestination = reactFunction.destination
  val destinationChangeTime = reactFunction.destinationChangeTime
  val time = reactFunction.time

  def chooseDestination(): Step = {

    var newDestinationOption: Option[Step] = None

    if (doesDestinationHaveToChange()) {
      val goSomewhereElsePreferences = new DirectionPreferences

      goSomewhereElsePreferences.increasePreference(Step((math.random * 8).toInt), 5.0)

      val rand: Int = ((math.random - 0.5) * 4.0).toInt
      val preferences = goSomewhereElsePreferences + cabinFeverPreferences

      val newDestination = preferences.findBestStep().rotate(rand)

      newDestinationOption = Option(newDestination)
    }

    newDestinationOption.getOrElse(reactFunction.destination)

  }

  private def doesDestinationHaveToChange(): Boolean = {
    if (currentDestination == XY.ZERO) {
      return true
    } else if (reactFunction.lastSteps.isFilled && time > destinationChangeTime + 10) {
      val change = reactFunction.lastSteps.calculateChange
      if (math.max(math.abs(change.x), math.abs(change.y)) < 5) {
        return true
      }
    } else if (time > destinationChangeTime + 400) {
      return true
    }
    false
  }
}
