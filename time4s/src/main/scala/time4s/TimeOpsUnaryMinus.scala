package time4s

import java.time._
import java.time.chrono.ChronoPeriod

trait TimeOpsUnaryMinus[A] {
  def minus(a: A): A
}

object TimeOpsUnaryMinus {
  def of[A](f: A => A): TimeOpsUnaryMinus[A] = new TimeOpsUnaryMinus[A] {
    override def minus(a: A): A = f(a)
  }

  implicit val periodUnaryMinus: TimeOpsUnaryMinus[Period]             = of[Period](_.negated())
  implicit val durationUnaryMinus: TimeOpsUnaryMinus[Duration]         = of[Duration](_.negated())
  implicit val chronoPeriodUnaryMinus: TimeOpsUnaryMinus[ChronoPeriod] = of[ChronoPeriod](_.negated())
}
