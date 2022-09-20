package app.core.util.xlsx;

import java.util.List;

public class PersonExcelWriter extends AbstractExcelWriter<Person> {
    
    public PersonExcelWriter(List<Person> list) {
        super(list);
    }
}
