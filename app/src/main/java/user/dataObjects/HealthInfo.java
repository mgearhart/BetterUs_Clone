package user.dataObjects;

public class HealthInfo {
    public enum BioSex{NONE, FEMALE, MALE};
    private float height, weight;
    private int age;
    private BioSex sex;

    public HealthInfo(BioSex sex, float height, float weight, int age){
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

    public HealthInfo(){}

    public void setSex(BioSex sex){
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

    public BioSex getSex(){return this.sex;}

    public float getHeight(){return this.height;}

    public float getWeight(){return this.weight;}

    public int getAge(){return this.age;}
}
