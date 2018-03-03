package KNearestNeighbours;

import DataHandling.Student;
import Enums.Degree;

import java.util.*;

/**
 * Created by lucas on 3/23/2017.
 */
public class KNearest {
    private Map<Double, List<Student>> distancesToStudents;
    private List<Student> studentDatabase;
    private int k;
    public int correct = 0;
    public int incorrect = 0;

    public KNearest(List<Student> trainingSet, int k) {
        studentDatabase = trainingSet;
        this.k = k;
        distancesToStudents = new TreeMap<>();
    }

    /**
     * Calculates a distance between two students based on their interests.
     * @param student1 student1
     * @param student2 student2
     * @return a double representing the distance between the two objects.
     */
    public static double calculateDistances (Student student1, Student student2) {
        double distance = 0;
        distance += Math.pow(student1.s_interest_efficient_database.getNumVal() - student2.s_interest_efficient_database.getNumVal(),2);
        distance += Math.pow(student1.s_interest_predictive_models.getNumVal() - student2.s_interest_predictive_models.getNumVal(),2);
        distance += Math.pow(student1.s_interest_similar_objects.getNumVal() - student2.s_interest_similar_objects.getNumVal(),2);
        distance += Math.pow(student1.s_interest_visualization.getNumVal() - student2.s_interest_visualization.getNumVal(),2);
        distance += Math.pow(student1.s_interest_patterns_sets.getNumVal() - student2.s_interest_patterns_sets.getNumVal(),2);
        distance += Math.pow(student1.s_interest_patterns_sequences.getNumVal() - student2.s_interest_patterns_sequences.getNumVal(),2);
        distance += Math.pow(student1.s_interest_patterns_graphs.getNumVal() - student2.s_interest_patterns_graphs.getNumVal(),2);
        distance += Math.pow(student1.s_interest_patterns_text.getNumVal() - student2.s_interest_patterns_text.getNumVal(),2);
        distance += Math.pow(student1.s_interest_patterns_images.getNumVal() - student2.s_interest_patterns_images.getNumVal(),2);
        distance += Math.pow(student1.s_interest_code_algorithms.getNumVal() - student2.s_interest_code_algorithms.getNumVal(),2);
        distance += Math.pow(student1.s_interest_use_tools.getNumVal() - student2.s_interest_use_tools.getNumVal(),2);

        return Math.sqrt(distance);
    }

    /**
     * This function populates the map with the distance from this student that is to be classified
     * to all other students in the database
     * @param student the student
     */
    private void populateDistances(Student student) {
        distancesToStudents = new TreeMap<>();

        for (int i = 0; i < studentDatabase.size(); i++) {
            Student currentStudent = studentDatabase.get(i);
            Double distance = calculateDistances(student, currentStudent);
            if (!distancesToStudents.containsKey(distance)) {
                List<Student> currentList = new ArrayList<>();
                currentList.add(currentStudent);
                distancesToStudents.put(distance, currentList);
            } else {
                distancesToStudents.get(distance).add(currentStudent);
            }
        }
    }

    /**
     * This function classifies a student degree based on his/hers nearest neighbours
     * @param student the student to be classified
     */
    public void classifyStudent (Student student) {
        populateDistances(student);
        Map<Degree, Integer> neighbours = countNeighbours();
        Degree degreeToAssign = null;
        int highestNeighbor = 0;
        for (Degree key: neighbours.keySet()){
            if (neighbours.get(key) > highestNeighbor) degreeToAssign = key;
            highestNeighbor = neighbours.get(key);
        }

        // check if it was classified correctly or not
        if (degreeToAssign!= student.s_degree) incorrect++;
        else correct++;

    }

    /**
     * This function returns a map containing the degree of the k closest neighbours to the particular student being
     * classified.
     * @return A map relating a particular degree to the count of neighbours closest to the student being classified
     */
    private Map<Degree, Integer> countNeighbours() {
        int neighbours = k;
        Map<Degree, Integer> counter = new HashMap<>();

        for (Double key : distancesToStudents.keySet()) {
            List<Student> currentList = distancesToStudents.get(key);
            for (int i = 0; i < currentList.size(); i++) {
                if (counter.containsKey(currentList.get(i).s_degree)) {
                    counter.put(currentList.get(i).s_degree, counter.get(currentList.get(i).s_degree) + 1);
                } else counter.put(currentList.get(i).s_degree, 1);
                neighbours--;
                if (neighbours <= 0) break;
            }
            if (neighbours <= 0) break;
        }
        return counter;
    }

    /**
     * This function calculates the accuracy of the model given a tests set
     * @param testSet a list of students to be tested against
     * @return the accuracy as being correct / total
     */
    public double accuracy (List<Student> testSet) {
        for (Student s: testSet) {
            classifyStudent(s);
        }
        //System.out.println("correct " + correct + "incorrect " + incorrect+ "total " + (correct + incorrect));
        return (double) correct / (correct + incorrect);
    }
}
