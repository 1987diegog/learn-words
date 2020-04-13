package com.demente.ideas.learnwords.services;

import com.demente.ideas.learnwords.model.domain.UserList;
import com.demente.ideas.learnwords.model.domain.entity.User;
import com.demente.ideas.learnwords.model.services.IUserService;
import com.demente.ideas.learnwords.model.services.UserService;
import com.demente.ideas.learnwords.repository.IUserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private IUserService userService = new UserService();

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

    private static List<User> getUserList() {
        return Arrays.asList(
                userData, new User(userData.getId() + 10L, userData.getName().concat("_plus"),
                        userData.getLastName().concat("_plus"), userData.getUsername().concat("_plus"),
                        userData.getPassword().concat("_plus"), userData.getEmail().concat("_plus"))
        );
    }

    @Test
    void save() throws Exception {

        ////////////////////// - ARRANGE (SETUP TEST) - //////////////////////
        when(userRepository.save(any(User.class))).thenReturn(userData);

        ///////////////////////// - ACT (EXECUTE) - /////////////////////////
        User user = userService.save(userData);

        ///////////////////// - ASSERT (VERIFY RESULT) - /////////////////////
        assertResultUser(user);

    }

    @Test
    void update() throws Exception {

        ////////////////////// - ARRANGE (SETUP TEST) - //////////////////////
        when(userRepository.findById(userData.getId())).thenReturn(Optional.of(userData));

        // Mock Save or Update
        when(userRepository.save(any(User.class))).thenReturn(userData);

        ///////////////////////// - ACT (EXECUTE) - /////////////////////////
        User user = userService.update(userData);

        ///////////////////// - ASSERT (VERIFY RESULT) - /////////////////////
        assertResultUser(user);

    }

    @Test
    void delete() throws Exception {

        ////////////////////// - ARRANGE (SETUP TEST) - //////////////////////
        when(userRepository.findById(userData.getId())).thenReturn(Optional.of(userData));

        // Mock Save or Update
        when(userRepository.save(any(User.class))).thenReturn(userData);

        ///////////////////////// - ACT (EXECUTE) - /////////////////////////
        userService.delete(userData);

        ///////////////////// - ASSERT (VERIFY RESULT) - /////////////////////
        verify(userRepository, times(1)).delete(userData);
    }

    @Test
    void findAll() throws Exception {

        ////////////////////// - ARRANGE (SETUP TEST) - //////////////////////
        when(userRepository.findAll()).thenReturn(getUserList());

        ///////////////////////// - ACT (EXECUTE) - /////////////////////////
        UserList usersList = userService.findAll();

        ///////////////////// - ASSERT (VERIFY RESULT) - /////////////////////
        assertNotNull(usersList);
        assertNotNull(usersList.getUsers());
        assertFalse(usersList.getUsers().isEmpty());
    }

    @Test
    void findById() throws Exception {

        ////////////////////// - ARRANGE (SETUP TEST) - //////////////////////
        when(userRepository.findById(userData.getId())).thenReturn(Optional.of(userData));

        ///////////////////////// - ACT (EXECUTE) - /////////////////////////
        User user = userService.findById(userData.getId());

        ///////////////////// - ASSERT (VERIFY RESULT) - /////////////////////
        assertResultUser(user);
    }

    @Test
    void findByEmail() throws Exception {

        ////////////////////// - ARRANGE (SETUP TEST) - //////////////////////
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(userData));

        ///////////////////////// - ACT (EXECUTE) - /////////////////////////
        User user = userService.findByEmail(userData.getEmail());

        ///////////////////// - ASSERT (VERIFY RESULT) - /////////////////////
        assertResultUser(user);
    }

    private void assertResultUser(User user) {
        assertNotNull(user);
        assertEquals(user.getId(), userData.getId());
        assertEquals(user.getName(), userData.getName());
        assertEquals(user.getLastName(), userData.getLastName());
        assertEquals(user.getUsername(), userData.getUsername());
        assertEquals(user.getEmail(), userData.getEmail());
        assertEquals(user.getPassword(), userData.getPassword());
    }
}