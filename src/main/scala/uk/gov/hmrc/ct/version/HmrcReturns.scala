package uk.gov.hmrc.ct.version

object HmrcReturns {
  case object Computations extends ReturnType {
    override def key(): String = "Computations"
  }

  case object CT600 extends ReturnType {
    override def key(): String = "CT600"
  }

  case object CT600a extends ReturnType {
    override def key(): String = "CT600a"
  }

  case object CT600j extends ReturnType {
    override def key(): String = "CT600j"
  }

  case object HmrcMicroEntityAccounts extends ReturnType with Accounts {
    override def key(): String = "HmrcMicroEntityAccounts"
  }

  case object HmrcStatutoryAccounts extends ReturnType with Accounts {
    override def key(): String = "HmrcStatutoryAccounts"
  }

  val returns: Set[ReturnType] = Set(Computations, CT600, CT600a, CT600j, HmrcMicroEntityAccounts, HmrcStatutoryAccounts)

  def fromKey(key: String): ReturnType = {
    returns.find(_.key() == key).getOrElse(throw new IllegalArgumentException(s"Unknown key for HmrcReturn: $key"))
  }
}

