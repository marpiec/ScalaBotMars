package impl.reactor.senses

import impl.servercommunication.command.Spawn
import impl.analyser.{PathFinder, ViewAnalyser}
import impl.data.{MiniBotRoles, XY}

/**
 * @author Marcin Pieciukiewicz
 */
class MissileDefence(viewAnalyser:ViewAnalyser) {


  def getCommands():List[Spawn] = {

    val targets: List[XY] = viewAnalyser.enemyMiniBots ::: viewAnalyser.enemyBots ::: viewAnalyser.badBeasts
    val myMiniBots: List[XY] = viewAnalyser.myMiniBots
    var spawnCommands = List[Spawn]()

    targets.foreach(target => {
      val pathFinder = new PathFinder(viewAnalyser)
      val pathSize = PathFinder.calculateRequiredSteps(target)
      val nextStep = pathFinder.findNextStepTo(target)

      if(pathSize < 15) {

        val notEnoughtMissilesNear = myMiniBots.count(myBotRelativePosition => {
          PathFinder.calculateRequiredSteps(myBotRelativePosition, target) < 10
        }) < 2

        if (notEnoughtMissilesNear && targets.size + 2 > myMiniBots.size) {
          spawnCommands ::= new Spawn(nextStep, "antiMissile", 100, MiniBotRoles.MISSILE)
        }
      }

    })
    spawnCommands
  }


}
