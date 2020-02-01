package time4s

import java.time._
import java.time.chrono.ChronoPeriod

trait TimeOpsPlus[A, B] {
  type Result
  def plus(a: A, b: B): Result
}

object TimeOpsPlus extends TimeOpsPlusInstance0 {
  type Aux[A, B, R] = TimeOpsPlus[A, B] { type Result = R }

  def of[A, B, C](f: (A, B) => C): Aux[A, B, C] = new TimeOpsPlus[A, B] {
    type Result = C
    override def plus(a: A, b: B): C = f(a, b)
  }

  implicit def temporalPlusOp[A <: temporal.Temporal, B <: temporal.TemporalAmount]: Aux[A, B, A] =
    of((a, b) => a.plus(b).asInstanceOf[A])
  implicit def periodPlusOp: Aux[Period, Period, Period]                         = of((a, b) => a.plus(b))
  implicit def durationPlusOp: Aux[Duration, Duration, Duration]                 = of((a, b) => a.plus(b))
  implicit def chronoPeriodPlusOp: Aux[ChronoPeriod, ChronoPeriod, ChronoPeriod] = of((a, b) => a.plus(b))
}

trait TimeOpsPlusInstance0 {
  import TimeOpsPlus.{Aux, of}
  implicit def commutativeOp[A, B, C](implicit ev: Aux[B, A, C]): Aux[A, B, C] = of((a, b) => ev.plus(b, a))
}
