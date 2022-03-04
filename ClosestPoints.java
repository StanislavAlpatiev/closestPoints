package closestPoints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ClosestPoints {

	/**
	 *
	 * @param points
	 * @return array of two points that build the pair with the closest points
	 */
	public static Point[] findClosestPairOfPoints(Point[] points){
		Point[] sortedByXAxis = sortPointsByX(points);
//		Point[] sortedByYAxis = sortPointsByY(points);
		return findClosestPairOfPointsRecursiveStep(sortedByXAxis, 0, points.length);
	}

	/**
	 *
	 * @param pointsX
	 * @param fromIndex
	 * @param toIndex
	 * @return array with the two closest point from given array of points
	 */
	private static Point[] findClosestPairOfPointsRecursiveStep(Point[] pointsX, int fromIndex, int toIndex) {
		Point[] pointPair;
//		if (toIndex - fromIndex == 2) {
//			return findClosestPairOfPointsBruteForce(pointsX, pointsY, fromIndex, toIndex);
//		}

		if (toIndex - fromIndex <= 3) {
			return findClosestPairOfPointsBruteForce(pointsX, fromIndex, toIndex);
		}

		int middleIndex = (fromIndex + toIndex) / 2;
//		Point middlePoint = pointsX[middleIndex];
		Point[] closestPointsFirstHalf = findClosestPairOfPointsRecursiveStep(pointsX, fromIndex, middleIndex + 1);
		Point[] closestPointsSecondHalf = findClosestPairOfPointsRecursiveStep(pointsX, middleIndex, toIndex);

		double pointsFirstHalfDistance = closestPointsFirstHalf[0].distanceTo(closestPointsFirstHalf[1]);
		double pointsSecondHalfDistance = closestPointsSecondHalf[0].distanceTo(closestPointsSecondHalf[1]);

		pointPair = pointsFirstHalfDistance < pointsSecondHalfDistance ? closestPointsFirstHalf : closestPointsSecondHalf;
		double pointPairDistance = Math.min(pointsFirstHalfDistance, pointsSecondHalfDistance);

//		List<Point> pointsAroundMiddleOfTwoHalves = new ArrayList<>();
//		pointsAroundMiddleOfTwoHalves.add(middlePoint);
//		for(int i = middleIndex + 1; i < toIndex; i++) {
//			if (Math.abs(pointsX[i].getX() - middlePoint.getX()) <= pointPairDistance) {
//				pointsAroundMiddleOfTwoHalves.add(pointsX[i]);
//			} //else {
////				while(i+1 < toIndex && pointsX[i].getX() != pointsX[i+1].getX()){
////					pointsAroundMiddleOfTwoHalves.add(pointsX[i]);
////					i++;
////				}
////				break;
////			}
//		}
//
//		for(int i = middleIndex - 1; i >= fromIndex; i--) {
//			if (Math.abs(pointsX[i].getX() - middlePoint.getX()) <= pointPairDistance) {
//				pointsAroundMiddleOfTwoHalves.add(pointsX[i]);
//			} //else {
////				while(i-1 >= fromIndex && pointsX[i].getX() != pointsX[i-1].getX()){
////					pointsAroundMiddleOfTwoHalves.add(pointsX[i]);
////					i--;
////				}
////				break;
////			}
//		}

//		for (Point point : pointsY) {
//			if (Math.abs(point.getX() - middlePoint.getX()) < pointPairDistance) {
//				pointsAroundMiddleOfTwoHalves.add(point);
//			}
//		}

//		for (int i = 0; i < pointsAroundMiddleOfTwoHalves.size(); i++){
//			for (int j = i+1; j < pointsAroundMiddleOfTwoHalves.size(); j++){
//				int distY = pointsAroundMiddleOfTwoHalves.get(i).getY() - pointsAroundMiddleOfTwoHalves.get(j).getY();
//				if (distY < pointPairDistance){
//					double dist = pointsAroundMiddleOfTwoHalves.get(i).distanceTo(pointsAroundMiddleOfTwoHalves.get(j));
//					if (dist < pointPairDistance){
//						pointPairDistance = dist;
//						pointPair[0] = pointsAroundMiddleOfTwoHalves.get(i);
//						pointPair[1] = pointsAroundMiddleOfTwoHalves.get(j);
//					}
//				}
//			}
//		}
		Point[] strip = getStripArray(pointsX, /**middlePoint,**/ middleIndex, fromIndex, toIndex, pointPairDistance);
		Point[] temp = findClosestPairOfPointsBruteForce(strip, 0, strip.length);

		if (temp[0] != null && temp[1] != null) {
			return pointPairDistance < temp[0].distanceTo(temp[1]) ? pointPair : temp;
		}

		return pointPair;
	}

	/**
	 *
	 * @param points
	 * @param middleIndex
	 * @param fromIndex
	 * @param toIndex
	 * @param pointPairDistance
	 * @return array with the points that are inside the strip bounds
	 */
	private static Point[] getStripArray(Point[] points, /**Point middlePoint,**/ int middleIndex, int fromIndex, int toIndex, double pointPairDistance) {
		List<Point> pointsAroundMiddleOfTwoHalves = new ArrayList<>();
//		pointsAroundMiddleOfTwoHalves.add(middlePoint);
		for(int i = middleIndex; i < toIndex; i++) {
			if (Math.abs(points[i].getX() - points[middleIndex].getX()) < pointPairDistance) {
				pointsAroundMiddleOfTwoHalves.add(points[i]);
			} else {
				while(i+1 < toIndex && points[i].getX() != points[i+1].getX()){
					pointsAroundMiddleOfTwoHalves.add(points[i]);
					i++;
				}
				break;
			}
		}

		for(int i = middleIndex - 1; i >= fromIndex; i--) {
			if (Math.abs(points[i].getX() - points[middleIndex].getX()) < pointPairDistance) {
				pointsAroundMiddleOfTwoHalves.add(points[i]);
			} else {
				while(i-1 >= fromIndex && points[i].getX() != points[i-1].getX()){
					pointsAroundMiddleOfTwoHalves.add(points[i]);
					i--;
				}
				break;
			}
		}
		return pointsAroundMiddleOfTwoHalves.toArray(new Point[pointsAroundMiddleOfTwoHalves.size()]);
	}

	/**
	 *
	 * @param points
	 * @param fromIndex
	 * @param toIndex
	 * @return array with the two points that have the shortest distance between them
	 */
	private static Point[] findClosestPairOfPointsBruteForce(Point[] points, int fromIndex, int toIndex) {
		Point[] pointPair = new Point[2];
		double closestDist = Double.MAX_VALUE;
		for (int i = fromIndex; i < toIndex; i++) {
			for (int j = i + 1; j < toIndex; j++) {
				double currentPairDistance = points[i].distanceTo(points[j]);
				if (currentPairDistance < closestDist) {
					closestDist = currentPairDistance;
					pointPair[0] = points[i];
					pointPair[1] = points[j];
				}
			}
		}
		return pointPair;
	}

	/**
	 *
	 * @param points
	 * @return array of points in sorted order by x value
	 */
	private static Point[] sortPointsByX(Point[] points) {
		return getPointsSortedByAxis(points, Comparator.comparingInt(Point::getX));
	}

//	private static Point[] sortPointsByY(Point[] points) {
//		return getPointsSortedByAxis(points, Comparator.comparingInt(Point::getY));
//	}

	/**
	 *
	 * @param points
	 * @param pointComparator
	 * @return array of points in sorted order according to passed comparator
	 */
	private static Point[] getPointsSortedByAxis(Point[] points, Comparator<Point> pointComparator) {
		Arrays.sort(points, pointComparator);
		return points;
	}
}
