package impl.servercommunication.command

import impl.data.XY

/**
 *
 */
class Spawn(var direction: XY, name: String, energy: Int, role: String) extends Command {
  override def toString: String = {
    val sb = new StringBuilder("Spawn(direction=").append(direction.toString).
      append(",name=").append(name).
      append(",energy=").append(energy.toString).
      append(",role=").append(role)

    sb.append(")")
    sb.toString()
  }
}
