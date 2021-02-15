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

open class SolverRegistry {
    private val solvers = linkedMapOf<String, Solver>()

    fun Solver.register(name: String? = null) {
        val registeredName = name ?: this::class.simpleName.toString()
        solvers[registeredName] = this
    }

    val availableNames: Set<String>
        get() = solvers.keys

    val default: String
        get() = solvers.keys.firstOrNull() ?: throw RuntimeException("No solver available.")

    fun getByName(name: String): Solver = solvers[name]
        ?: throw RuntimeException("The solver '$name' is not registered. Available solvers are ${solvers.keys}")
}