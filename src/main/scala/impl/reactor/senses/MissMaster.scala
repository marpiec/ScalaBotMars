package impl.reactor.senses

import impl.analyser.{PathFinder, ViewAnalyser}
import impl.servercommunication.function.ReactFunction
import impl.data.DirectionPreferences

/**
 * @author Marcin Pieciukiewicz
 */
class MissMaster(viewAnalyser:ViewAnalyser, reactFunction: ReactFunction) {


  def getPreferences():DirectionPreferences = {

    val preferences = new DirectionPreferences()
    val masterPosition = reactFunction.masterOption.get

    val pathFinder = new PathFinder(viewAnalyser)
    val pathSize = PathFinder.calculateRequiredSteps(masterPosition)
    val nextStep = pathFinder.findNextStepTo(masterPosition)

    val prize = reactFunction.energy / pathSize * (reactFunction.energy / 100)


    preferences.increasePreference(nextStep, prize)
    preferences
  }
}
