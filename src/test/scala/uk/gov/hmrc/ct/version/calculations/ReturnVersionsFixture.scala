/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ct.version.calculations

import uk.gov.hmrc.ct.version.CoHoAccounts._
import uk.gov.hmrc.ct.version.CoHoVersions.{FRS102, FRS105, FRSSE2008}
import uk.gov.hmrc.ct.version.HmrcReturns._
import uk.gov.hmrc.ct.version.HmrcVersions._
import uk.gov.hmrc.ct.version.Return

object ReturnVersionsFixture {

  val coHoOnlyMicroEntityFRSSE2008Returns = Set(Return(CoHoMicroEntityAccounts, FRSSE2008))

  val coHoOnlyAbridgedMicroEntityFRSSE2008Returns = Set(Return(CoHoMicroEntityAbridgedAccounts, FRSSE2008))

  val coHoOnlyStatutoryFRSSE2008Returns = Set(Return(CoHoStatutoryAccounts, FRSSE2008))

  val coHoOnlyAbbreviatedStatutoryFRSSE2008Returns = Set(Return(CoHoStatutoryAbbreviatedAccounts, FRSSE2008))

  val coHoOnlyFRS105Returns = Set(Return(CoHoMicroEntityAccounts, FRS105))

  val coHoOnlyAbridgedFRS102Returns = Set(Return(CoHoAbridgedAccounts, FRS102))

  val coHoOnlyFullFRS102Returns = Set(Return(CoHoStatutoryAccounts, FRS102))

  val jointAbbreviatedStatutoryFRSSE2008V2Returns = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                                        Return(CoHoStatutoryAbbreviatedAccounts, FRSSE2008),
                                                        Return(CT600, CT600Version2),
                                                        Return(CT600a, CT600Version2),
                                                        Return(CT600j, CT600Version2),
                                                        Return(Computations, ComputationsCT20141001))

  val jointMicroFRSSE2008V2Returns = Set(Return(HmrcMicroEntityAccounts, FRSSE2008),
                                         Return(CoHoMicroEntityAccounts, FRSSE2008),
                                         Return(CT600, CT600Version2),
                                         Return(CT600a, CT600Version2),
                                         Return(CT600j, CT600Version2),
                                         Return(Computations, ComputationsCT20141001))

  val jointMicroFRSSE2008V3Returns = Set(Return(HmrcMicroEntityAccounts, FRSSE2008),
                                         Return(CoHoMicroEntityAccounts, FRSSE2008),
                                         Return(CT600, CT600Version3),
                                         Return(CT600a, CT600Version3),
                                         Return(CT600j, CT600Version3),
                                         Return(Computations, ComputationsCT20150201))

  val jointAbridgedMicroFRSSE2008V2Returns = Set(Return(HmrcMicroEntityAccounts, FRSSE2008),
                                                 Return(CoHoMicroEntityAbridgedAccounts, FRSSE2008),
                                                 Return(CT600, CT600Version2),
                                                 Return(CT600a, CT600Version2),
                                                 Return(CT600j, CT600Version2),
                                                 Return(Computations, ComputationsCT20141001))

  val jointAbridgedMicroFRSSE2008V3Returns = Set(Return(HmrcMicroEntityAccounts, FRSSE2008),
                                                 Return(CoHoMicroEntityAbridgedAccounts, FRSSE2008),
                                                 Return(CT600, CT600Version3),
                                                 Return(CT600a, CT600Version3),
                                                 Return(CT600j, CT600Version3),
                                                 Return(Computations, ComputationsCT20150201))

  val jointStatutoryFRSSE2008V2Returns = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                             Return(CoHoStatutoryAccounts, FRSSE2008),
                                             Return(CT600, CT600Version2),
                                             Return(CT600a, CT600Version2),
                                             Return(CT600j, CT600Version2),
                                             Return(Computations, ComputationsCT20141001))

  val jointAbbreviatedStatutoryFRSSE2008V3Returns = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                                        Return(CoHoStatutoryAbbreviatedAccounts, FRSSE2008),
                                                        Return(CT600, CT600Version3),
                                                        Return(CT600a, CT600Version3),
                                                        Return(CT600j, CT600Version3),
                                                        Return(Computations, ComputationsCT20150201))

  val jointStatutoryFRSSE2008V3Returns = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                             Return(CoHoStatutoryAccounts, FRSSE2008),
                                             Return(CT600, CT600Version3),
                                             Return(CT600a, CT600Version3),
                                             Return(CT600j, CT600Version3),
                                             Return(Computations, ComputationsCT20150201))

  val jointStatutoryFRS102V3Returns = Set(Return(HmrcStatutoryAccounts, FRS102),
                                          Return(CoHoStatutoryAccounts, FRS102),
                                          Return(CT600, CT600Version3),
                                          Return(CT600a, CT600Version3),
                                          Return(CT600j, CT600Version3),
                                          Return(Computations, ComputationsCT20150201))

  val jointAbridgedFRS102V3Returns = Set(Return(HmrcAbridgedAccounts, FRS102),
                                         Return(CoHoAbridgedAccounts, FRS102),
                                         Return(CT600, CT600Version3),
                                         Return(CT600a, CT600Version3),
                                         Return(CT600j, CT600Version3),
                                         Return(Computations, ComputationsCT20150201))

  val jointMicroFRS105V3Returns = Set(Return(HmrcMicroEntityAccounts, FRS105),
                                      Return(CoHoMicroEntityAccounts, FRS105),
                                      Return(CT600, CT600Version3),
                                      Return(CT600a, CT600Version3),
                                      Return(CT600j, CT600Version3),
                                      Return(Computations, ComputationsCT20150201))


  val hmrcOnlyMicroFRSSE2008V2Returns = Set(Return(HmrcMicroEntityAccounts, FRSSE2008),
                                   Return(CT600, CT600Version2),
                                   Return(CT600a, CT600Version2),
                                   Return(CT600j, CT600Version2),
                                   Return(Computations, ComputationsCT20141001))

  val hmrcOnlyMicroFRSSE2008V3Returns = Set(Return(HmrcMicroEntityAccounts, FRSSE2008),
                                   Return(CT600, CT600Version3),
                                   Return(CT600a, CT600Version3),
                                   Return(CT600j, CT600Version3),
                                   Return(Computations, ComputationsCT20150201))

  val hmrcOnlyStatutoryFRSSE2008V2Returns = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                                Return(CT600, CT600Version2),
                                                Return(CT600a, CT600Version2),
                                                Return(CT600j, CT600Version2),
                                                Return(Computations, ComputationsCT20141001))

  val hmrcOnlyStatutoryFRSSE2008V3Returns = Set(Return(HmrcStatutoryAccounts, FRSSE2008),
                                                Return(CT600, CT600Version3),
                                                Return(CT600a, CT600Version3),
                                                Return(CT600j, CT600Version3),
                                                Return(Computations, ComputationsCT20150201))

  val hmrcOnlyUploadAccountsV2Returns = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                            Return(CT600, CT600Version2),
                                            Return(CT600a, CT600Version2),
                                            Return(CT600j, CT600Version2),
                                            Return(Computations, ComputationsCT20141001))

  val hmrcOnlyUploadAccountsV3Returns = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                            Return(CT600, CT600Version3),
                                            Return(CT600a, CT600Version3),
                                            Return(CT600j, CT600Version3),
                                            Return(Computations, ComputationsCT20150201))

  val hmrcOnlyUploadAccountsV3Returns2016 = Set(Return(HmrcUploadedAccounts, UploadedAccounts),
                                            Return(CT600, CT600Version3),
                                            Return(CT600a, CT600Version3),
                                            Return(CT600j, CT600Version3),
                                            Return(Computations, ComputationsCT20161001))

  val hmrcOnlyMicroFRS105V3Returns = Set(Return(HmrcMicroEntityAccounts, FRS105),
                                         Return(CT600, CT600Version3),
                                         Return(CT600a, CT600Version3),
                                         Return(CT600j, CT600Version3),
                                         Return(Computations, ComputationsCT20150201))

  val hmrcOnlyMicroFRS105V3Returns2016 = Set(Return(HmrcMicroEntityAccounts, FRS105),
                                         Return(CT600, CT600Version3),
                                         Return(CT600a, CT600Version3),
                                         Return(CT600j, CT600Version3),
                                         Return(Computations, ComputationsCT20161001))

  val hmrcOnlyAbridgedFRS102V3Returns = Set(Return(HmrcAbridgedAccounts, FRS102),
                                            Return(CT600, CT600Version3),
                                            Return(CT600a, CT600Version3),
                                            Return(CT600j, CT600Version3),
                                            Return(Computations, ComputationsCT20150201))

  val hmrcOnlyAbridgedFRS102V3Returns2016 = Set(Return(HmrcAbridgedAccounts, FRS102),
                                            Return(CT600, CT600Version3),
                                            Return(CT600a, CT600Version3),
                                            Return(CT600j, CT600Version3),
                                            Return(Computations, ComputationsCT20161001))

  val hmrcOnlyStatutoryFRS102V3Returns = Set(Return(HmrcStatutoryAccounts, FRS102),
                                             Return(CT600, CT600Version3),
                                             Return(CT600a, CT600Version3),
                                             Return(CT600j, CT600Version3),
                                             Return(Computations, ComputationsCT20150201))

  val hmrcOnlyStatutoryFRS102V3Returns2016 = Set(Return(HmrcStatutoryAccounts, FRS102),
                                             Return(CT600, CT600Version3),
                                             Return(CT600a, CT600Version3),
                                             Return(CT600j, CT600Version3),
                                             Return(Computations, ComputationsCT20161001))

  val allCoHoOnlyReturnCombinations = Seq(coHoOnlyStatutoryFRSSE2008Returns,
                                          coHoOnlyAbbreviatedStatutoryFRSSE2008Returns,
                                          coHoOnlyAbridgedFRS102Returns,
                                          coHoOnlyAbridgedMicroEntityFRSSE2008Returns,
                                          coHoOnlyFRS105Returns,
                                          coHoOnlyFullFRS102Returns,
                                          coHoOnlyMicroEntityFRSSE2008Returns)

  val allJointReturnCombinations = Seq(jointAbbreviatedStatutoryFRSSE2008V3Returns,
                                       jointAbbreviatedStatutoryFRSSE2008V2Returns,
                                       jointAbridgedMicroFRSSE2008V2Returns,
                                       jointAbridgedMicroFRSSE2008V3Returns,
                                       jointMicroFRSSE2008V2Returns,
                                       jointMicroFRSSE2008V3Returns,
                                       jointStatutoryFRSSE2008V2Returns,
                                       jointStatutoryFRSSE2008V3Returns)

  val allJointReturnCombinationsWithDifferentAccountsBetweenHmrcAndCoHo = Seq(jointAbbreviatedStatutoryFRSSE2008V3Returns,
                                                                              jointAbbreviatedStatutoryFRSSE2008V2Returns,
                                                                              jointAbridgedMicroFRSSE2008V2Returns,
                                                                              jointAbridgedMicroFRSSE2008V3Returns)

  val jointReturnCombinationsWithSameAccountsForCoHoAndHmrc = Seq(jointMicroFRSSE2008V2Returns,
                                                                  jointMicroFRSSE2008V3Returns,
                                                                  jointStatutoryFRSSE2008V2Returns,
                                                                  jointStatutoryFRSSE2008V3Returns)

  val hmrcOnlyUploadAccountsReturnCombinations = Seq(hmrcOnlyUploadAccountsV2Returns,
                                                     hmrcOnlyUploadAccountsV3Returns)

  val hmrcOnlyFRSSE2008ReturnCombinationsNoUploads = Seq(hmrcOnlyMicroFRSSE2008V2Returns,
                                                         hmrcOnlyMicroFRSSE2008V3Returns,
                                                         hmrcOnlyStatutoryFRSSE2008V2Returns,
                                                         hmrcOnlyStatutoryFRSSE2008V3Returns)

  val hmrcOnlyFRS10xReturnCombinationsNoUploads = Seq(hmrcOnlyAbridgedFRS102V3Returns,
                                                      hmrcOnlyStatutoryFRS102V3Returns,
                                                      hmrcOnlyMicroFRS105V3Returns)

  val allHmrcOnlyReturnCombinations = hmrcOnlyFRSSE2008ReturnCombinationsNoUploads ++ hmrcOnlyFRS10xReturnCombinationsNoUploads ++ hmrcOnlyUploadAccountsReturnCombinations


}
