import impl.function.ReactFunction
import impl.io.{InputParser}
import impl.ReactHandler

class ControlFunction {
  def respond(input: String): String = {

    try {
      val response = tryToRespond(input)
      println(input)
      println(response)
      response
    } catch {
      case e => {
        e.printStackTrace()
        ""
      }
    }

  }


  def tryToRespond(input: String): String = {
    val parser = new InputParser(input)

    if (parser.isReactFunction) {
      ReactHandler.respond(parser.result.asInstanceOf[ReactFunction])
    } else {
      ""
    }
  }


}
