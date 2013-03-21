package impl.data

import spock.lang.Specification

class XYSpec extends Specification {

    def "Parsing of X:Y format ability"() {

        expect:
        XY.parse(input) == new XY(x, y)

        where:
        input   || x   | y
        "0:0"   || 0   | 0
        "43:42" || 43  | 42
        "-12:3" || -12 | 3

    }

    def "Proper toString"() {

        expect:
        new XY(x, y).toString() == output

        where:
        x   | y   || output
        0   | 0   || "0:0"
        23  | 42  || "23:42"
        -12 | -3  || "-12:-3"

    }
}
