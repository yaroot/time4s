package time4s
package cats

import java.time._
import java.time.chrono.ChronoPeriod

import _root_.cats.Show
import _root_.cats.kernel._

object instances {
  implicit val javaPeriodInstances: Hash[Period] with Order[Period] with Show[Period] with CommutativeGroup[Period] =
    new Hash[Period] with Order[Period] with Show[Period] with CommutativeGroup[Period] {
      override def hash(x: Period): Int                  = x.hashCode()
      override def compare(x: Period, y: Period): Int    = JavaTimeOrdering.periodOrdering.compare(x, y)
      override def empty: Period                         = Period.ZERO
      override def inverse(a: Period): Period            = a.negated()
      override def show(t: Period): String               = t.toString
      override def combine(x: Period, y: Period): Period = x.plus(y)
    }

  implicit val javaDurationInstances
    : Hash[Duration] with Order[Duration] with Show[Duration] with CommutativeGroup[Duration] =
    new Hash[Duration] with Order[Duration] with Show[Duration] with CommutativeGroup[Duration] {
      override def hash(x: Duration): Int                      = x.hashCode()
      override def compare(x: Duration, y: Duration): Int      = JavaTimeOrdering.durationOrdering.compare(x, y)
      override def empty: Duration                             = Duration.ZERO
      override def inverse(a: Duration): Duration              = a.negated()
      override def show(t: Duration): String                   = t.toString
      override def combine(x: Duration, y: Duration): Duration = x.plus(y)
    }

  implicit val javaChronoPeriodInstances
    : Hash[ChronoPeriod] with Order[ChronoPeriod] with Show[ChronoPeriod] with CommutativeGroup[ChronoPeriod] =
    new Hash[ChronoPeriod] with Order[ChronoPeriod] with Show[ChronoPeriod] with CommutativeGroup[ChronoPeriod] {
      override def hash(x: ChronoPeriod): Int                              = x.hashCode()
      override def compare(x: ChronoPeriod, y: ChronoPeriod): Int          = JavaTimeOrdering.chronoPeriodOrdering.compare(x, y)
      override def empty: ChronoPeriod                                     = Period.ZERO
      override def inverse(a: ChronoPeriod): ChronoPeriod                  = a.negated()
      override def show(t: ChronoPeriod): String                           = t.toString
      override def combine(x: ChronoPeriod, y: ChronoPeriod): ChronoPeriod = x.plus(y)
    }
}
