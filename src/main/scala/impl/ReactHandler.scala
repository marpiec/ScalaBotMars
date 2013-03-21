package impl

import function.ReactFunction
import io.OutputWriter

object ReactHandler {
  def respond(reactFunction: ReactFunction): String = {
    val output = new OutputWriter
    output.status(reactFunction.viewDistance.toString)
    output.get
  }
}
