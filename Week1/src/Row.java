
public class Row{
  private int age;
  private int height;
  private String degree;
  private double shoeSize;

  public Row(int age, int height, String degree, double shoeSize) {
    this.age = age;
    this.height = height;
    this.degree = degree;
    this.shoeSize = shoeSize;
  }

  public int getAge() {
    return age;
  }

  public int getHeight() {
    return height;
  }

  public String getDegree() {
    return degree;
  }

  public double getShoeSize() {
    return shoeSize;
  }
}
