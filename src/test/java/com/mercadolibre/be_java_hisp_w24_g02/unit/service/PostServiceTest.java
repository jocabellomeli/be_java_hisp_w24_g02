package com.mercadolibre.be_java_hisp_w24_g02.unit.service;

import com.mercadolibre.be_java_hisp_w24_g02.dto.PostDto;
import com.mercadolibre.be_java_hisp_w24_g02.dto.UserFollowedsPostsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.entity.Post;
import com.mercadolibre.be_java_hisp_w24_g02.entity.Product;
import com.mercadolibre.be_java_hisp_w24_g02.entity.User;
import com.mercadolibre.be_java_hisp_w24_g02.repository.implementations.PostRepositoryImpl;
import com.mercadolibre.be_java_hisp_w24_g02.repository.implementations.UserRepositoryImpl;
import com.mercadolibre.be_java_hisp_w24_g02.service.implementations.PostServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostServiceImpl service;

    @Mock
    private PostRepositoryImpl postRepository;

    @Mock
    private UserRepositoryImpl userRepository;

    @Test
    @DisplayName("Test tha verify the Last two weeks posts")
    public void lastTwoWeeksPostsTest() {
        // Arrange
        Integer userId = 1;
        User user = new User(1, "Usuario 1", new ArrayList<>(), new ArrayList<>(), List.of(3, 5, 6, 9), List.of(2,3));
        List<Post> posts = List.of(
                new Post(2,2, LocalDate.of(2024,1,24),
                new Product(
                1002,
                "Smartphone ABC",
                "Smartphone",
                "ABC",
                "Black",
                "Último modelo de smartphone con cámara de alta resolución"
                ),
                2, 799.99),
                new Post(3,3, LocalDate.of(2023,6,10),
                new Product(
                1003,
                "Cámara DSLR 123",
                "Cámara",
                "DSLR",
                "Black",
                "Cámara profesional para fotógrafos aficionados y profesionales"
                ),
                3,
                899.99));

        UserFollowedsPostsDTO postExpected = new UserFollowedsPostsDTO(1,
                List.of(new PostDto(2,2, LocalDate.of(2024,1,24),
                new Product(
                    1002,
                    "Smartphone ABC",
                    "Smartphone",
                    "ABC",
                    "Black",
                    "Último modelo de smartphone con cámara de alta resolución"
                ),
                2, 799.99)));
        // Act
        when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(this.postRepository.findAll()).thenReturn(posts);
        var result = this.service.getFollowedPost(userId);

        // Assert
        Assertions.assertEquals(postExpected, result);
    }

}
