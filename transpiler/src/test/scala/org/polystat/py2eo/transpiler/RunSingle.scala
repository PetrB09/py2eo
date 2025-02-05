package org.polystat.py2eo.transpiler

import org.junit.Test

import java.io.File

class RunSingle extends Commons {
  val simpleTestsFolder = new File(testsPrefix + File.separator + "simple-tests" + File.separator)

  @Test def singleTest(): Unit = {
    val testPath = simpleTestsFolder + "/simple-statements/annotated-assignment/annotated-2.yaml"
    useCageHolder(new File(testPath))
  }
}