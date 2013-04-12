package impl.reactor.senses

import impl.analyser.{BotCounter, DirectionAdvisor, PathFinder, ViewAnalyser}
import impl.servercommunication.command.Spawn
import impl.data.{XY, MiniBotRoles, DirectionPreferences}
import impl.configuration.PrizesFunctions
import impl.servercommunication.function.ReactFunction

/**
 *
 */
class ScoutsCreator(reactFunction: ReactFunction, viewAnalyser: ViewAnalyser, slavesCount: Int, maxSlaves: Int) {

  def calculateCommands(): Option[Spawn] = {
    val myMiniBots: List[XY] = viewAnalyser.myMiniBots
    if (myMiniBots.size < 10) {
      val canCreate = BotCounter.createBot(reactFunction.time, 1)
      if (canCreate) {
        val preferences = new DirectionPreferences()
        myMiniBots.foreach(myMiniBotRelative => {
          val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, myMiniBotRelative)
          val prize = PrizesFunctions.loner(pathLength)
          preferences.increasePreference(nextStep, prize)
        })
        val step: XY = DirectionAdvisor.findBestMoveFromPreferences(preferences, viewAnalyser, true)
        Option(new Spawn(step, "hunter", 100, MiniBotRoles.HUNTER))
      } else {
        None
      }
    } else {
      None
    }
  }

}
