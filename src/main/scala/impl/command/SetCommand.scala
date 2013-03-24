package impl.command

/**
 * 
 */
class SetCommand(role:String) extends Command {
  override def toString: String = {
    "Set(role="+role+")"
  }
}
