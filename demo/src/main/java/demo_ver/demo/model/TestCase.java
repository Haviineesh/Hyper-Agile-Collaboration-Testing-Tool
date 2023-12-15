package demo_ver.demo.model;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class TestCase {
    private Long idtest_cases;
    private String test_desc;
    private String code;

     // Default constructor (no-argument constructor)

    public TestCase(){

    }

    public TestCase(Long idtest_cases, String code, String test_desc) {
        this.idtest_cases = idtest_cases;
        this.test_desc = test_desc;
        this.code = code;
    }
    public Long getIdtest_cases() {
        return idtest_cases;
    }
    public void setIdtest_cases(Long idtest_cases) {
        this.idtest_cases = idtest_cases;
    }
    public String getTest_desc() {
        return test_desc;
    }
    public void setTest_desc(String test_desc) {
        this.test_desc = test_desc;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }


}


    
    

