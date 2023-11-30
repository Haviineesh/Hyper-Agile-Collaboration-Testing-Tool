package demo_ver.demo.service;

import org.springframework.stereotype.Service;
import demo_ver.demo.model.ManageRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ManageRoleService {

    private static List<ManageRole> roleList = new ArrayList<ManageRole>(Arrays.asList(
            new ManageRole(4055, "Tester", "unit tester"),
            new ManageRole(5055, "Product Manager", "manage production")));

    public static List<ManageRole> getAllRoles() {
        return new ArrayList<ManageRole>(roleList);
    }

}
