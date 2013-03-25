package impl.servercommunication.command

/**
 *
 */
class Explode(val size: Int) extends Command {
  override def toString = {
    "Explode(size=" + size.toString + ")"
  }
}
