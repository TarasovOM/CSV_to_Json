public class Employee {
    public long id;
    public String firstName;
    public String lastName;
    public String country;
    public int age;

    public Employee() {
        // Пустой конструктор
    }

    public Employee(long id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }

    @Override
    public String toString() {
        return STR."id= '\{this.id}', Name: '\{this.firstName}', lastName: '\{this.lastName}' country: '\{this.country}', age: '\{this.age}'";
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setfirsName(String firstName) {
        this.firstName = firstName;
    }

    public void setlastName(String lastName) {
        this.lastName = String.valueOf(lastName);
    }

    public void setcountry(String country) {
        this.country = country;
    }

    public void setage(int age) {
        this.age = age;
    }
}

