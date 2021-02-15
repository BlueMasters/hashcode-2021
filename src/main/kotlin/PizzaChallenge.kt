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

const val MIN_TEAM_SIZE = 2
const val MAX_TEAM_SIZE = 4

data class Pizza(val ingredients: Set<String>)

class PizzaChallenge(inFile: Path, outputManager: OutputManager) :
    Challenge(inFile, outputManager) {

    val pizzas = mutableListOf<Pizza>()
    val deliveries = mutableListOf<Set<Int>>()
    private val numberOfTeams = IntArray(MAX_TEAM_SIZE - MIN_TEAM_SIZE + 1)

    /**
     * returns the number of teams for this size
     */
    private fun numberOfTeams(teamSize: Int): Int {
        return numberOfTeams[teamSize - MIN_TEAM_SIZE]
    }

    /**
     * returns A map of teamSize, numberOfTeams
     */
    fun teamSizes(): Map<Int, Int> {
        return numberOfTeams.indices.map { MIN_TEAM_SIZE + it to numberOfTeams[it] }.toMap()
    }

    /**
     * Parse input file
     */
    override fun parseIn(scanner: Scanner) {
        val nOfPizzas = scanner.nextInt()
        numberOfTeams.indices.forEach {
            numberOfTeams[it] = scanner.nextInt()
        }
        repeat(nOfPizzas) {
            val ingredients = (1..scanner.nextInt()).mapTo(mutableSetOf()) { scanner.next() }
            pizzas.add(Pizza(ingredients))
        }
    }

    /**
     * Compute the score of the solution
     */
    override val score: Long
        get() {
            val usedPizzas = mutableSetOf<Int>()
            val deliveredTeams = IntArray(MAX_TEAM_SIZE - MIN_TEAM_SIZE + 1)

            val nUniqueIngredientsPerDelivery = deliveries.asSequence().map { delivery ->
                deliveredTeams[delivery.size - MIN_TEAM_SIZE] += 1
                check(deliveredTeams[delivery.size - MIN_TEAM_SIZE] <= numberOfTeams(delivery.size)) {
                    "Too many pizzas delivered to teams of ${delivery.size}"
                }
                val ingredients = delivery.flatMap { pizzaId ->
                    check(usedPizzas.add(pizzaId)) { "Pizza $pizzaId used more than once" }
                    pizzas[pizzaId].ingredients
                }
                ingredients.toSet().size
            }

            return nUniqueIngredientsPerDelivery.map { it.toLong() * it }.sum()
        }

    /**
     * Output Writer
     */
    override fun writeOut(writer: PrintWriter) {
        writer.println(deliveries.size)
        for (i in deliveries) {
            writer.println("${i.size} ${i.joinToString(" ")}")
        }
    }


    /**
     * Output Reader (to read an already solved challenge)
     */
    override fun parseOut(scanner: Scanner) {
        deliveries.clear()
        repeat(scanner.nextInt()) {
            val pizzas = (1..scanner.nextInt()).map { scanner.nextInt() }
            deliveries.add(pizzas.toSet())
        }
    }

    override fun toString(): String {
        val res = StringBuilder()
        res.appendLine("--- Pizza generic.Challenge : HashCode 2021 ----")
        // res.appendLine("${pizzas.size} Pizzas")
        for (i in MIN_TEAM_SIZE..MAX_TEAM_SIZE) {
            res.appendLine("${numberOfTeams(i)} teams of $i")
        }
        for (p in pizzas) {
            res.appendLine(p.toString())
        }
        return res.toString()
    }
}


