/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Input}

case class CP113(value: Int) extends CtBoxIdentifier(name = "Net profit on sale of fixed assets") with CtInteger with Input
