package impl.reactor.senses

import impl.analyser.{DirectionAdvisor, ViewAnalyser}
import impl.servercommunication.function.ReactFunction
import impl.data.{DirectionPreferences, Directions, XY}
import scala.None

/**
 * 
 */
class LifeGuide(viewAnalyser: ViewAnalyser, reactFunction: ReactFunction, cabinFeverPreferences:DirectionPreferences) {

  val currentDestination = reactFunction.destination
  val destinationChangeTime = reactFunction.destinationChangeTime
  val time = reactFunction.time

  def chooseDestination():XY = {

    var newDestinationOption:Option[XY] = None

    if (doesDestinationHaveToChange()) {
      if (currentDestination == XY.ZERO) {
        newDestinationOption = Option(XY(-1,-1))
      } else {
        //val currentDirection = Directions.getDirectionFor(currentDestination)
        val newDestination: XY = cabinFeverPreferences.findBestStep()
        //println("New destination: "+newDestination+" "+cabinFeverPreferences)
        newDestinationOption = Option(newDestination)//
      }

    }

    newDestinationOption.getOrElse(reactFunction.destination)

  }

  private def doesDestinationHaveToChange():Boolean = {
    if(currentDestination == XY.ZERO) {
      return true
    } else if (reactFunction.lastSteps.isFilled && time > destinationChangeTime + 20) {
      val change = reactFunction.lastSteps.calculateChange
      if (math.max(change.x, change.y) < 5) {
        return true
      }
    } else if (time > destinationChangeTime + 400) {
      return true
    }
    false
  }
}
