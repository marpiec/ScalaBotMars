package impl.command

/**
 * 
 */
class Set(val state:Map[String, String]) extends Command {
  override def toString: String = {
    val sb = new StringBuilder("Set(")
    var nonFirst = false
    for ((key, value) <- state) {
      if (nonFirst) {
        sb.append(",")
      } else {
        nonFirst = true
      }
      sb.append(key).append("=").append(value)
    }
    sb.append(")")
    sb.toString()
  }
}
