package DataHandling;

import Enums.*;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by lucas on 3/20/2017.
 */
public class Student {
    public Gender gender;
    public Double shoeSize;
    public Double height;
    public Degree s_degree;
    public Interest s_interest_efficient_database;
    public Interest s_interest_predictive_models;
    public Interest s_interest_similar_objects;
    public Interest s_interest_visualization;
    public Interest s_interest_patterns_sets;
    public Interest s_interest_patterns_sequences;
    public Interest s_interest_patterns_graphs;
    public Interest s_interest_patterns_text;
    public Interest s_interest_patterns_images;
    public Interest s_interest_code_algorithms;
    public Interest s_interest_use_tools;
    public List<GamesPlayed> s_games_played;

    public Student (){

    }

    public Student(Student student) {
        this.gender = student.gender;
        this.shoeSize = student.shoeSize;
        this.height = student.height;
        this.s_degree = student.s_degree;
        this.s_interest_efficient_database =student.s_interest_efficient_database;
        this.s_interest_predictive_models = student.s_interest_predictive_models;
        this.s_interest_similar_objects = student.s_interest_similar_objects;
        this.s_interest_visualization = student.s_interest_visualization;
        this.s_interest_patterns_sets = student.s_interest_patterns_sets;
        this.s_interest_patterns_sequences = student.s_interest_patterns_sequences;
        this.s_interest_patterns_graphs = student.s_interest_patterns_graphs;
        this.s_interest_patterns_text = student.s_interest_patterns_text;
        this.s_interest_patterns_images = student.s_interest_patterns_images;
        this.s_interest_code_algorithms = student.s_interest_code_algorithms;
        this.s_interest_use_tools =student.s_interest_use_tools;
        this.s_games_played = student.s_games_played;
    }

    public Student(double height, double shoeSize) {
        this.height = height;
        this.shoeSize = shoeSize;
    }

    @Override
    public String toString() {
        return
                gender +
                "," + shoeSize +
                "," + height +
                "," + s_degree +
                "," + s_interest_efficient_database +
                "," + s_interest_predictive_models +
                "," + s_interest_similar_objects +
                "," + s_interest_visualization +
                "," + s_interest_patterns_sets +
                "," + s_interest_patterns_sequences +
                "," + s_interest_patterns_graphs +
                "," + s_interest_patterns_text +
                "," + s_interest_patterns_images +
                "," + s_interest_code_algorithms +
                "," + s_interest_use_tools +
                "," + s_games_played
                ;
    }

    /**
     * This function compares the distance between two students based only on the height and shoe size.
     * @param another a student to be compared
     * @return the eucledian distance between the two students
     * @throws IllegalAccessException
     */
    public double distanceTo(Student another) throws IllegalAccessException {
        double toalDistance = 0;
        // iterate through all instance variables
        for (Field field: getClass().getDeclaredFields()) {
            if (field.getType().isAssignableFrom(Double.class)) { // if the type is numeral, do the math, otherwise skip it.
                Double distance = Math.abs((double)field.get(this) - (double)field.get(another));
                distance = Math.pow(distance, 2.0);
                toalDistance += distance;
            }
        }

        return Math.sqrt(toalDistance); // eucledian distance
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (gender != student.gender) return false;
        if (shoeSize != null ? !shoeSize.equals(student.shoeSize) : student.shoeSize != null) return false;
        if (height != null ? !height.equals(student.height) : student.height != null) return false;
        if (s_degree != student.s_degree) return false;
        if (s_interest_efficient_database != student.s_interest_efficient_database) return false;
        if (s_interest_predictive_models != student.s_interest_predictive_models) return false;
        if (s_interest_similar_objects != student.s_interest_similar_objects) return false;
        if (s_interest_visualization != student.s_interest_visualization) return false;
        if (s_interest_patterns_sets != student.s_interest_patterns_sets) return false;
        if (s_interest_patterns_sequences != student.s_interest_patterns_sequences) return false;
        if (s_interest_patterns_graphs != student.s_interest_patterns_graphs) return false;
        if (s_interest_patterns_text != student.s_interest_patterns_text) return false;
        if (s_interest_patterns_images != student.s_interest_patterns_images) return false;
        if (s_interest_code_algorithms != student.s_interest_code_algorithms) return false;
        if (s_interest_use_tools != student.s_interest_use_tools) return false;
        return s_games_played != null ? s_games_played.equals(student.s_games_played) : student.s_games_played == null;

    }

    @Override
    public int hashCode() {
        int result = gender != null ? gender.hashCode() : 0;
        result = 31 * result + (shoeSize != null ? shoeSize.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (s_degree != null ? s_degree.hashCode() : 0);
        result = 31 * result + (s_interest_efficient_database != null ? s_interest_efficient_database.hashCode() : 0);
        result = 31 * result + (s_interest_predictive_models != null ? s_interest_predictive_models.hashCode() : 0);
        result = 31 * result + (s_interest_similar_objects != null ? s_interest_similar_objects.hashCode() : 0);
        result = 31 * result + (s_interest_visualization != null ? s_interest_visualization.hashCode() : 0);
        result = 31 * result + (s_interest_patterns_sets != null ? s_interest_patterns_sets.hashCode() : 0);
        result = 31 * result + (s_interest_patterns_sequences != null ? s_interest_patterns_sequences.hashCode() : 0);
        result = 31 * result + (s_interest_patterns_graphs != null ? s_interest_patterns_graphs.hashCode() : 0);
        result = 31 * result + (s_interest_patterns_text != null ? s_interest_patterns_text.hashCode() : 0);
        result = 31 * result + (s_interest_patterns_images != null ? s_interest_patterns_images.hashCode() : 0);
        result = 31 * result + (s_interest_code_algorithms != null ? s_interest_code_algorithms.hashCode() : 0);
        result = 31 * result + (s_interest_use_tools != null ? s_interest_use_tools.hashCode() : 0);
        result = 31 * result + (s_games_played != null ? s_games_played.hashCode() : 0);
        return result;
    }
}
