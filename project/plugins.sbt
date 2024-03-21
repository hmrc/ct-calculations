val artefactsUrl: String = "https://open.artefacts.tax.service.gov.uk"
resolvers += "Typesafe Releases" at "https://repo.typesafe.com/typesafe/releases/"
resolvers += "HMRC-open-artefacts-maven" at (artefactsUrl + "/maven2")
resolvers += Resolver.url("HMRC-open-artefacts-ivy", url(artefactsUrl + "/ivy2"))(Resolver.ivyStylePatterns)
resolvers += "Typesafe Releases" at "https://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("uk.gov.hmrc" % "sbt-auto-build" % "3.19.0")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.9")