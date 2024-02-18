package user.dataObjects;

public class HealthInfo {
    private float height, weight;
    private int sex, age;

    public HealthInfo(int sex, float height, float weight, int age){
        this.sex = sex; // 0 = None; 1 = Female; 2 = Male
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

    public HealthInfo(){}

    public void setSex(int sex){
        this.sex = sex;
    }

    public void setHeight(float height){
        this.height = height;
    }

    public void setWeight(float weight){
        this.weight = weight;
    }

    public void setAge(int age){
        this.age = age;
    }

    public int getSex(){return this.sex;}

    public float getHeight(){return this.height;}

    public float getWeight(){return this.weight;}

    public int getAge(){return this.age;}
}
