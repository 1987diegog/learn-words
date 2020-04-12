package com.demente.ideas.learnwords;

import com.demente.ideas.learnwords.dtos.UserDTO;
import com.demente.ideas.learnwords.model.domain.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author 1987diegog
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LearnWordsApplication.class, //
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserResourceTest {

    private Logger logger = LogManager.getLogger(UserResourceTest.class);

    @LocalServerPort
    public int port;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private static UserDTO userDTO;

    public String getRootUrlUser() {
        return "http://localhost:" + port + "/api/v1/users";
    }

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testCreateOriginUser() throws Exception {

        logger.info(" ----------------------------------------------------------- ");
        logger.info(" ---------------- [TEST_CREATE_ORIGIN_USER] ---------------- ");
        logger.info(" ----------------------------------------------------------- ");

        /////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////

        logger.info("[TEST_CREATE_ORIGIN_USER] - Creating user data...");

        String name = "Diego_TEST";
        String lastName = "Gonzalez_TEST";
        String username = "1987diegog_TEST";
        String email = "1987diegog@gmail.com_TEST";
        String status = Status.ENABLED.name();

        UserDTO user = new UserDTO(1L, name, lastName, username, email, status);

        String jsonContent = mapper.writeValueAsString(user);

        /////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////

        logger.info("[TEST_CREATE_ORIGIN_USER] - Call mock mvc...");

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
                .post(getRootUrlUser())
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        /////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////

        logger.info("[TEST_CREATE_ORIGIN_USER] - Process results...");

        resultActions.andDo(print());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(lastName));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(status));

        MvcResult result = resultActions.andReturn();
        String json = result.getResponse().getContentAsString();
        // I save the created object
        userDTO = mapper.readValue(json, UserDTO.class);

        logger.info("[TEST_CREATE_ORIGIN_USER] - Assigned id: " + userDTO.getIdUser());

        assertEquals(result.getResponse().getStatus(), HttpStatus.CREATED.value());
        assertNotNull(userDTO);
    }

//    @Test
//    @Order(2)
//    void testUpdateUserById() throws Exception {
//
//        logger.info(" ----------------------------------------------------------- ");
//        logger.info(" ---------------- [TEST_UPDATE_USER_BY_ID] ----------------- ");
//        logger.info(" ----------------------------------------------------------- ");
//
//        /////////////////////////////////////////////////////////////////////////////
//        /////////////////////////////////////////////////////////////////////////////
//
//        logger.info("[TEST_UPDATE_USER_BY_ID] - Updating user data, id: " + userDTO.getIdUser());
//
//        userDTO.setName("Diego Andres");
//        userDTO.setLastName("Gonzalez Durand");
//        userDTO.setEmail("1987diegogTestUpdate@gmail.com");
//        userDTO.setStatus(Status.DISABLE.name());
//        userDTO.setUsername("1987diegogTestUpdate");
//
//        String jsonContent = mapper.writeValueAsString(userDTO);
//
//        /////////////////////////////////////////////////////////////////////////////
//        /////////////////////////////////////////////////////////////////////////////
//
//        logger.info("[TEST_UPDATE_USER_BY_ID] - Call mock mvc...");
//
//        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
//                .put(getRootUrlUser())
//                .content(jsonContent)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));
//
//        /////////////////////////////////////////////////////////////////////////////
//        /////////////////////////////////////////////////////////////////////////////
//
//        logger.info("[TEST_UPDATE_USER_BY_ID] - Process results...");
//
//        resultActions.andDo(print());
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Diego Andres"));
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("1987diegogTestUpdate@gmail.com"));
//
//        MvcResult result = resultActions.andReturn();
//        String json = result.getResponse().getContentAsString();
//        userDTO = mapper.readValue(json, UserDTO.class);
//
//        assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
//        assertNotNull(userDTO);
//        assertEquals("Diego Andres", userDTO.getName());
//        assertEquals("1987diegogTestUpdate@gmail.com", userDTO.getEmail());
//
//    }
//
//    @Test
//    @Order(3)
//    public void getUserById() throws Exception {
//
//        logger.info(" ----------------------------------------------------------- ");
//        logger.info(" ----------------- [TEST_FIND_USER_BY_ID] ------------------ ");
//        logger.info(" ----------------------------------------------------------- ");
//
//        /////////////////////////////////////////////////////////////////////////////
//        /////////////////////////////////////////////////////////////////////////////
//
//        logger.info("[TEST_FIND_USER_BY_ID] - Call mock mvc...");
//
//        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
//                .get(getRootUrlUser() + "/{id}", userDTO.getIdUser())
//                .accept(MediaType.APPLICATION_JSON));
//
//        /////////////////////////////////////////////////////////////////////////////
//        /////////////////////////////////////////////////////////////////////////////
//
//        logger.info("[TEST_FIND_USER_BY_ID] - Process results...");
//
//        resultActions.andDo(print());
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Diego Andres"));
//        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("1987diegogTestUpdate@gmail.com"));
//
//        MvcResult result = resultActions.andReturn();
//        String json = result.getResponse().getContentAsString();
//        UserDTO user = mapper.readValue(json, UserDTO.class);
//
//        assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
//        assertNotNull(user);
//        assertEquals("Diego Andres", user.getName());
//        assertEquals("1987diegogTestUpdate@gmail.com", user.getEmail());
//    }
//
//
//    @Test
//    @Order(4)
//    public void findAllUsers() throws Exception {
//
//        logger.info(" ----------------------------------------------------------- ");
//        logger.info(" ------------------ [TEST_FIND_ALL_USERS] ------------------ ");
//        logger.info(" ----------------------------------------------------------- ");
//
//        /////////////////////////////////////////////////////////////////////////////
//        /////////////////////////////////////////////////////////////////////////////
//
//        logger.info("[TEST_FIND_ALL_USERS] - Call mock mvc...");
//
//        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
//                .get(getRootUrlUser())
//                .accept(MediaType.APPLICATION_JSON));
//
//        /////////////////////////////////////////////////////////////////////////////
//        /////////////////////////////////////////////////////////////////////////////
//
//        logger.info("[TEST_FIND_ALL_USERS] - Process results...");
//
//        resultActions.andDo(print());
//
//        MvcResult result = resultActions.andReturn();
//        String json = result.getResponse().getContentAsString();
//        UsersListDTO list = mapper.readValue(json, UsersListDTO.class);
//
//        assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
//        assertNotNull(list);
//    }
//
//    @Test
//    @Order(6)
//    public void deleteUserById() throws Exception {
//
//        logger.info(" ----------------------------------------------------------- ");
//        logger.info(" ----------------- [TEST_DELETE_USER_BY_ID] ------------------ ");
//        logger.info(" ----------------------------------------------------------- ");
//
//        /////////////////////////////////////////////////////////////////////////////
//        /////////////////////////////////////////////////////////////////////////////
//
//        logger.info("[TEST_DELETE_USER_BY_ID] - Call mock mvc...");
//
//        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
//                .delete(getRootUrlUser() + "/{id}", userDTO.getIdUser())
//                .accept(MediaType.APPLICATION_JSON));
//
//        /////////////////////////////////////////////////////////////////////////////
//        /////////////////////////////////////////////////////////////////////////////
//
//        logger.info("[TEST_DELETE_USER_BY_ID] - Process results...");
//
//        resultActions.andDo(print());
//        assertEquals(resultActions.andReturn().getResponse().getStatus(), HttpStatus.OK.value());
//    }
}


