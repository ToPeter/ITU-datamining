package data;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Iris {

	private float Sepal_Length;
	private float Sepal_Width;
	private float Petal_Length;
	private float Petal_Width;
	private IrisClass Class;
	
	public Iris(float sepal_length, float sepal_width, float petal_length, float petal_width, String iris_class)
	{
		this(sepal_length,sepal_width,petal_length,petal_width,ResolveIrisClass(iris_class));
	}
	
	public Iris(float sepal_length, float sepal_width, float petal_length, float petal_width, IrisClass iris_class)
	{
		this.Sepal_Length = sepal_length;
		this.Sepal_Width = sepal_width;
		this.Petal_Length = petal_length;
		this.Petal_Width = petal_width;
		this.Class = iris_class;
	}
	
	private static IrisClass ResolveIrisClass(String iris_class)
	{
		if(iris_class.equals("Iris-setosa"))
		{
			return IrisClass.Iris_setosa;
		}
		else if(iris_class.equals("Iris-versicolor"))
		{
			return IrisClass.Iris_versicolor;
		}
		else if(iris_class.equals("Iris-virginica"))
		{
			return IrisClass.Iris_virginica;
		}
		
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Iris iris = (Iris) o;

		if (Float.compare(iris.Sepal_Length, Sepal_Length) != 0) return false;
		if (Float.compare(iris.Sepal_Width, Sepal_Width) != 0) return false;
		if (Float.compare(iris.Petal_Length, Petal_Length) != 0) return false;
		if (Float.compare(iris.Petal_Width, Petal_Width) != 0) return false;
		return Class == iris.Class;

	}

	@Override
	public int hashCode() {
		int result = (Sepal_Length != +0.0f ? Float.floatToIntBits(Sepal_Length) : 0);
		result = 31 * result + (Sepal_Width != +0.0f ? Float.floatToIntBits(Sepal_Width) : 0);
		result = 31 * result + (Petal_Length != +0.0f ? Float.floatToIntBits(Petal_Length) : 0);
		result = 31 * result + (Petal_Width != +0.0f ? Float.floatToIntBits(Petal_Width) : 0);
		return result;
	}

	@Override
	public String toString() {
		String result = "Iris Object --> | Sepal_Length = "+this.Sepal_Length+" | Sepal_Width = "+this.Sepal_Width+" | Petal_Length = "+this.Petal_Length + " | Petal_Width = "+this.Petal_Width + " | Class = "+this.Class;
		
		return result;
	}

	public double distanceTo(Iris another) throws IllegalAccessException {
		double totalDistanceNumeral = 0;
		int totalDistanceNominal = 0;

		// iterate through all instance variables
		for (Field field: getClass().getDeclaredFields()) {
			if (field.getType() == Float.TYPE) { // if the type is numeral, do the math.
				double distance = Math.abs(field.getFloat(this) - field.getFloat(another));
				distance = Math.pow(distance, 2.0);
				totalDistanceNumeral += distance;
			}
			else { // if the type is nominal, consider 1 if they are different, and 0 if the same
				if (field.get(this) != field.get(another)) {
					totalDistanceNominal ++;
				}
			}
		}

		return Math.sqrt(totalDistanceNumeral) + totalDistanceNominal; // eucledian distance
	}

	public float getSepal_Length() {
		return Sepal_Length;
	}

	public float getSepal_Width() {
		return Sepal_Width;
	}

	public float getPetal_Length() {
		return Petal_Length;
	}

	public float getPetal_Width() {
		return Petal_Width;
	}

	public void setSepal_Length(float sepal_Length) {
		Sepal_Length = sepal_Length;
	}

	public void setSepal_Width(float sepal_Width) {
		Sepal_Width = sepal_Width;
	}

	public void setPetal_Length(float petal_Length) {
		Petal_Length = petal_Length;
	}

	public void setPetal_Width(float petal_Width) {
		Petal_Width = petal_Width;
	}
}
