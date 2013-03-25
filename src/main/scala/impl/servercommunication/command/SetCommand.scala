package impl.servercommunication.command

/**
 *
 */
class SetCommand(params:Map[String, String]) extends Command {
  override def toString: String = {

    val sb = new StringBuilder("Set(")

    var notFirst = false
    for ((key, value) <- params) {
      if (notFirst) {
        sb.append(",")
      } else {
        notFirst = true
      }
      sb.append(key).append("=").append(value)
    }

    sb.append(")").toString()
  }
}
