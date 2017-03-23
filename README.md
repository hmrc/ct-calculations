# ct-calculations

[![Build Status](https://travis-ci.org/hmrc/ct-calculations.svg?branch=master)](https://travis-ci.org/hmrc/ct-calculations) [ ![Download](https://api.bintray.com/packages/hmrc/releases/ct-calculations/images/download.svg) ](https://bintray.com/hmrc/releases/ct-calculations/_latestVersion)


CT Calculations is a library that contains the domain needed to create a Corporations Tax return for HMRC. This includes implementations of Boxes and calculations required to produce a CT600, including CT600J, CT600A and Computations. 

At the moment this library supports a subset of Boxes and calculations but we are open to supporting the full set of available boxes. 

# Download ct-calculations
```scala
resolvers += Resolver.bintrayRepo("hmrc", "releases")

libraryDependencies += "uk.gov.hmrc" %% "ct-calculations" % "x.x.x"
```

# Future Enhancements
* Support for V3 of CT600
* Support for Accounts Boxes

# License
 
This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
