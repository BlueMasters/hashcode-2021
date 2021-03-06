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

/**
 * Don't forget to register your solver here!
 */

import com.github.bluemasters.hashcode.SolverRegistry
import solvers.RandomSolver
import solvers.StarSolver
import solvers.StatsSolver
import solvers.TestSolver

object TrafficSolvers : SolverRegistry() {
    init {
        RandomSolver.register()
        TestSolver.register()
        StatsSolver.register()
        StarSolver.register()
    }
}