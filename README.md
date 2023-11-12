# numerical-methods
JavaFX program that iterates a function's approximations based on the Bisection, Fixed-Point, Newton, and Secant methods. 

Source code is found in: <code>src/main/java/com/example/project1_6318248</code>

The example function and code is based on this assignment: [Project.pdf](https://github.com/reallyprettycool/numerical-methods/files/13329031/Project.pdf)

The algorithms are derived from Chapter 2 of Burden/Faires/Burden's _Numerical Analysis_, (10th edition). 

### Example
<b>Fixed Point Iteration</b>
```java

   public static void fixedPointIteration(double p, double TOL, int N, ArrayList<LineChart> lineCharts) { // Initial approx p, tolerance TOL, N = iterations
        double next = 0;

        ArrayList<XYChart.Series> SeriesArray = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SeriesArray.add(new XYChart.Series());
            SeriesArray.get(i).setName("Fixed-Point");
            lineCharts.get(i).getData().add(SeriesArray.get(i));
        }

        for (int i = 1; i <= N; i++) {
            next = Math.pow(( (3 * Math.pow(p, 2)) + 3 ), .25); // p = g(p0)
            ErrorCalculation(i, next, p, lineCharts, SeriesArray);
            System.out.printf("Value of p at %d iteration(s): %.8f\n", i, next);

            if ((Math.abs(next - p)) < TOL) { // Successful completion of procedure
                System.out.printf("\nFinal value of p after %d iterations: %.8f", i, next);
                break;
            }
            p = next;
        }
    }

```
