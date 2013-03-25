package impl.reactor

import impl.servercommunication.function.ReactFunction
import impl.analyser.ViewAnalyser
import impl.data.MiniBotRoles
import impl.servercommunication.command.debug.Say
import impl.servercommunication.command.Commands

/**
 * @author Marcin Pieciukiewicz
 */
class MiniBotReactHandler(reactFunction: ReactFunction) {

  def respond(): Commands = {
    val viewAnalyser = new ViewAnalyser(reactFunction.view)

    val commands = if (reactFunction.role == MiniBotRoles.MISSILE) {
      new MissileMiniBotReactHandler(reactFunction, viewAnalyser).respond()
    } else if (reactFunction.role == MiniBotRoles.HUNTER) {
      new HunterMiniBotReactHandler(reactFunction, viewAnalyser).respond()
    } else {
      new Commands(new Say("Be or not to be?"))
    }

    commands
  }

}
