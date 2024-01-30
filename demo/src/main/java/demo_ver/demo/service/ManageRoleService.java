package demo_ver.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo_ver.demo.model.ManageRole;

@Service
public class ManageRoleService {

    private final String API_Base_Url = "http://localhost:8090/api/rolegetallroles";

    private final String API_Add_Role_Url = "http://localhost:8090/api/roleadd"; // Endpoint for adding roles
    private final String API_Delete_Role_Url = "http://localhost:8090/api/roledelete/";

    @Autowired
    private final RestTemplate restTemplate;

    public ManageRoleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static List<ManageRole> roleList = new ArrayList<ManageRole>() {
        {
            add(new ManageRole(1000, "ROLE_Admin", "Administration"));
            add(new ManageRole(1001, "ROLE_Tester", "unit tester"));
            add(new ManageRole(1002, "ROLE_Product Manager", "manage production"));
            add(new ManageRole(1003, "ROLE_Developer", "Programming"));

        }
    };

    public static List<ManageRole> getAllRoles() {
        return roleList;
    }

    public List<ManageRole> apiGetAllRoles() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                API_Base_Url,
                HttpMethod.GET,
                null,
                String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String jsonResponse = responseEntity.getBody();
            return parseJsonResponse(jsonResponse);
        } else {
            // Handle non-OK status code
            return Collections.emptyList();
        }
    }

    public void addRole(ManageRole newRole) {
        if (roleList.isEmpty()) {
            // If the roleList is empty, set the roleID to 1
            newRole.setRoleID(1000);
            roleList.add(newRole);
        } else if (roleList.stream().noneMatch(role -> role.getRoleName().equals(newRole.getRoleName()))) {
            newRole.setRoleID(roleList.get(roleList.size() - 1).getRoleID() + 1);
            roleList.add(newRole);
        } else {
            // Handle the case when a role with the same roleName already exists
            // You can throw an exception, log a message, or take any other appropriate
            // action.
            // For simplicity, let's log a message to the console.
            System.out.println("Role with roleName " + newRole.getRoleName() + " already exists.");
        }
    }

    public void apiAddRole(ManageRole manageRole) {
        try {
            ResponseEntity<ManageRole> responseEntity = restTemplate.postForEntity(API_Add_Role_Url, manageRole,
                    ManageRole.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ManageRole addedRole = responseEntity.getBody();
                System.out.println("Role added successfully: " + addedRole);
            } else {
                System.out.println("Failed to add role. HTTP Status: " + responseEntity.getStatusCode().value());
            }
        } catch (RestClientException e) {
            System.out.println("Failed to add role due to exception: " + e.getMessage());
        }
    }

    // public Optional<ManageRole> findById(int id) {
    // return roleList.stream().filter(t -> t.getRoleID() == id).findFirst();
    // }

    public Optional<ManageRole> apiFindById(int id) {
        List<ManageRole> roles = apiGetAllRoles();
        return roles.stream()
                .filter(role -> role.getRoleID() == id)
                .findFirst();
    }

    public void deleteRole(int id) {
        roleList.removeIf(t -> t.getRoleID() == id);
        System.out.println("Remove role ID:" + id + "Success");
    }

    public void apiDeleteRole(int id) {
        try {
            String deleteUrl = API_Delete_Role_Url + id;
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    deleteUrl,
                    HttpMethod.DELETE,
                    null,
                    String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                System.out.println("Role with ID " + id + " deleted successfully.");
            } else {
                System.out.println("Failed to delete role with ID " + id + ". HTTP Status: "
                        + responseEntity.getStatusCode().value());
            }
        } catch (RestClientException e) {
            System.out.println("Failed to delete role due to exception: " + e.getMessage());
        }
    }

    public void updateManageRole(ManageRole manageRole) {
        Optional<ManageRole> existingRoleOptional = apiFindById(manageRole.getRoleID());

        if (existingRoleOptional.isPresent()) {
            ManageRole existingRole = existingRoleOptional.get();

            existingRole.setDescription(manageRole.getDescription());
            // Check if the new roleName is not already in use
            if (roleList.stream().noneMatch(role -> role.getRoleName().equals(manageRole.getRoleName()))) {
                // Update the existing role's properties
                existingRole.setRoleName(manageRole.getRoleName());
                System.out.println("Role with ID " + existingRole.getRoleID() + " updated successfully");
            } else {
                System.out.println("Role with roleName " + manageRole.getRoleName() + " already exists.");
            }
        } else {
            // Handle the case when the role does not exist
            System.out.println("Role with ID " + manageRole.getRoleID() + " not found");
        }
    }

    // public ManageRole getRoleById(int roleID) {
    //     List<ManageRole> roles = apiGetAllRoles();
    //     return roles.stream()
    //             .filter(role -> role.getRoleID() == roleID)
    //             .findFirst()
    //             .orElse(null);
    // }

    public static ManageRole getRoleById(int roleID) {
        return roleList.stream()
                .filter(role -> role.getRoleID() == roleID)
                .findFirst()
                .orElse(null);
    }

    private List<ManageRole> parseJsonResponse(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ManageRole> manageRoles = new ArrayList<>();

        try {
            JsonNode root = objectMapper.readTree(jsonResponse);

            if (root.isArray()) {
                Iterator<JsonNode> elements = root.elements();

                while (elements.hasNext()) {
                    JsonNode node = elements.next();

                    int roleID = node.get("roleID").asInt();
                    String description = node.get("description").asText();
                    String roleName = node.get("roleName").asText();

                    ManageRole manageRole = new ManageRole(roleID, roleName, description);
                    manageRoles.add(manageRole);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }

        return manageRoles;
    }

}
