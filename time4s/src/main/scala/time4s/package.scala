package time4s

import java.time._
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration

object implicits extends OrderingInstance with JavaTimeSyntax

trait JavaTimeSyntax {
  implicit def toTimeOps[A](a: A): TimeOps[A]                            = new TimeOps(a)
  implicit def toFiniteDurationOps(a: FiniteDuration): FiniteDurationOps = new FiniteDurationOps(a)
}

class TimeOps[A](private val a: A) extends AnyVal {
  def +[B](b: B)(implicit ev: TimeOpsPlus[A, B]): ev.Result  = ev.plus(a, b)
  def -[B](b: B)(implicit ev: TimeOpsMinus[A, B]): ev.Result = ev.minus(a, b)
  def unary_-(implicit ev: TimeOpsUnaryMinus[A]): A          = ev.minus(a)
}

class FiniteDurationOps(private val fd: FiniteDuration) extends AnyVal {
  import TimeUnit._
  def asJava: Duration = {
    fd.unit match {
      case NANOSECONDS  => Duration.ofNanos(fd.length)
      case MICROSECONDS => Duration.ofNanos(MICROSECONDS.toNanos(fd.length))
      case MILLISECONDS => Duration.ofMillis(fd.length)
      case SECONDS      => Duration.ofSeconds(fd.length)
      case MINUTES      => Duration.ofMinutes(fd.length)
      case HOURS        => Duration.ofHours(fd.length)
      case DAYS         => Duration.ofDays(fd.length)
      // these are all units that FiniteDuration supports
    }
  }
}
