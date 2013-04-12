package impl.reactor.senses

import impl.servercommunication.command.Spawn
import impl.analyser.{BotCounter, PathFinder, ViewAnalyser}
import impl.data.{MiniBotRoles, XY}
import impl.servercommunication.function.ReactFunction

/**
 * @author Marcin Pieciukiewicz
 */
class MissileDefence(reactFunction:ReactFunction, viewAnalyser: ViewAnalyser, slavesCount: Int, maxSlaves: Int) {


  def calculateCommands(): Option[Spawn] = {

    val targets: List[XY] = viewAnalyser.enemyMiniBots ::: viewAnalyser.enemyBots
    val myMiniBots: List[XY] = viewAnalyser.myMiniBots

    if (slavesCount < maxSlaves) {
      targets.foreach(target => {
        val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, target)

        if (pathLength < 8) {

          val notEnoughMissilesNear = myMiniBots.count(myBotRelativePosition => {
            PathFinder.calculateRequiredSteps(myBotRelativePosition, target) < 5
          }) < 2

          if (notEnoughMissilesNear && targets.size + 5 > myMiniBots.size) {
            if (BotCounter.createBot(reactFunction.time)) {
              return Option(new Spawn(nextStep, "antiMissile", 100, MiniBotRoles.MISSILE))
            }
          }
        }

      })
    }
    None
  }


}
