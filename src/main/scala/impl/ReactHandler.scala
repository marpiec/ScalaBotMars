package impl

import command.DebugCommand
import function.ReactFunction

object ReactHandler {
  def respond(reactFunction: ReactFunction): String = {
    DebugCommand.say(reactFunction.viewDistance.toString)
  }
}
