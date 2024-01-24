package com.mercadolibre.be_java_hisp_w24_g02.unit.service;

import com.mercadolibre.be_java_hisp_w24_g02.dto.UpdateToRelationshipsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.entity.User;
import com.mercadolibre.be_java_hisp_w24_g02.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w24_g02.repository.interfaces.IUserRepository;
import com.mercadolibre.be_java_hisp_w24_g02.service.implementations.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private IUserRepository userRepository;


    @Test
    @DisplayName("Verify that the user to unfollow exists.")
    public void testUnfollowUser() {
        // Arrange
        User userFinded = new User(2, "Usuario 2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        User userToUnfollow = new User(3, "Usuario 3", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        userFinded.setFollowed(List.of(userToUnfollow));
        userToUnfollow.setFollowers(List.of(userFinded));

        UpdateToRelationshipsDTO updateToRelationshipsDTO = new UpdateToRelationshipsDTO(2, 3);
        Mockito.when(userRepository.findById(2))
                .thenReturn(Optional.of(userFinded));
        Mockito.when(userRepository.findById(3))
                .thenReturn(Optional.of(userToUnfollow));
        // Act - Assert
        Assertions.assertDoesNotThrow(() -> service.unfollowUser(updateToRelationshipsDTO));
    }

    @Test
    @DisplayName("Verify that the user to unfollow does not exist.")
    public void testUnfollowUserNotFound() {
        // Arrange
        User userFinded = new User(2, "Usuario 2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        UpdateToRelationshipsDTO updateToRelationshipsDTO = new UpdateToRelationshipsDTO(2, 3);
        Mockito.when(userRepository.findById(2))
                .thenReturn(Optional.of(userFinded));
        Mockito.when(userRepository.findById(3))
                .thenReturn(Optional.empty());
        // Act - Assert
        Assertions.assertThrows(NotFoundException.class, () -> service.unfollowUser(updateToRelationshipsDTO));
    }

    @Test
    @DisplayName("Verify that the user principal exists.")
    public void testUnfollowUserPrincipalNotFound() {
        // Arrange
        UpdateToRelationshipsDTO updateToRelationshipsDTO = new UpdateToRelationshipsDTO(2, 3);
        Mockito.when(userRepository.findById(2))
                .thenReturn(Optional.empty());
        // Act - Assert
        Assertions.assertThrows(NotFoundException.class, () -> service.unfollowUser(updateToRelationshipsDTO));
    }
}
