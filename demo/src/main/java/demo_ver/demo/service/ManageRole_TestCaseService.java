package demo_ver.demo.service;

import java.util.ArrayList;
import java.util.List;

import demo_ver.demo.model.ManageRole_TestCase;

public class ManageRole_TestCaseService {

    private static List<ManageRole_TestCase> roleTestCaseList = new ArrayList<ManageRole_TestCase>() {
        {
            add(new ManageRole_TestCase(1234, 4321, (long) 3243));
        }
    };
    
}
