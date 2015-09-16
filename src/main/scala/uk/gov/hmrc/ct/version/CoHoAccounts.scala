package uk.gov.hmrc.ct.version

object CoHoAccounts {

  case object CoHoMicroEntityAccounts extends ReturnType with Accounts {
    override def key(): String = "CoHoMicroEntityAccounts"
  }

  case object CoHoMicroEntityAbridgedAccounts extends ReturnType with Accounts {
    override def key(): String = "CoHoMicroEntityAbridgedAccounts"
  }

  case object CoHoStatutoryAccounts extends ReturnType with Accounts {
    override def key(): String = "CoHoStatutoryAccounts"
  }

  case object CoHoStatutoryAbbreviatedAccounts extends ReturnType with Accounts {
    override def key(): String = "CoHoStatutoryAbbreviatedAccounts"
  }

  val returns: Set[ReturnType] = Set(CoHoMicroEntityAccounts, CoHoMicroEntityAbridgedAccounts, CoHoStatutoryAccounts, CoHoStatutoryAbbreviatedAccounts)

  def fromKey(key: String): ReturnType = {
    returns.find(_.key() == key).getOrElse(throw new IllegalArgumentException(s"Unknown key for CoHoAccounts: $key"))
  }
}
