/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class AC15(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Cost of sales")
                                    with CtOptionalInteger with Input
