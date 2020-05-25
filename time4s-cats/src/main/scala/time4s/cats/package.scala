package time4s
package cats

import java.time._
import java.time.chrono.ChronoPeriod

import _root_.cats.kernel._

object implicits extends OrderInstance with HashInstance with CommutativeGroupInstance

object JavaDurationOrder extends OrderInstance

trait OrderInstance {
  implicit val periodOrder: Order[Period]             = Order.fromOrdering(JavaTimeOrdering.periodOrdering)
  implicit val durationOrder: Order[Duration]         = Order.fromOrdering(JavaTimeOrdering.durationOrdering)
  implicit val chronoPeriodOrder: Order[ChronoPeriod] = Order.fromOrdering(JavaTimeOrdering.chronoPeriodOrdering)
}

trait MonoidInstance {
  implicit val periodMonoid: Monoid[Period] =
    Monoid.instance[Period](Period.ZERO, TimeOpsPlus.periodPlusOp.plus)
  implicit val durationMonoid: Monoid[Duration] =
    Monoid.instance[Duration](Duration.ZERO, TimeOpsPlus.durationPlusOp.plus)
  implicit val chronoPeriodMonoid: Monoid[ChronoPeriod] =
    Monoid.instance[ChronoPeriod](Period.ZERO, TimeOpsPlus.chronoPeriodPlusOp.plus)
}

object JavaDurationHash extends HashInstance

trait HashInstance {
  implicit val periodHash: Hash[Period]             = Hash.fromUniversalHashCode
  implicit val durationHash: Hash[Duration]         = Hash.fromUniversalHashCode
  implicit val chronoPeriodHash: Hash[ChronoPeriod] = Hash.fromUniversalHashCode
}

trait CommutativeGroupInstance {
  implicit val periodCommutitiveGroup: CommutativeGroup[Period] = new CommutativeGroup[Period] {
    override def empty: Period                         = Period.ZERO
    override def combine(x: Period, y: Period): Period = x.plus(y)
    override def inverse(a: Period): Period            = a.negated()
  }

  implicit val durationCommutitiveGroup: CommutativeGroup[Duration] = new CommutativeGroup[Duration] {
    override def empty: Duration                             = Duration.ZERO
    override def combine(x: Duration, y: Duration): Duration = x.plus(y)
    override def inverse(a: Duration): Duration              = a.negated()
  }

  implicit val chronoPeriodCommutitiveGroup: CommutativeGroup[ChronoPeriod] = new CommutativeGroup[ChronoPeriod] {
    override def empty: ChronoPeriod                                     = Period.ZERO
    override def combine(x: ChronoPeriod, y: ChronoPeriod): ChronoPeriod = x.plus(y)
    override def inverse(a: ChronoPeriod): ChronoPeriod                  = a.negated()
  }
}
