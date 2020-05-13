

package uk.gov.hmrc.ct.version

object CoHoAccounts {

  case object CoHoMicroEntityAccounts extends Accounts {
    override def key(): String = "CoHoMicroEntityAccounts"
  }

  case object CoHoMicroEntityAbridgedAccounts extends Accounts with ReducedAccounts {
    override def key(): String = "CoHoMicroEntityAbridgedAccounts"

    override def fullVersion: Accounts = CoHoMicroEntityAccounts
  }

  case object CoHoStatutoryAccounts extends Accounts {
    override def key(): String = "CoHoStatutoryAccounts"
  }

  case object CoHoStatutoryAbbreviatedAccounts extends Accounts with ReducedAccounts {
    override def key(): String = "CoHoStatutoryAbbreviatedAccounts"

    override def fullVersion: Accounts = CoHoStatutoryAccounts
  }

  case object CoHoAbridgedAccounts extends Accounts {
    override def key(): String = "CoHoAbridgedAccounts"
  }

  val returns: Set[ReturnType] = Set(CoHoMicroEntityAccounts, CoHoMicroEntityAbridgedAccounts, CoHoStatutoryAccounts, CoHoStatutoryAbbreviatedAccounts, CoHoAbridgedAccounts)

  def fromKey(key: String): ReturnType = {
    returns.find(_.key() == key).getOrElse(throw new IllegalArgumentException(s"Unknown key for CoHoAccounts: $key"))
  }
}

trait ReducedAccounts {

  self: Accounts =>

  def fullVersion: Accounts
}
