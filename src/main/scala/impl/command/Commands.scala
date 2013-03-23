package impl.command

/**
 * @author Marcin Pieciukiewicz
 */
class Commands {
  var commands = List[Command]()

  def addCommand(command:Command) {
    commands ::= command
  }

  override def toString: String = {
    val sb = new StringBuilder()
    var nonFirst = false
    for (command <- commands) {
      if (nonFirst) {
        sb.append("|")
      } else {
        nonFirst = true
      }
      sb.append(command)
    }
    sb.append(")")
    sb.toString()
  }
}
