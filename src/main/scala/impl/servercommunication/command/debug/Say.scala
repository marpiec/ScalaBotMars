package impl.servercommunication.command.debug

import impl.servercommunication.command.Command

/**
 * @author Marcin Pieciukiewicz
 */
class Say(text: String) extends Command {
  override def toString = "Say(text=" + text + ")"
}
