package jmh.benchmark;

import org.openjdk.jmh.annotations.*;
import ca.mcmaster.se2aa4.mazerunner.*;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class TestBenchMark {

    @State(Scope.Benchmark)
    public static class MazeState {
        public Maze maze;
        public String mazeFilePath = "examples/small.maz.txt"; 

        @Setup(Level.Invocation)
        public void loadMaze() {
            try {
            InputStream input = getClass().getClassLoader().getResourceAsStream(mazeFilePath);
            maze = new Maze(input);
            } catch (Exception e) {
            throw new RuntimeException("Failed benchmark:  " + e.getMessage());
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public Maze loadMazeBenchmark(MazeState state) {
        state.loadMaze();
        return state.maze; 
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void solveWithRightHandBenchmark(MazeState state) {
        new RightHandSolver().solve(state.maze);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void solveWithTremauxBenchmark(MazeState state) {
        new TremauxSolver().solve(state.maze);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void solveWithBFSSolverBenchmark(MazeState state) {
        new BreadthFirst().solve(state.maze);
    }
}
