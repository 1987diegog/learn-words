package com.demente.ideas.learnwords.mapper;

import com.demente.ideas.learnwords.dtos.UserDTO;
import com.demente.ideas.learnwords.dtos.UsersListDTO;
import com.demente.ideas.learnwords.dtos.WordDTO;
import com.demente.ideas.learnwords.model.domain.Status;
import com.demente.ideas.learnwords.model.domain.UserList;
import com.demente.ideas.learnwords.model.domain.entity.Meaning;
import com.demente.ideas.learnwords.model.domain.entity.Tag;
import com.demente.ideas.learnwords.model.domain.entity.User;
import com.demente.ideas.learnwords.model.domain.entity.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
class MapperTests {

    ///////////////////////////////////////////////////////
    ///////////////////// TEST MAPPER /////////////////////
    ///////////////////////////////////////////////////////

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private UserMapper userMapper;

    private static User userData;

    @BeforeEach
    public void loadData() {

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

    @Test
    public void givenWordDTOFromWord() {

        Word word = new Word("Functional interface");
        word.setId(1L);

        Tag tag = new Tag("Sistemas");
        tag.setId(1L);
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        Meaning meaning = new Meaning("Se le conoce como interface funcional a toda aquella interface que tenga " +
                "solamente un método abstracto, es decir puede implementar uno o más métodos default, pero deberá " +
                "tener forzosamente un único método abstracto. Si no te queda claro que es un método abstracto en una " +
                "interface, es un método sin implementar.");
        meaning.setId(1L);
        meaning.setWord(word);

        Set<Meaning> meanings = new HashSet<>();
        meanings.add(meaning);

        word.setTags(tags);
        word.setMeanings(meanings);

        WordDTO dto = wordMapper.create(word);

        assertEquals(dto.getId(), word.getId());
        assertEquals(dto.getWord(), word.getWord());
    }

    @Test
    public void givenWordFromWordDTO() {

        WordDTO dto = new WordDTO();
        dto.setId(1L);
        dto.setWord("Hola");

        Word entity = wordMapper.create(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getWord(), entity.getWord());
    }

    @Test
    public void givenUserDTOFromUser() {

        UserDTO userDTO = userMapper.create(userData);

        assertEquals(userData.getId(), userDTO.getIdUser());
        assertEquals(userData.getName(), userDTO.getName());
        assertEquals(userData.getLastName(), userDTO.getLastName());
        assertEquals(userData.getUsername(), userDTO.getUsername());
        assertEquals(userData.getEmail(), userDTO.getEmail());
        assertEquals(userData.getStatus().name(), userDTO.getStatus());
    }

    @Test
    public void givenUserFromUserDTO() {

        User user = userMapper.create(getUserDTO());

        assertEquals(user.getId(), getUserDTO().getIdUser());
        assertEquals(user.getName(), getUserDTO().getName());
        assertEquals(user.getLastName(), getUserDTO().getLastName());
        assertEquals(user.getUsername(), getUserDTO().getUsername());
        assertEquals(user.getEmail(), getUserDTO().getEmail());
        assertEquals(user.getStatus().name(), getUserDTO().getStatus());
    }

    @Test
    public void givenUserListDTOFromUserList() {

        User user = null;
        UserList userList = new UserList();
        for (int i = 0; i < 10; i++) {
            user = new User();
            user.setId(i + 1L);
            user.setName("Diego" + i);
            user.setLastName("Gonzalez" + i);
            user.setUsername("1987diegog" + i);
            user.setEmail("1987diegog@gmail.com" + i);
            user.setStatus(Status.ENABLED);

            userList.add(user);
        }

        UsersListDTO listDTO = userMapper.create(userList);
        assertEquals(userList.getUsers().size(), listDTO.getUsers().size());
    }
}
