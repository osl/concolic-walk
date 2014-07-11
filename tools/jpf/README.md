Only the `choco` solver seems to be half-way implemented.  All other
decision procedures fail one way or the other.  (CVC3 has a warning
"Do not use" in its wrapper `ProblemCVC3.java`; Yices requires the
[Yices Lite API][yices-lite], which is no longer available for
download; Choco 2 does not support reals; ...).

Running the CORAL benchmarks triggers a bug (in
`AbstractRealLargeNumber` or so) in v1.0.4 of Choco , which is
commited in the source repository.  Replacing the JAR file
`jpf-symbc/lib/choco-1_2_04.jar` with the
[last release of Choco (v1.0.6)][choco-update] seems to fix the
problem.


[yices-lite]: http://atlantis.seidenberg.pace.edu/wiki/lep/Yices_Java_API_Lite
[choco-update]: http://sourceforge.net/projects/choco/files/choco/1.2.06/
