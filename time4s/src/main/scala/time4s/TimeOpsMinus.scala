package time4s

import java.time._
import java.time.chrono.ChronoPeriod

trait TimeOpsMinus[A, B] {
  type Result
  def minus(a: A, b: B): Result
}

object TimeOpsMinus {
  type Aux[A, B, R] = TimeOpsMinus[A, B] { type Result = R }

  def of[A, B, C](f: (A, B) => C): Aux[A, B, C] = new TimeOpsMinus[A, B] {
    type Result = C
    override def minus(a: A, b: B): C = f(a, b)
  }

  // guaranteed cast success
  implicit def temporalMinusOp[A <: temporal.Temporal, B <: temporal.TemporalAmount]: Aux[A, B, A] =
    of((a, b) => a.minus(b).asInstanceOf[A])

  implicit def temporalBetween[A <: temporal.Temporal, B <: temporal.Temporal](
    implicit ev: A =:= B
  ): Aux[A, B, Duration] = {
    val _ = ev // so the parameter is consumed
    of((a, b) => Duration.between(a, b))
  }

  implicit val periodMinusOp: Aux[Period, Period, Period]                         = of((a, b) => a.minus(b))
  implicit val chronoPeriodMinusOp: Aux[ChronoPeriod, ChronoPeriod, ChronoPeriod] = of((a, b) => a.minus(b))
  implicit val durationMinusOp: Aux[Duration, Duration, Duration]                 = of((a, b) => a.minus(b))
}
