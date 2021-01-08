/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2.retriever

import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.ct600.v2._

trait RepaymentsBoxRetriever extends BoxRetriever {

  def b139(): B139

  def b140(): B140

  def b149(): B149

  def b150(): B150

  def b151(): B151

  def b152(): B152

  def b153(): B153

  def b156(): B156

  def b1571(): B1571

  def b1572(): B1572

  def b1573(): B1573

  def b1574(): B1574

  def b1575(): B1575

  def b158(): B158

  def rdq1(): RDQ1

  def rdq2(): RDQ2
}
