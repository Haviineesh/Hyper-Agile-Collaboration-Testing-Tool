package demo_ver.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity

public class ViewCase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int idtest_cases;
    private String test_desc;

    public int getIdtest_cases() {
        return idtest_cases;
    }
    public void setIdtest_cases(int idtest_cases) {
        this.idtest_cases = idtest_cases;
    }
    public String getTest_desc() {
        return test_desc;
    }
    public void setTest_desc(String test_desc) {
        this.test_desc = test_desc;
    }
    public ViewCase(String test_desc) {
        this.test_desc = test_desc;
    }

    
    
}
