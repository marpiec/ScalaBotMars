package impl.reactor.senses

import impl.servercommunication.command.{Commands, Spawn}
import impl.analyser.{PathFinder, ViewAnalyser}
import impl.data.{MiniBotRoles, XY}

/**
 * @author Marcin Pieciukiewicz
 */
class MissileDefence(viewAnalyser: ViewAnalyser) {


  def calculateCommands(): Commands = {

    val targets: List[XY] = viewAnalyser.enemyMiniBots ::: viewAnalyser.enemyBots ::: viewAnalyser.badBeasts
    val myMiniBots: List[XY] = viewAnalyser.myMiniBots
    var spawnCommands = List[Spawn]()

    targets.foreach(target => {
      val (nextStep, pathLength) = PathFinder.findNextStepAndDistance(viewAnalyser, target)

      if (pathLength < 15) {

        val notEnoughMissilesNear = myMiniBots.count(myBotRelativePosition => {
          PathFinder.calculateRequiredSteps(myBotRelativePosition, target) < 10
        }) < 2

        if (notEnoughMissilesNear && targets.size + 2 > myMiniBots.size) {
          spawnCommands ::= new Spawn(nextStep, "antiMissile", 100, MiniBotRoles.MISSILE)
        }
      }

    })
    spawnCommands
    new Commands(spawnCommands)
  }


}
