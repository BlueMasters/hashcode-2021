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

import org.slf4j.LoggerFactory
import java.nio.file.Path

/**
 * The OutputManager manages the output of your challenge.
 */
class OutputManager(
    val outputDir: Path = Path.of("out"),
    val outputExt: String = "out"
) {

    init {
        with(outputDir.toFile()) {
            if (!this.exists()) {
                logger.info("Creating ${this.name} directory")
                this.mkdirs()
            }
        }
    }

    fun cleanupAll(keepN: Int = 1) {
        namesToSortedResultFiles()?.forEach { (_, files) ->
            files.dropLast(keepN).forEach {
                logger.info("Deleting ${it.name}")
                it.delete()
            }
        }
    }

    fun namesToSortedResultFiles() =
        outputDir.toFile().listFiles()
            ?.filter { f -> f.extension == outputExt }
            ?.groupBy { f -> f.name.split(".")[0] }
            ?.mapValues { (_, files) -> files.sortedBy { it.name } }

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }
}