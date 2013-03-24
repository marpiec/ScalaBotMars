package impl.reactor

import impl.function.ReactFunction
import impl.analyser.ViewAnalyser
import senses.{Hunger}
import impl.data.MiniBotRoles
import impl.command.debug.Say
import impl.command.{Move, Commands}

/**
 * @author Marcin Pieciukiewicz
 */
class MiniBotReactHandler(reactFunction: ReactFunction) {

  def respond() = {
    val viewAnalyser = new ViewAnalyser(reactFunction.view)

    val multipleCommands = if(reactFunction.role == MiniBotRoles.MISSILE) {
      new MissileMiniBotReactHandler(reactFunction, viewAnalyser).respond()
    } else if(reactFunction.role == MiniBotRoles.HUNTER) {
      new HunterMiniBotReactHandler(reactFunction, viewAnalyser).respond()
    } else {
      List(new Say("Be or not to be?"))
    }

    val command = new Commands()
    command.addMultipleCommands(multipleCommands)
    command
  }

}
