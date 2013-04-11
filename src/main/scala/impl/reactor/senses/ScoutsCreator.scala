package impl.reactor.senses

import impl.analyser.{DirectionAdvisor, PathFinder, ViewAnalyser}
import impl.servercommunication.command.Spawn
import impl.data.{Step, XY, MiniBotRoles, DirectionPreferences}
import impl.configuration.PrizesFunctions

/**
 *
 */
class ScoutsCreator(viewAnalyser: ViewAnalyser, slavesCount: Int, maxSlaves: Int) {

  def calculateCommands(): Option[Spawn] = {
    val myMiniBots: List[XY] = viewAnalyser.myMiniBots
    if (myMiniBots.size < 30 && slavesCount < maxSlaves - 5 && slavesCount < 500) {
      val preferences = new DirectionPreferences()
      myMiniBots.foreach(myMiniBotRelative => {
        val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, myMiniBotRelative)
        val prize = PrizesFunctions.loner(pathLength)
        preferences.increasePreference(nextStep, prize)
      })
      val step: Step = DirectionAdvisor.findBestMoveFromPreferences(preferences, viewAnalyser, true)
      Option(new Spawn(step, "hunter", 100, MiniBotRoles.HUNTER))
    } else {
      None
    }
  }

}
