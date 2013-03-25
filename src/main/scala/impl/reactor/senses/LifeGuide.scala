package impl.reactor.senses

import impl.analyser.ViewAnalyser
import impl.servercommunication.function.ReactFunction
import impl.data.XY
import scala.None

/**
 * 
 */
class LifeGuide(viewAnalyser: ViewAnalyser, reactFunction: ReactFunction) {

  val currentDestination = reactFunction.destination

  def chooseDestination():XY = {

    var newDestinationOption:Option[XY] = None

    val changeDestination = doesDestinationHaveToChange()



    newDestinationOption.getOrElse(reactFunction.destination)

  }

  private def doesDestinationHaveToChange() = {
    if(currentDestination == XY.ZERO) {
      true
    } else {

    }
  }
}
