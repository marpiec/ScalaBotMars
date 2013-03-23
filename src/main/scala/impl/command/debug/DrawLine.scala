package impl.command.debug

import impl.data.XY
import impl.command.Command

/**
 * @author Marcin Pieciukiewicz
 */
class DrawLine(from: XY, to: XY, color: String) extends Command {
  override def toString = "DrawLine(from=" + from.toString + ",to=" + to.toString + ",color=" + color + ")"
}
