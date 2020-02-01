package time4s

import java.time._
import java.time.chrono.ChronoPeriod
import java.time.temporal.Temporal

trait OrderingInstance {
  private[time4s] def of[A](f: (A, A) => Int): Ordering[A] = new Ordering[A] {
    override def compare(x: A, y: A): Int = f(x, y)
  }

  implicit def temporalOrdering[A <: Temporal](implicit ev: A <:< Comparable[A]): Ordering[A] = new Ordering[A] {
    override def compare(x: A, y: A): Int = ev(x).compareTo(y)
  }

  implicit val periodOrdering: Ordering[Period] = of[Period] { (x, y) =>
    val a = x.minus(y)
    if (a.isZero) 0
    else if (a.isNegative) -1
    else 1
  }

  implicit val durationOrdering: Ordering[Duration] = of[Duration](_.compareTo(_))
  implicit val chronoPeriodOrdering: Ordering[ChronoPeriod] = of[ChronoPeriod] { (x, y) =>
    val a = x.minus(y)
    if (a.isZero) 0
    else if (a.isNegative) -1
    else 1
  }
}

object ordering extends OrderingInstance
