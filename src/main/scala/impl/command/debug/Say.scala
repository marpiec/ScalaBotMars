package impl.command.debug

import impl.command.Command

/**
 * @author Marcin Pieciukiewicz
 */
class Say(text: String) extends Command {
  override def toString = "Say(text=" + text + ")"
}
