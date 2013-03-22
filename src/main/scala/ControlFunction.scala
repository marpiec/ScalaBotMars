import impl.function.ReactFunction
import impl.io.{InputParser}
import impl.reactor.ReactHandler

class ControlFunction {
  def respond(input: String): String = {

    try {
      //println(input)
      val response = tryToRespond(input)
      //
      println(response)
      //Thread.sleep(1000)
      response.toString
    } catch {
      case e => {
        e.printStackTrace()
        ""
      }
    }

  }


  def tryToRespond(input: String) = {
    val parser = new InputParser(input)

    if (parser.isReactFunction) {
      new ReactHandler(parser.result.asInstanceOf[ReactFunction]).respond()
    } else {
      ""
    }
  }


}
