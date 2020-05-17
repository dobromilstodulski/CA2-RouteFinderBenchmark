/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package org.example;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.example.GraphNodeAL.findPathBreadthFirst;
import static org.example.GraphNodeAL.findPathDepthFirst;
import static org.example.GraphNodeAL2.findCheapestPathDijkstra;

@Measurement(iterations=10)
@Warmup(iterations=5)
@Fork(value=1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class MyBenchmark {

    ArrayList<Placemark> testArrayList = new ArrayList<>();

    Placemark placemark1 = new Placemark("Landmark", "Colosseum", "null", "0", "0");
    Placemark placemark2 = new Placemark("Landmark", "Pantheon", "null", "0", "0");
    Placemark placemark3 = new Placemark("Landmark", "Leonardo da Vinci International Airport", "null", "0", "0");

    GraphNodeAL<String> Colosseum = new GraphNodeAL<>(placemark1.getName());
    GraphNodeAL<String> Pantheon = new GraphNodeAL<>(placemark2.getName());
    GraphNodeAL<String> LeonardoDaVinci = new GraphNodeAL<>(placemark3.getName());

    GraphNodeAL2<String> ColosseumDjikstra = new GraphNodeAL2<>(placemark1.getName());
    GraphNodeAL2<String> PantheonDjikstra = new GraphNodeAL2<>(placemark2.getName());
    GraphNodeAL2<String> LeonardoDaVinciDjikstra = new GraphNodeAL2<>(placemark3.getName());

    @Setup(Level.Invocation)
    public void setup() {
        testArrayList.add(placemark1);
        testArrayList.add(placemark2);
        testArrayList.add(placemark3);

        Colosseum.connectToNodeUndirected(Pantheon);
        Pantheon.connectToNodeDirected(Colosseum);

        ColosseumDjikstra.connectToNodeDirected(PantheonDjikstra, 14);
        PantheonDjikstra.connectToNodeUndirected(ColosseumDjikstra, 14);
    }

    @Benchmark
    public void ArrayListBenchmark() {
        testArrayList.get(0);
        testArrayList.get(1);
        testArrayList.get(2);
    }

    @Benchmark
    public void DFSBenchmark() {
        List<GraphNodeAL<?>> pathTest = findPathDepthFirst(Colosseum, null, Pantheon);
        if (pathTest != null) {
            for (GraphNodeAL<?> n : pathTest) {
                String output = "\r\n" + (String) n.data;
                System.out.println(output);
            }
        }
    }

    @Benchmark
    public void BFSBenchmark() {
        List<GraphNodeAL<?>> pathTest = findPathBreadthFirst(Colosseum, Pantheon);
        if (pathTest != null) {
            for (GraphNodeAL<?> n : pathTest) {
                String output = "\r\n" + (String) n.data;
                System.out.println(output);
            }
        }
    }

    @Benchmark
    public void DjikstraBenchmark() {
        GraphNodeAL2.CostedPath costedPathTest = findCheapestPathDijkstra(ColosseumDjikstra, PantheonDjikstra);
        for (GraphNodeAL2<?> n : costedPathTest.pathList) {
            String output = "\r\n" + (String) n.data + "\r\n";
            System.out.println(output);
        }
    }

    public static void main(String[] args) throws
            RunnerException, IOException {
        Main.main(args);
    }

}