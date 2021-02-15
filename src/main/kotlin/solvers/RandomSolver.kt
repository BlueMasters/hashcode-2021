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

package solvers

import PizzaChallenge
import com.github.bluemasters.hashcode.Challenge
import com.github.bluemasters.hashcode.Solver

object RandomSolver : Solver {

    override fun solve(challenge: Challenge) {
        check(challenge is PizzaChallenge) {
            "I can only solve PizzaChallenge"
        }
        challenge.deliveries.clear()

        val allPizzas = challenge.pizzas.indices.toMutableList()
        val allTeams = challenge.teamSizes().flatMapTo(mutableListOf()) { (teamSize, nTeams) ->
            List(nTeams) { teamSize }
        }

        allTeams.shuffle()
        allPizzas.shuffle()

        var remainingPizzas = allPizzas.size
        for (t in allTeams) {
            if (remainingPizzas >= t) {
                val pizzas = (1..t).map { allPizzas[--remainingPizzas] }
                challenge.deliveries.add(pizzas.toSet())
            }
        }
    }

}