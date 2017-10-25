# Table of Contents
* [Launch Information](#team-members)
* [Algorithm Information](#algorithm-info)
* [Tests Summary](#tests-summary)

# <a name="team-members"></a> Launch Information
* Run Main.java to launch both linear and parallel algorithms with time tracking for performance comparison. 
Matrices of 1500x1500 used as test case
* Run `mvn test` in terminal.
# <a name="algorithm-info"></a> Algorithm information
[Strassen algorithm](https://en.wikipedia.org/wiki/Strassen_algorithm) used in order to implement parallel multiplication
# <a name="#tests-summary"></a> Tests summary
Tests included:
* Check for wrong matrices sizes incoming to linear multiplication
(first matrix column length does not equal to second matrix row length)
* Testing for multiplication correctness
* Performance testing