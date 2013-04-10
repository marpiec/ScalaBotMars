package impl.reactor.senses

import impl.analyser.{PathFinder, ViewAnalyser}
import impl.servercommunication.command.Spawn
import impl.data.{XY, MiniBotRoles, DirectionPreferences}
import impl.configuration.PrizesFunctions

/**
 *
 */
class ScoutsCreator(viewAnalyser: ViewAnalyser, slavesCount: Int, maxSlaves: Int) {

  def calculateCommands(): Option[Spawn] = {
    val myMiniBots: List[XY] = viewAnalyser.myMiniBots
    if (myMiniBots.size < 10 && slavesCount < maxSlaves - 5 && slavesCount < 1000) {
      val preferences = new DirectionPreferences()
      myMiniBots.foreach(myMiniBotRelative => {
        val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, myMiniBotRelative)
        val prize = PrizesFunctions.loner(pathLength)
        preferences.increasePreference(nextStep, prize)
      })
      Option(new Spawn(preferences.findBestStep(), "hunter", 100, MiniBotRoles.HUNTER))
    } else {
      None
    }
  }

}
