Installing Pex
==============

In a Windows 7 virtual machine:

* Download and install the [Microsoft .NET Framework 4 (Web
  Installer)](http://www.microsoft.com/en-us/download/details.aspx?id=17851)

* Download and install Visual Studio 2010 (available via MSDN Academic
  Alliance).

* Download and install
  [Pex](http://research.microsoft.com/en-use/projects/pex/Downloads.aspx).
  Choose the _academic_ version.  Make sure to install the Visual
  Studio 2010 integration.
  

Generating Test Cases
=====================

* Open the `PexEvaluation` _solution_ (= Eclipse workspace) in Visual
  Studio.  The solution and all related projects are located in the
  `experiments/src/programs.c#/` directory.

* Right-click on any of the `*Test` projects and choose `Run Pex
  Explorations`.
  
* After the run has finished, export a text file with the test
  details: in the _Pex Explorer_ sub-window right-click on the root
  namespace and select `Send To > Text file`.  A new editing window
  labeled `testdetails.txt` should open.
  
* Via the `File > Save testdetails As` menu, save the generated test
  cases in an appropriate place (such as one of the
  `experiments/res/pex/run*/` directories).

