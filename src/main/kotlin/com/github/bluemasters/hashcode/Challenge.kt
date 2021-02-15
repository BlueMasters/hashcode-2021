/*
 * Copyright 2021 BlueMasters
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

// Jacques Supcik / 15. Feb. 2021

package com.github.bluemasters.hashcode

import org.slf4j.LoggerFactory.getLogger
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

/**
 * The abstract Challenge class is the base for all hashcode challenges.
 */
abstract class Challenge(private val inFile: Path, private val outputManager: OutputManager) {

    // Those are the functions that you have to override in your challenge
    abstract fun parseIn(scanner: Scanner)
    abstract fun writeOut(writer: PrintWriter)
    abstract fun parseOut(scanner: Scanner)
    abstract val score: Long

    // The "name" of a challenge is the input filename without the extension
    // This will be used for to construct the output file and could be used
    // for logging as well.
    private val name: String
        get() {
            val name = inFile.fileName.toString()
            return if (name.indexOf(".") > 0) {
                name.substring(0, name.lastIndexOf("."))
            } else {
                name
            }
        }

    // Read the input file if a challenge using the "parseIn" abstract function.
    fun read() {
        logger.debug("reading $inFile")
        parseIn(Scanner(inFile.toFile()))
    }

    // Solve the challenge using the given solver
    fun solve(solver: Solver) {
        logger.debug("solving using $solver")
        solver.solve(this)
        logger.debug("score = $score")
    }

    // Write the output file using the "writeOut" abstract function.
    fun write() {
        val tmpFile =
            Files.createTempFile(outputManager.outputDir.toAbsolutePath(), null, ".tmp").toFile()
        val writer = tmpFile.printWriter()
        logger.debug("writing to $tmpFile")
        writeOut(writer)
        writer.close()
        val outFile =
            outputManager.outputDir.resolve("${name}.${"%020d".format(score)}.${outputManager.outputExt}")
                .toFile()
        logger.debug("renaming to $outFile")
        tmpFile.renameTo(outFile)
    }

    // Returns the best result from all outputs.
    fun parseBestResult(nTh: Int = 0) {
        val f = outputManager.namesToSortedResultFiles()?.get(name)?.asReversed()?.getOrNull(nTh)
        check(f != null) {
            "bestResult[${nTh}] not found."
        }
        parseOut(Scanner(f))
    }

    // Cleanup removes output files with the lowest score
    fun cleanup(keepN: Int = 1) {
        outputManager.namesToSortedResultFiles()?.get(name)?.dropLast(keepN)?.forEach {
            logger.info("deleting ${it.name}")
            it.delete()
        }
    }

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = getLogger(javaClass.enclosingClass)
    }

}