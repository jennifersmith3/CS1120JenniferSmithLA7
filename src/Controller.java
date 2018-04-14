/* Assignment: 
 * Author: Jennifer N. Smith
 * Date:
 * Reference: 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class Controller implements IController {

	PriorityQueue<Request> requestQueue;
	ArrayList<Course> courses;
	BufferedReader fileIn;
	BufferedReader fileIn1;

	public Controller(PriorityQueue<Request> requestQueue,
			ArrayList<Course> courses, BufferedReader fileIn,
			BufferedReader fileIn1) {

		this.requestQueue = requestQueue;
		this.courses = courses;
		this.fileIn = fileIn;
		this.fileIn1 = fileIn1;
	}

	@Override
	public void readCourseFile() {

		String thisLine;

		try {
			while ((thisLine = fileIn1.readLine()) != null) {
				//System.out.println(thisLine);
				String fields[] = thisLine.split(",");

				if (fields.length == 5) {
					int num = Integer.parseInt(fields[4]);
					Request req = new Request(fields[0], fields[2], fields[1],
							fields[3], num);
					addRequest(req);
				} else {
					System.out.println("Error parsing line " + thisLine);
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void readRequestFile() {
		String thisLine;

		try {
			while ((thisLine = fileIn.readLine()) != null) {
				System.out.println(thisLine);
				String fields[] = thisLine.split(",");

				if (fields.length == 3) {
					int cap = Integer.parseInt(fields[2]);
					int num = Integer.parseInt(fields[1]);

					Course course = new Course(fields[0], num, cap);
					courses.add(course);

				} else {
					System.out.println("Error parsing line " + thisLine);
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void addRequest(Request req) {
		requestQueue.add(req);

	}

	@Override
	public void processRequests() {
		// Process all the requests as follows: if request can be granted,
		// update the relevant classlist,
		// update the class/course capacity, print out a message that the
		// student who made that
		// request has been successfully registered for that course. Else, print
		// out a message that
		// student could not be registered.

		while (!requestQueue.isEmpty()) {
			Request req = requestQueue.poll();
			System.out.println(req+" processed.");
			Course course = getCourse(req.courseDept, req.courseNumber);
			if (course == null) {
				System.out.println("Requested course not found");

			}
			else {
				if (course.isFull()) {
					System.out.println(req.studentName+" cannot register for " +req.courseDept+" "+req.courseNumber);
				} else {
					course.addStudent(req.studentName);
					System.out.println(req.studentName+" successfully registered "+req.courseDept+" "+req.courseNumber);
				}
			}
	
		}
	}

	@Override
	public Course getCourse(String courseDept, int courseNumber) {
		// Return the course object with data values that match the parameters
		// received.

		for (Course oneCourse : courses) {
			if (courseDept.equals(oneCourse.courseDept)
					&& courseNumber == oneCourse.courseNumber) {

				return oneCourse;
			}
		}
		return null;
	}

	@Override
	public void printClassList() {
		// Print classlists for all courses.
		for (Course oneCourse : courses) {
			oneCourse.printClassList();
		}

	}

}