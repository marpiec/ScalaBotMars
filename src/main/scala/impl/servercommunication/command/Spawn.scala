package impl.servercommunication.command

import impl.data.{Step, XY}

/**
 *
 */
class Spawn(var direction: Step, name: String, energy: Int, role: String) extends Command {
  override def toString: String = {
    val sb = new StringBuilder("Spawn(direction=").append(direction.xy.toString).
      append(",name=").append(name).
      append(",energy=").append(energy.toString).
      append(",role=").append(role)

    sb.append(")")
    sb.toString()
  }
}
