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

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.path
import org.slf4j.LoggerFactory
import java.nio.file.Path

/**
 * The Cli class implements the command line interface of your hashcode
 */
class Cli(val solvers: SolverRegistry, val challenge: (Path, OutputManager) -> Challenge) {

    inner class RunCli : CliktCommand() {
        override fun run() = Unit
    }

    inner class Solve : CliktCommand(help = "Solve the challenge") {
        private val source: List<Path> by argument().path(mustExist = true).multiple()
        private val debug by option("--debug", "-d").flag(default = false)
        private val quiet by option("--quiet", "-q").flag(default = false)
        private val keep: Int by option(help = "Number of results (per source) to keep")
            .int().default(1)

        private val outputDir by option("--outputDir", "-o")
            .path().default(Path.of("out"))
        private val outputExt by option("--outputExt").default("out")
        private val solver by option(
            "-s",
            "--solver",
            help = "Solver name, one of: ${solvers.availableNames}"
        ).default(solvers.default)

        override fun run() {
            val logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger
            logger.level = if (debug) Level.DEBUG else if (quiet) Level.WARN else Level.INFO
            val outputManager = OutputManager(outputDir = outputDir, outputExt = outputExt)
            val solver = solvers.getByName(solver)
            source
                .map{ challenge(it, outputManager)}
                .forEach { challenge ->
                challenge.read()
                challenge.solve(solver)
                challenge.write()
                if (keep > 0) {
                    challenge.cleanup(keep)
                }
            }
        }
    }

    inner class Cleanup : CliktCommand(help = "Cleanup the outputs") {
        private val outputDir by option("--outputDir", "-o").path().default(Path.of("out"))
        private val outputExt by option("--outputExt").default("out")
        private val keep: Int by option(help = "Number of results (per input) to keep")
            .int().default(1)

        override fun run() {
            val h = OutputManager(outputDir = outputDir, outputExt = outputExt)
            h.cleanupAll(keep)
        }
    }

    fun main(args: Array<String>) = RunCli()
        .subcommands(Solve(), Cleanup())
        .main(args)

}