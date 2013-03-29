package impl.reactor.senses

import impl.analyser.ViewAnalyser
import impl.servercommunication.function.ReactFunction
import impl.data.{Directions, DirectionPreferences, XY}
import scala.None

/**
 *
 */
class LifeGuide(viewAnalyser: ViewAnalyser, reactFunction: ReactFunction, cabinFeverPreferences: DirectionPreferences) {

  val currentDestination = reactFunction.destination
  val destinationChangeTime = reactFunction.destinationChangeTime
  val time = reactFunction.time

  def chooseDestination(): XY = {

    var newDestinationOption: Option[XY] = None

    if (doesDestinationHaveToChange()) {
      if (currentDestination == XY.ZERO) {
        newDestinationOption = Option(XY(-1, -1))
      } else {
        //val currentDirection = Directions.getDirectionFor(currentDestination)
        //val newDestination: XY = cabinFeverPreferences.findBestStep()


        val goSomewhereElsePreferences = new DirectionPreferences
        goSomewhereElsePreferences.increasePreference(Directions.getStepForDirection((math.random * 8).toInt), 5.0)

        val rand:Int = ((math.random - 0.5) * 4.0).toInt
        val preferences = goSomewhereElsePreferences + cabinFeverPreferences

        val newDestination = Directions.getStepForDirection(preferences.findBestDirection()+ rand)
        //println("New destination: "+newDestination+" "+cabinFeverPreferences)
        newDestinationOption = Option(newDestination) //
      }

    }

    newDestinationOption.getOrElse(reactFunction.destination)

  }

  private def doesDestinationHaveToChange(): Boolean = {
    if (currentDestination == XY.ZERO) {
      return true
    } else if (reactFunction.lastSteps.isFilled && time > destinationChangeTime + 20) {
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
