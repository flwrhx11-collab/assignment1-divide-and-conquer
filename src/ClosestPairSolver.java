public class ClosestPairSolver {

    public static double solve(Point[] points) {
        if (points == null || points.length < 2) {
            return Double.POSITIVE_INFINITY;
        }
        Point[] pointsByX = points.clone();
        sortPointsByX(pointsByX, 0, pointsByX.length - 1);

        Point[] pointsByY = points.clone();
        sortPointsByY(pointsByY, 0, pointsByY.length - 1);

        return closest(pointsByX, pointsByY, 0, points.length - 1);
    }

    private static void sortPointsByX(Point[] points, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        sortPointsByX(points, lo, mid);
        sortPointsByX(points, mid + 1, hi);

        Point[] aux = new Point[hi - lo + 1];
        int i = lo, j = mid + 1, k = 0;
        while (i <= mid && j <= hi) {
            if (points[i].x <= points[j].x) aux[k++] = points[i++];
            else aux[k++] = points[j++];
        }
        while (i <= mid) aux[k++] = points[i++];
        while (j <= hi) aux[k++] = points[j++];
        System.arraycopy(aux, 0, points, lo, aux.length);
    }

    private static void sortPointsByY(Point[] points, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        sortPointsByY(points, lo, mid);
        sortPointsByY(points, mid + 1, hi);

        Point[] aux = new Point[hi - lo + 1];
        int i = lo, j = mid + 1, k = 0;
        while (i <= mid && j <= hi) {
            if (points[i].y <= points[j].y) aux[k++] = points[i++];
            else aux[k++] = points[j++];
        }
        while (i <= mid) aux[k++] = points[i++];
        while (j <= hi) aux[k++] = points[j++];
        System.arraycopy(aux, 0, points, lo, aux.length);
    }

    private static double bruteForce(Point[] pointsByX, int lo, int hi) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = lo; i <= hi; i++) {
            for (int j = i + 1; j <= hi; j++) {
                double dist = pointsByX[i].distanceTo(pointsByX[j]);
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }

    private static double closest(Point[] pointsByX, Point[] pointsByY, int lo, int hi) {
        if (hi - lo <= 3) {
            return bruteForce(pointsByX, lo, hi);
        }

        int mid = lo + (hi - lo) / 2;
        Point midPoint = pointsByX[mid];

        Point[] yLeft = new Point[mid - lo + 1];
        Point[] yRight = new Point[hi - mid];
        int leftIndex = 0;
        int rightIndex = 0;

        for (Point point : pointsByY) {
            if (point.x <= midPoint.x && leftIndex < yLeft.length) {
                yLeft[leftIndex++] = point;
            } else {
                yRight[rightIndex++] = point;
            }
        }

        double deltaLeft = closest(pointsByX, yLeft, lo, mid);
        double deltaRight = closest(pointsByX, yRight, mid + 1, hi);
        double delta = Math.min(deltaLeft, deltaRight);

        Point[] strip = new Point[hi - lo + 1];
        int stripSize = 0;
        for (Point point : pointsByY) {
            if (Math.abs(point.x - midPoint.x) < delta) {
                strip[stripSize++] = point;
            }
        }

        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1; j < stripSize && (strip[j].y - strip[i].y) < delta; j++) {
                double distance = strip[i].distanceTo(strip[j]);
                if (distance < delta) {
                    delta = distance;
                }
            }
        }

        return delta;
    }
}