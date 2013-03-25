package impl.servercommunication.command.debug

import impl.servercommunication.command.Command

/**
 * @author Marcin Pieciukiewicz
 */
class Log(text: String) extends Command {
  override def toString = "Log(text=" + text + ")"
}
