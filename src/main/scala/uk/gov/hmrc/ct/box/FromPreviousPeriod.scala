package uk.gov.hmrc.ct.box

trait FromPreviousPeriod[PreviousCurrent <: CtValue, CurrentPrevious <: CtValue] {

  def prePopulate(previousPeriodBox: PreviousCurrent): CurrentPrevious
}
