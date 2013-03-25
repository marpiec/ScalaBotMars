package impl.reactor.senses

import impl.analyser.{PathFinder, ViewAnalyser}
import impl.servercommunication.function.ReactFunction
import impl.data.DirectionPreferences
import impl.configuration.PrizesFunctions

/**
 * @author Marcin Pieciukiewicz
 */
class GoHome(viewAnalyser: ViewAnalyser, reactFunction: ReactFunction) {

  def calculatePreferences(): DirectionPreferences = {

    val preferences = new DirectionPreferences()
    val masterPosition = reactFunction.masterOption.get
    val foodCount = viewAnalyser.goodPlants.size + viewAnalyser.goodBeasts.size

    val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, masterPosition)
    val prize = PrizesFunctions.goHome(pathLength, reactFunction.energy, foodCount, viewAnalyser.myBots.nonEmpty)

    preferences.increasePreference(nextStep, prize)

    preferences
  }
}
