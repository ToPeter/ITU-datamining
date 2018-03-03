package DataHandling;

import Enums.Degree;
import Enums.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lucas on 3/20/2017.
 */
public class DataManager {
    /**
     * This function parses the data using a CSV reader and
     * based on a series of constraints determined within each attribute
     * @return a list of students
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static List<Student> parseData() throws NoSuchFieldException, IllegalAccessException {
        List<Student> students = new ArrayList<>();

        try {
            // read the whole dataset
            String[][] rawData = CSVFileReader.readDataFile("./resources/data.csv",";;", "-",false);
            //System.out.println("Number of rows loaded: "+rawData.length);

            //extract the relevant attributes
            for (int i = 0; i < rawData.length; i ++) {
                Student studentToAdd = new Student();
                studentToAdd.gender = loadStudentGender(rawData[i][2]);
                studentToAdd.height = loadStudentHeight(rawData[i][4]);
                studentToAdd.shoeSize = loadStudentShoeSize(rawData[i][3]);
                studentToAdd.s_degree = loadStudentDegree(rawData[i][5]);
                studentToAdd.s_interest_efficient_database = loadStudentInterest(rawData[i][9]);
                studentToAdd.s_interest_predictive_models = loadStudentInterest(rawData[i][10]);
                studentToAdd.s_interest_similar_objects = loadStudentInterest(rawData[i][11]);
                studentToAdd.s_interest_visualization = loadStudentInterest(rawData[i][12]);
                studentToAdd.s_interest_patterns_sets = loadStudentInterest(rawData[i][13]);
                studentToAdd.s_interest_patterns_sequences = loadStudentInterest(rawData[i][14]);
                studentToAdd.s_interest_patterns_graphs = loadStudentInterest(rawData[i][15]);
                studentToAdd.s_interest_patterns_text = loadStudentInterest(rawData[i][16]);
                studentToAdd.s_interest_patterns_images = loadStudentInterest(rawData[i][17]);
                studentToAdd.s_interest_code_algorithms = loadStudentInterest(rawData[i][18]);
                studentToAdd.s_interest_use_tools = loadStudentInterest(rawData[i][19]);
                studentToAdd.s_games_played = loadGamesPlayed(rawData[i][20]);
                students.add(studentToAdd);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return students;
    }


    /**
     * This function fills missing values for students without age and/or shoeSize based on the median value for his/her
     * gender
     * @param students a list of students to be evaluated
     * @return a list of students with filled missing values
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static List<Student> fillMissingValues(List<Student> students) throws NoSuchFieldException, IllegalAccessException {
        List<Student> result = new ArrayList<>();
        Double medianShoeSizeMale = median(getAttribute(students, "gender", Gender.male, "shoeSize"));
        Double medianShoeSizeFemale = median(getAttribute(students, "gender", Gender.female, "shoeSize"));
        Double medianHeightMale = median(getAttribute(students, "gender", Gender.male, "height"));
        Double medianHeightFemale = median(getAttribute(students, "gender", Gender.female, "height"));
        int j = 2;
        for (Student s : students) {
/*            System.out.print("row: " + j + " ");
            j++;
            System.out.println(s.toString());*/
            if (s.gender == null) continue; // remove students without defined gender since this is the attribute I am trying to predict
            if (s.gender.equals(Gender.male)) {
                if (s.shoeSize == null) s.shoeSize = medianShoeSizeMale;
                if (s.height == null) s.height = medianHeightMale;
            }
            else {
                if (s.shoeSize == null) s.shoeSize = medianShoeSizeFemale;
                if (s.height == null) s.height = medianHeightFemale;
            }
            result.add(s);
        }
        return result;
    }

    /**
     * This function takes a list of students, a condition for a particular attribute, the attribute to be constrained and the attribute
     * to be returned. For instance, when passing (students, "gender", Gender.male, "shoeSize") as arguments, the function returns
     * a list of shoSizes for all the students where gender = gender.male.
     * @param students a list of students to be evaluated
     * @param fieldCondition the field to be constrained
     * @param fieldValue the value for the constrainted field
     * @param fieldToGet the field to be returned within the list
     * @return a list containing the fieldToGet for all the students
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static List<Double> getAttribute(List<Student> students, String fieldCondition, Object fieldValue, String fieldToGet) throws NoSuchFieldException, IllegalAccessException {
        List<Double> result = new ArrayList<>();
        Field field = Student.class.getField(fieldCondition);
        for (Student s: students) {
            Double fieldToAdd = (Double) Student.class.getField(fieldToGet).get(s);
            Gender fieldToCompare = (Gender) field.get(s);
            if (fieldToAdd == null || fieldToCompare == null ) continue;
            if (fieldToCompare.equals(fieldValue)){
                result.add(fieldToAdd);
            }
        }
        return result;
    }

    /**
     * This function parses the student shoe size while under some constraints. Non numeric characters are removed,
     * and valid shoe size is between 25 and 60
     * @param shoeSize the string of the shoe size to be parsed
     * @return shoe size as a double
     */

    private static Double loadStudentShoeSize(String shoeSize) {
        if (shoeSize == null) return null;
        shoeSize = shoeSize.replaceAll("\"", "").replaceAll(",", ".").replaceAll("[^.0-9]", "").replaceAll(" ", "");
        Double doubleShoeSize;
        try {
            doubleShoeSize = Double.parseDouble(shoeSize);
        } catch (NumberFormatException e) {
            return null;
        }
        if (doubleShoeSize > 60 ||  doubleShoeSize  <25 ) return null;
        return doubleShoeSize;
    }

    /**
     * This function parses the student height while under some constraints. Non numeric characters are removed,
     * and valid height is between 55 and 280
     * @param height the string of the height to be parsed
     * @return height as a double
     */
    private static Double loadStudentHeight(String height) {
        if (height == null) return null;
        height = height.replaceAll("\"", "");
        height = height.replaceAll("[^,.0-9]", "").replaceAll(" ", ""); // removes all non numeric characters
        Double doubleHeight;
        try {
            doubleHeight = Double.parseDouble(height);
        } catch (NumberFormatException e) {
            return null;
        }
        if (doubleHeight > 280 ||  doubleHeight < 55) return null; //smallest and biggest person ever registered
        return doubleHeight;
    }


    /**
     * This function parses the gender based on some observed patterns on the dataset. If the string
     * does not follow any of them, then it is just set to null.
     * @param gender the string that specify the gender
     * @return a Enum gender
     */
    private static Gender loadStudentGender(String gender) {
        if (gender == null) return null;
        gender = gender.replaceAll("\"", "").replaceAll(" ", "");
        gender = gender.toLowerCase();
        if (gender.equals("male") ||  gender.equals("m") || gender.equals("man") ) return Gender.male;
        else if (gender.equals("female") || gender.equals("f") || gender.equals("woman")) return Gender.female;
        else return null;
    }

    /**
     * This function parses the games played for each student
     * @param games the String containing the games played separated by ";"
     * @return a list of games played
     */
    private static List<GamesPlayed> loadGamesPlayed(String games) {
        if (games == null) return null;
        games = games.replaceAll("\"", "");
        String[] arrayOfGames = games.split(";");
        List<GamesPlayed> result = new ArrayList<>();
        for (int i = 0; i < arrayOfGames.length; i++) {
            String currentGame = arrayOfGames[i];
            if (currentGame.equals("Candy Crush")) result.add(GamesPlayed.candy_crush);
            else if (currentGame.equals("Wordfeud")) result.add(GamesPlayed.wordfeud);
            else if (currentGame.equals("Minecraft")) result.add(GamesPlayed.minecraft);
            else if (currentGame.equals("FarmVille")) result.add(GamesPlayed.farmVille);
            else if (currentGame.equals("Fifa 2017")) result.add(GamesPlayed.fifa_2017);
            else if (currentGame.equals("Star Wars Battlefield")) result.add(GamesPlayed.star_wars_battlefield);
            else if (currentGame.equals("Life is Strange")) result.add(GamesPlayed.life_is_strange);
            else if (currentGame.equals("Battlefield 4")) result.add(GamesPlayed.battlefield_4);
            else if (currentGame.equals("Journey")) result.add(GamesPlayed.journey);
            else if (currentGame.equals("Gone Home")) result.add(GamesPlayed.gone_home);
            else if (currentGame.equals("Stanley Parable")) result.add(GamesPlayed.stanley_parable);
            else if (currentGame.equals("Call of Duty: Black Ops")) result.add(GamesPlayed.call_of_duty_black_ops);
            else if (currentGame.equals("Rocket League")) result.add(GamesPlayed.rocket_league);
            else if (currentGame.equals("Bloodthorne")) result.add(GamesPlayed.bloodthorne);
            else if (currentGame.equals("Rise of the Tomb Raider")) result.add(GamesPlayed.rise_of_the_tomb_raider);
            else if (currentGame.equals("The Witness")) result.add(GamesPlayed.the_witness);
            else if (currentGame.equals("Her Story")) result.add(GamesPlayed.her_story);
            else if (currentGame.equals("Fallout 4")) result.add(GamesPlayed.fallout_4);
            else if (currentGame.equals("Dragon Age: Inquisition")) result.add(GamesPlayed.dragon_age_inquisition);
            else if (currentGame.equals("Counter Strike_ GO")) result.add(GamesPlayed.counter_strike_go);
            else if (currentGame.equals("Angry Birds")) result.add(GamesPlayed.angry_birds);
            else if (currentGame.equals("The Last of Us")) result.add(GamesPlayed.the_last_of_us);
            else if (currentGame.equals("The Magic Circle")) result.add(GamesPlayed.the_magic_circle);
            else result.add(GamesPlayed.none);
        }
        return result;
    }

    /**
     * This function parses the student interests
     * @param scale the level of interest
     * @return the enum representing the interest of the student
     */
    private static Interest loadStudentInterest(String scale) {
        if (scale == null) return null;
        scale = scale.replaceAll("\"", "");
        if (scale.equals("Not interested")) return Interest.not_interested;
        else if (scale.equals("Sounds interesting")) return Interest.sounds_interesting;
        else if (scale.equals("Meh")) return Interest.meh;
        else if (scale.equals("Very interesting")) return Interest.very_interesting;
        else return null;
    }

    /**
     * This function parses the student degree
     * @param degree the degree of the student
     * @return the enum representing the degree of the student
     */
    private static Degree loadStudentDegree(String degree) {
        if (degree == null) return null;
        degree = degree.replaceAll("\"", "");
        if (degree.equals("GAMES-A")) return Degree.games_a;
        else if (degree.equals("GAMES-T")) return Degree.games_t;
        else if (degree.equals("SDT-DT")) return Degree.sdt_dt;
        else if (degree.equals("SDT-SE")) return Degree.sdt_se;
        else if (degree.equals("SWU")) return Degree.swu;
        else if (degree.equals("Guest student")) return Degree.guest;
        else return Degree.other;
    }

    /**
     * This function removes students who marked "none" in the games played
     * @param parsedData a list of students
     * @return a list of students with only students that did not marked "none" for games played
     */
    public static List<Student> removeInvalidGames(List<Student> parsedData) {
        List<Student> result = new ArrayList<>();
        for (Student student: parsedData) {
            if (!student.s_games_played.contains(GamesPlayed.none)) result.add(student);
        }
        return result;
    }

    /**
     * calculates the median from the list of doubles
     * @param data list of doubles
     * @return the median of the doubles
     */
    public static Double median (List<Double> data) {
        List<Double> sortedData = data;
        Collections.sort(sortedData);
        Double median;
        if (sortedData.size() % 2 == 0) {
            median = (sortedData.get(sortedData.size() / 2) + sortedData.get(sortedData.size() / 2  - 1)) / 2;
        }
        else median = sortedData.get((int)Math.floor((sortedData.size()/2)));
        return median;
    }

    /**
     * This function normalizes attributes height and shoeSize using Max-min normalization technique
     * @param list List of students
     * @return List of students with normalized values
     */
    public static List<Student> normalizeNumericAttributes(List<Student> list){
        List<Student> result = new ArrayList<>();
        Double minShoeSizeValue = list.stream().mapToDouble(o -> o.shoeSize).min().getAsDouble();
        Double maxShoeSizeValue = list.stream().mapToDouble(o -> o.shoeSize).max().getAsDouble();
        Double minHeightValue = list.stream().mapToDouble(o -> o.height).min().getAsDouble();
        Double maxHeightValue = list.stream().mapToDouble(o -> o.height).max().getAsDouble();

        for (Student s: list) {
            s.height = (s.height - minHeightValue) / (maxHeightValue - minHeightValue);
            s.shoeSize = (s.shoeSize - minShoeSizeValue) / (maxShoeSizeValue - minShoeSizeValue);
            result.add(s);
            //System.out.println(s.toString());
        }

        return result;

    }


}
