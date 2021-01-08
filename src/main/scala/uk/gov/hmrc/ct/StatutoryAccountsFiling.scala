/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Input}

case class StatutoryAccountsFiling(value: Boolean) extends CtBoxIdentifier("Statutory Accounts Filing") with CtBoolean with Input
