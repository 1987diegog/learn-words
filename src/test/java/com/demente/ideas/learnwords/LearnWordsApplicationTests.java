package com.demente.ideas.learnwords;

import com.demente.ideas.learnwords.dtos.UsersListDTO;
import com.demente.ideas.learnwords.dtos.UserDTO;
import com.demente.ideas.learnwords.dtos.WordDTO;
import com.demente.ideas.learnwords.mapper.UserMapper;
import com.demente.ideas.learnwords.mapper.WordMapper;
import com.demente.ideas.learnwords.model.domain.UserList;
import com.demente.ideas.learnwords.model.domain.Status;
import com.demente.ideas.learnwords.model.entity.Meaning;
import com.demente.ideas.learnwords.model.entity.Tag;
import com.demente.ideas.learnwords.model.entity.User;
import com.demente.ideas.learnwords.model.entity.Word;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LearnWordsApplication.class, //
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LearnWordsApplicationTests {

    @LocalServerPort
    public int port;

//	@Test
//	void contextLoads() {
//	}

    ///////////////////////////////////////////////////////
    ///////////////////// TEST MAPPER /////////////////////
    ///////////////////////////////////////////////////////

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private UserMapper userMapper;

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

        User user = new User();
        user.setId(1L);
        user.setName("Diego");
        user.setLastName("Gonzalez");
        user.setUsername("1987diegog");
        user.setEmail("1987diegog@gmail.com");
        user.setStatus(Status.ENABLED);

        UserDTO userDTO = userMapper.create(user);

        assertEquals(user.getId(), userDTO.getIdUser());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getStatus().name(), userDTO.getStatus());
    }

    @Test
    public void givenUserFromUserDTO() {

        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(1L);
        userDTO.setName("Diego");
        userDTO.setLastName("Gonzalez");
        userDTO.setUsername("1987diegog");
        userDTO.setEmail("1987diegog@gmail.com");
        userDTO.setStatus("ENABLED");

        User user = userMapper.create(userDTO);

        assertEquals(user.getId(), userDTO.getIdUser());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getStatus().name(), userDTO.getStatus());
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
