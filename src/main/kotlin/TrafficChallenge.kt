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

import com.github.bluemasters.hashcode.Challenge
import com.github.bluemasters.hashcode.OutputManager
import java.io.PrintWriter
import java.nio.file.Path
import java.util.*


class TrafficChallenge(inFile: Path, outputManager: OutputManager) :
    Challenge(inFile, outputManager) {

    lateinit var simulation: Simulation

    /**
     * Parse input file
     */
    override fun parseIn(scanner: Scanner) {
        val duration = scanner.nextInt()
        val nIntersection = scanner.nextInt()
        val nStreet = scanner.nextInt()
        val nCar = scanner.nextInt()
        val bonus = scanner.nextInt()

        val streets = (0 until nStreet).map {
            Street(
                intersectionStart = scanner.nextInt(),
                intersectionEnd = scanner.nextInt(),
                name = scanner.next(),
                length = scanner.nextInt()
            )
        }.map { it.name to it }.toMap()

        val cars = (0 until nCar).map {
            Car(
                streets = (1..scanner.nextInt()).map { streets[scanner.next()]!! }.toMutableList(),
            )
        }

        simulation = Simulation(
            duration,
            bonus,
            (0 until nIntersection).map { Intersection(it) },
            streets.values.toMutableList(),
            cars
        )

    }

    /**
     * Compute the score of the solution
     */
    override val score: Long
        get() {
            return 0 // you are crap
        }

    /**
     * Output Writer
     */
    override fun writeOut(writer: PrintWriter) {
        // TODO
    }


    /**
     * Output Reader (to read an already solved challenge)
     */
    override fun parseOut(scanner: Scanner) {
        // TODO
    }

    override fun toString(): String {
        // TODO
        return ""
    }
}

