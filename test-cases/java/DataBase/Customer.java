package DataBase;

import javax.persistence.Id;
import javax.persistence.Entity;
import io.ebean.Model;

//@Entity //commenting this to build the project, will fix later, as this creates the query bean
public class Customer extends Model {

    @Id
    long id;

    String name;

    // getters and setters
    public void setName (String name){
        this.name= name;
    }
}