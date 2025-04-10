package pac;

import java.util.ArrayList;
import java.util.List;

public class Student {
    String name;
    List<String> preferencesList=new ArrayList<>();

    /**
     * constructor for initializing variables of the student object
     * @param name->name of the student
     * @param preferencesList->list of preferences of the student
     */
    public Student(String name, List<String> preferencesList) {
        this.name=name;
        for(int preferencesListIndex=0;preferencesListIndex<preferencesList.size();preferencesListIndex++){
            String preferences=preferencesList.get(preferencesListIndex);
            this.preferencesList.add(preferences);
        }
    }

    /**
     * will get name of the employee
     * @return name->employee
     */
    public String getName(){
        return this.name;
    }

    /**
     * will get preferences of the student
     * @return list containing preferences of the student
     */
    public List<String> getPreferencesList(){
        return preferencesList;
    }
}
