package impl.servercommunication.command.debug

import impl.data.XY
import impl.servercommunication.command.Command

/**
 * @author Marcin Pieciukiewicz
 */
class MarkCell(position: XY, color: String) extends Command {
  override def toString = "MarkCell(position=" + position.toString + ",color=" + color + ")"

}
