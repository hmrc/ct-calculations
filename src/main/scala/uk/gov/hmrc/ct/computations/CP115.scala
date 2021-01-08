/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Input}

case class CP115(value: Int) extends CtBoxIdentifier(name = "Net capital allowances") with CtInteger with Input
