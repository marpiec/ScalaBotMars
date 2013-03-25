package impl.servercommunication.command.debug

import impl.servercommunication.command.Command

/**
 * @author Marcin Pieciukiewicz
 */
class Status(text: String) extends Command {
  override def toString = "Status(text=" + text + ")"
}
