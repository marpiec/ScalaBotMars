package impl.io

class OutputWriter {
  var output = new StringBuilder()

  def status(text:String) {
    output.append("Status(text=").append(text).append(")")
  }

  def log(text:String) {
    output.append("Log(text=").append(text).append(")")
  }

  def get = output.toString
}
