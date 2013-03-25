package impl.reactor.senses

import impl.analyser.{PathFinder, ViewAnalyser}
import impl.servercommunication.function.ReactFunction
import impl.data.DirectionPreferences
import impl.configuration.PrizesFunctions

/**
 * 
 */
class Explorer(val viewAnalyser: ViewAnalyser, reactFunction: ReactFunction) {
  val currentDestination = reactFunction.destination

  def calculatePreferences(): DirectionPreferences = {

    val preferences = new DirectionPreferences()

    val nextStep = PathFinder.findNextStepTo(viewAnalyser, currentDestination)
    val prize = PrizesFunctions.explorer(reactFunction.lastSteps)

    preferences.increasePreference(nextStep, prize)
    preferences
  }
}
