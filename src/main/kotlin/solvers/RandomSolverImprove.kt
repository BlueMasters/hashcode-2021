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

// Lucy Linder / 15. Feb. 2021

package solvers

import PizzaChallenge
import com.github.bluemasters.hashcode.Challenge
import com.github.bluemasters.hashcode.Solver

object RandomSolverImprove : Solver {

    private const val stopCriterion = 100

    override fun solve(challenge: Challenge) {
        check(challenge is PizzaChallenge) {
            "I can only solve PizzaChallenge"
        }

        with(challenge) {
            parseBestResult()
            fun score(d: Collection<Int>) = d.flatMap { pizzas[it].ingredients }.toSet().size

            var noImprovementCounter = 0
            while (noImprovementCounter < stopCriterion) {
                val (idx1, d1) = deliveries.indices.random().let { it to deliveries[it] }
                val (idx2, d2) = deliveries.indices.random().let { it to deliveries[it] }
                if (idx1 == idx2) continue

                val oldScore = score(d1) + score(d2)
                val (d1n, d2n) = (d1 + d2).shuffled().let { mix ->
                    mix.take(d1.size) to mix.drop(d1.size)
                }

                val newScore = score(d1n) + score(d2n)
                if (newScore > oldScore) {
                    println("$noImprovementCounter   $oldScore -> $newScore")
                    deliveries[idx1] = d1n.toSet()
                    deliveries[idx2] = d2n.toSet()
                    noImprovementCounter = 0
                } else {
                    println("$noImprovementCounter")
                    noImprovementCounter += 1
                }

            }
        }
    }

}