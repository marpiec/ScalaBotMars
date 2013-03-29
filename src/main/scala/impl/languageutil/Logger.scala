package impl.languageutil

/**
 * 
 */
object Logger {

  private val enabled = new ThreadLocal[Boolean]()
  enabled.set(false)

  def log(message: => String) {
    if (enabled.get()) {
      println(message)
    }
  }

  def logNoLn(message: => String) {
    if (enabled.get()) {
      print(message)
    }
  }

  def enable() {
    enabled.set(true)
  }

  def disable() {
    enabled.set(false)
  }
}
