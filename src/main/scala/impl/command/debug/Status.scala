package impl.command.debug

import impl.command.Command

/**
 * @author Marcin Pieciukiewicz
 */
class Status(text: String) extends Command {
  override def toString = "Status(text=" + text + ")"
}
