package impl.reactor.senses

import impl.analyser.{PathFinder, ViewAnalyser}
import impl.servercommunication.command.{Spawn, Commands}
import impl.data.{XY, MiniBotRoles, DirectionPreferences}
import impl.configuration.PrizesFunctions

/**
 * 
 */
class ScoutsCreator(viewAnalyser: ViewAnalyser, slavesCount:Int, maxSlaves: Int) {

  def calculateCommands(): Commands = {
    val myMiniBots: List[XY] = viewAnalyser.myMiniBots
    var spawnCommands = new Commands(List())
    if (myMiniBots.size < 5 && slavesCount < maxSlaves - 5 && slavesCount < 50) {
      val preferences = new DirectionPreferences()
      myMiniBots.foreach(myMiniBotRelative => {
        val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, myMiniBotRelative)
        val prize = PrizesFunctions.loner(pathLength)
        preferences.increasePreference(nextStep, prize)
      })
      spawnCommands ::= new Spawn(preferences.findBestStep(), "hunter", 100, MiniBotRoles.HUNTER)
    }
    spawnCommands
  }

}
