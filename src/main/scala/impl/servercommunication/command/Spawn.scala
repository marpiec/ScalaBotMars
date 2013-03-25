package impl.servercommunication.command

import impl.data.XY

/**
 *
 */
class Spawn(val direction: XY, val name: String, val energy: Int, val role: String) extends Command {
  override def toString: String = {
    val sb = new StringBuilder("Spawn(direction=").append(direction.toString).
      append(",name=").append(name).
      append(",energy=").append(energy.toString).
      append(",role=").append(role)

    sb.append(")")
    sb.toString()
  }
}
