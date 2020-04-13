package com.demente.ideas.learnwords.integration;

import com.demente.ideas.learnwords.dtos.UserDTO;
import com.demente.ideas.learnwords.dtos.UsersListDTO;
import com.demente.ideas.learnwords.model.domain.entity.User;
import com.demente.ideas.learnwords.repository.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author 1987diegog
 */
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = LearnWordsApplication.class, //
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@AutoConfigureTestDatabase
//@IntegrationTest({"server.port=0"})
//@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class}
)
public class UserRestControllerIntegretionTest {

    private Logger logger = LogManager.getLogger(UserRestControllerIntegretionTest.class);

    @MockBean
    private IUserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    public String getRootUrlUser() {
        return "/api/v1/users";
    }

    private static User userData;

    @BeforeAll
    public static void loadData() {

        Long idUser = 456789L;
        String name = "Diego_TEST";
        String lastName = "Gonzalez_TEST";
        String username = "1987diegog_TEST";
        String email = "1987diegog@gmail.com_TEST";
        String password = "pass123987";

        userData = new User(idUser, name, lastName, username, password, email);
    }

    private static UserDTO getUserDTO() {
        return new UserDTO(userData.getId(), userData.getName(),
                userData.getLastName(), userData.getUsername(),
                userData.getPassword(), userData.getEmail(), userData.getStatus().name());
    }

    private static List<User> getUserList() {
        return Arrays.asList(
                userData, new User(userData.getId() + 10L, userData.getName().concat("_plus"),
                        userData.getLastName().concat("_plus"), userData.getUsername().concat("_plus"),
                        userData.getPassword().concat("_plus"), userData.getEmail().concat("_plus"))
        );
    }

    @Test
    void testCreateUser() throws Exception {

        logger.info(" ---------------------------------------------------- ");
        logger.info(" ---------------- [TEST_CREATE_USER] ---------------- ");
        logger.info(" ---------------------------------------------------- ");

        ////////////////////// - ARRANGE (SETUP TEST) - //////////////////////

        logger.info("[TEST_CREATE_USER] - Creating user data...");

        // Mock Save or Update
        when(userRepository.save(any(User.class))).thenReturn(userData);

        // Prepare body request
        String jsonContent = mapper.writeValueAsString(getUserDTO());

        ///////////////////////// - ACT (EXECUTE) - /////////////////////////

        logger.info("[TEST_CREATE_USER] - Call mock mvc integration test...");
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
                .post(getRootUrlUser())
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        ///////////////////// - ASSERT (VERIFY RESULT) - /////////////////////

        logger.info("[TEST_CREATE_USER] - Process results...");
        MvcResult result = this.assertResponseUserData(resultActions);

        String json = result.getResponse().getContentAsString();
        UserDTO userDTO = mapper.readValue(json, UserDTO.class);

        // HTTP status code 201
        assertEquals(result.getResponse().getStatus(), HttpStatus.CREATED.value());
        assertEquals(userDTO.getIdUser(), userData.getId());
        assertNotNull(userDTO);
    }

    @Test
    void testUpdateUser() throws Exception {

        logger.info(" ----------------------------------------------------- ");
        logger.info(" ---------------- [TEST_UPDATE_USER] ----------------- ");
        logger.info(" ----------------------------------------------------- ");

        ////////////////////// - ARRANGE (SETUP TEST) - //////////////////////

        logger.info("[TEST_UPDATE_USER] - Updating user data");

        // Mock Find ById
        when(userRepository.findById(userData.getId())).thenReturn(Optional.of(userData));

        // Mock Save or Update
        when(userRepository.save(any(User.class))).thenReturn(userData);

        // Prepare body request
        String jsonContent = mapper.writeValueAsString(getUserDTO());

        ///////////////////////// - ACT (EXECUTE) - /////////////////////////

        logger.info("[TEST_UPDATE_USER] - Call mock mvc integration test...");

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
                .put(getRootUrlUser())
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        ///////////////////// - ASSERT (VERIFY RESULT) - /////////////////////

        logger.info("[TEST_UPDATE_USER] - Process results...");
        MvcResult result = this.assertResponseUserData(resultActions);
        String json = result.getResponse().getContentAsString();
        UserDTO userDTO = mapper.readValue(json, UserDTO.class);

        // HTTP status code 200
        assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
        assertEquals(userDTO.getIdUser(), userData.getId());
        assertNotNull(userDTO);
    }

    /**
     * Verify the result of GET, POST or PUT this...
     *
     * @param resultActions
     * @return
     * @throws Exception
     */
    private MvcResult assertResponseUserData(ResultActions resultActions) throws Exception {

        resultActions.andDo(print());
        resultActions.andExpect(MockMvcResultMatchers
                .jsonPath("$.name").value(userData.getName()));
        resultActions.andExpect(MockMvcResultMatchers
                .jsonPath("$.lastName").value(userData.getLastName()));
        resultActions.andExpect(MockMvcResultMatchers
                .jsonPath("$.username").value(userData.getUsername()));
        resultActions.andExpect(MockMvcResultMatchers
                .jsonPath("$.email").value(userData.getEmail()));
        resultActions.andExpect(MockMvcResultMatchers
                .jsonPath("$.status").value(userData.getStatus().name()));

        return resultActions.andReturn();
    }

    @Test
    public void deleteUserById() throws Exception {


        logger.info(" ----------------------------------------------------------- ");
        logger.info(" ----------------- [TEST_DELETE_USER_BY_ID] ------------------ ");
        logger.info(" ----------------------------------------------------------- ");

        ////////////////////// - ARRANGE (SETUP TEST) - //////////////////////

        logger.info("[TEST_DELETE_USER_BY_ID] - Deleting user...");

        // Mock Find ById
        when(userRepository.findById(userData.getId())).thenReturn(Optional.of(userData));

        // Mock Delete
        doNothing().when(userRepository).deleteById(userData.getId());

        ///////////////////////// - ACT (EXECUTE) - /////////////////////////

        logger.info("[TEST_DELETE_USER_BY_ID] - Call mock mvc integration test...");

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
                .delete(getRootUrlUser() + "/{id}", userData.getId())
                .accept(MediaType.APPLICATION_JSON));

        /////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////

        logger.info("[TEST_DELETE_USER_BY_ID] - Process results...");

        resultActions.andDo(print());
        assertEquals(resultActions.andReturn().getResponse().getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void findAllUsers() throws Exception {

        logger.info(" ----------------------------------------------------------- ");
        logger.info(" ------------------ [TEST_FIND_ALL_USERS] ------------------ ");
        logger.info(" ----------------------------------------------------------- ");

        ////////////////////// - ARRANGE (SETUP TEST) - //////////////////////

        logger.info("[TEST_FIND_ALL_USERS] - Find all users");

        // Mock Find all
        when(userRepository.findAll()).thenReturn(getUserList());

        ///////////////////////// - ACT (EXECUTE) - /////////////////////////

        logger.info("[TEST_FIND_ALL_USERS] - Call mock mvc integration test...");

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
                .get(getRootUrlUser())
                .accept(MediaType.APPLICATION_JSON));

        ///////////////////// - ASSERT (VERIFY RESULT) - /////////////////////

        logger.info("[TEST_FIND_ALL_USERS] - Process results...");

        resultActions.andDo(print());

        MvcResult result = resultActions.andReturn();
        String json = result.getResponse().getContentAsString();
        UsersListDTO list = mapper.readValue(json, UsersListDTO.class);

        // HTTP status code 200
        assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
        assertNotNull(list);
    }


    @Test
    public void getUserById() throws Exception {

        logger.info(" ----------------------------------------------------------- ");
        logger.info(" ----------------- [TEST_FIND_USER_BY_ID] ------------------ ");
        logger.info(" ----------------------------------------------------------- ");

        ////////////////////// - ARRANGE (SETUP TEST) - //////////////////////

        // Mock Find ById
        when(userRepository.findById(userData.getId())).thenReturn(Optional.of(userData));

        ///////////////////////// - ACT (EXECUTE) - /////////////////////////

        logger.info("[TEST_FIND_USER_BY_ID] - Call mock mvc integration test...");

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
                .get(getRootUrlUser() + "/{id}", userData.getId())
                .accept(MediaType.APPLICATION_JSON));

        ///////////////////// - ASSERT (VERIFY RESULT) - /////////////////////

        logger.info("[TEST_FIND_USER_BY_ID] - Process results...");
        MvcResult result = this.assertResponseUserData(resultActions);
        String json = result.getResponse().getContentAsString();
        UserDTO userDTO = mapper.readValue(json, UserDTO.class);

        // HTTP status code 200
        assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
        assertNotNull(userDTO);
        assertEquals(userDTO.getIdUser(), userData.getId());
    }
}


