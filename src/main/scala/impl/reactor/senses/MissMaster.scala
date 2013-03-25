package impl.reactor.senses

import impl.analyser.{PathFinder, ViewAnalyser}
import impl.servercommunication.function.ReactFunction
import impl.data.DirectionPreferences

/**
 * @author Marcin Pieciukiewicz
 */
class MissMaster(viewAnalyser: ViewAnalyser, reactFunction: ReactFunction) {


  def calculatePreferences(): DirectionPreferences = {

    val preferences = new DirectionPreferences()
    val masterPosition = reactFunction.masterOption.get

    val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, masterPosition)
    val prize = reactFunction.energy / pathLength * (reactFunction.energy / 100)


    preferences.increasePreference(nextStep, prize)
    preferences
  }
}
