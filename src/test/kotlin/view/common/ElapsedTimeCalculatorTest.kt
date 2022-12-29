package view.common

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.Date

class ElapsedTimeCalculatorTest : StringSpec({
    "seconds" {
        // 2022/12/20/12:00:00
        val startTime = 1671505200000

        // 2022/12/20/12:00:01
        val elapsedSecond1 = 1671505201000
        ElapsedTimeCalculator.calc(Date(startTime), Date(elapsedSecond1)) shouldBe "00:00:01"

        // 2022/12/20/12:00:59
        val elapsedSecond59 = 1671505259000
        ElapsedTimeCalculator.calc(Date(startTime), Date(elapsedSecond59)) shouldBe "00:00:59"
    }
    "minites" {
        // 2022/12/20/12:00:00
        val startTime = 1671505200000

        // 2022/12/20/12:01:00
        val elapsedMinute1 = 1671505260000
        ElapsedTimeCalculator.calc(Date(startTime), Date(elapsedMinute1)) shouldBe "00:01:00"

        // 2022/12/20/12:59:00
        val elapsedMinute59 = 1671508740000
        ElapsedTimeCalculator.calc(Date(startTime), Date(elapsedMinute59)) shouldBe "00:59:00"
    }
    "hours" {
        // 2022/12/20/12:00:00
        val startTime = 1671505200000

        // 2022/12/20/13:00:00
        val elapsedHour1 = 1671508800000
        ElapsedTimeCalculator.calc(Date(startTime), Date(elapsedHour1)) shouldBe "01:00:00"

        // 2022/12/21/12:00:00
        val elapsedHour24 = 1671591600000
        ElapsedTimeCalculator.calc(Date(startTime), Date(elapsedHour24)) shouldBe "24:00:00"

        // 2022/12/22/12:00:00
        val elapsedHour48 = 1671678000000
        ElapsedTimeCalculator.calc(Date(startTime), Date(elapsedHour48)) shouldBe "48:00:00"
    }
})
