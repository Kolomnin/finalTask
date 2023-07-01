package com.example.finaltask.controller;

import com.example.finaltask.mapping.UserMapper;
import com.example.finaltask.model.dto.*;
import com.example.finaltask.model.entity.User;
import com.example.finaltask.repository.AvatarRepository;
import com.example.finaltask.repository.UserRepository;
import com.example.finaltask.service.AdsImageService;
import com.example.finaltask.service.AvatarService;
import com.example.finaltask.service.UserService;
import com.example.finaltask.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    private final UserService userService;

    private final AvatarService avatarService;
    private final AvatarRepository avatarRepository;

    private final AuthServiceImpl authService;

    private final UserRepository userRepository;
    private final AdsImageService adsImageService;




    private UserMapper userMapping;

    /**
     * PasswordEncoder предназначен для хеширования паролей. Он используется для шифрования паролей пользователей,
     * чтобы сохранить их в безопасной форме в базе данных или другом хранилище.
     */
    private PasswordEncoder passwordEncoder;




    /**
     * Этот код служит для документирования API, чтобы разработчики и потребители могли лучше понять,
     * как использовать и взаимодействовать с операцией изменения пароля пользователя. Он также может быть
     * использован различными инструментами для автоматической генерации документации или клиентских
     * библиотек для работы с API.
     */
    @Operation(
            operationId = "setPassword",
            summary = "Обновление пароля",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = NewPasswordDTO.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )

    /**
     * updatePassword метод контроллера, который обрабатывает POST-запрос на пути "/set_password".
     *
     * @RequestBody NewPasswordDTO newPassword - аннотация @RequestBody указывает,
     * что данные запроса должны быть преобразованы в объект NewPasswordDTO, который является моделью
     * данных для нового пароля. Входные данные запроса будут автоматически преобразованы в объект NewPasswordDTO
     * на основе содержимого тела запроса.
     *
     * Знак вопроса (?) указывает на использование неопределенного типа данных.
     *
     * Метод возвращает ResponseEntity с кодом состояния HTTP HttpStatus.OK, что означает успешное выполнение операции.
     * В данном случае, возвращается пустое тело ответа (new ResponseEntity<>(HttpStatus.OK)),
     * но можно также вернуть любой другой объект или модель данных, который будет сериализован и отправлен в теле ответа.
    */
//    @PostMapping("/set_password")
//    public ResponseEntity<?> updatePassword(@RequestBody NewPasswordDTO newPassword, Authentication authentication) {
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDTO> setPassword(@RequestBody NewPasswordDTO newPassword,
                                                      Authentication authentication) {
        log.info("Set password: " + newPassword);
        Optional<User> user = userRepository.findByLogin(authentication.getName());
//        user.setPassword(newPassword.getNewPassword());

//        authService.changePassword(newPassword, authentication.getName());
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (authService.changePassword(newPassword, authentication.getName())) {
            User existingUser = user.get();
            existingUser.setPassword(newPassword.getNewPassword());
            userRepository.save(existingUser);

//            authService.changePassword(newPassword, authentication.getName());
            return ResponseEntity.ok(new NewPasswordDTO());
        }


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            operationId = "getUser",
            summary = "Получить информацию об авторизованном пользователе",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = UserDTO.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    /**
     * Метод должен обрабатывать HTTP GET-запросы на указанном пути ("/me")
     */
//    @GetMapping("/me")
//    public ResponseEntity<UserDTO> getUser(Authentication authentication) {
//        userDTOInterface.getUser();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//    @GetMapping("{id}")
//    public ResponseEntity<User> getUser(@PathVariable Long id) {
//
//        return ResponseEntity.ok(userService.getUserById(id));
//    }
    // produces в аннотации GetMapping нужно указать для того, чтобы браузер понимал, что передается картинка
//    @GetMapping(value = "/images/{id}/", produces = {MediaType.IMAGE_PNG_VALUE})
//    public byte[] getAdsImage() {
//        UserAvatar enityt= avatarRepository.findById(1L).orElseThrow(null);
//         return enityt.getBytes();
//    }
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable () Integer id){
         userService.deleteUserById(id);
    }
//    @Operation(summary = "Изменение параметров владельца",
//            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Новый владелец",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
//            ))
//    @PutMapping
//    public ResponseEntity<User> editUser(@RequestBody UserDTO user,Authentication authentication) {
//        User foundUser = userService.editUser(user,authentication);
//        System.out.println("запрос на смену имени, фамилии");
//        if (foundUser == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(foundUser);
//    }

@GetMapping("/me")
public ResponseEntity<Optional<UserDTO>> getUser(Authentication authentication) {
    return ResponseEntity.ok(userService.getUserByLogin(authentication.getName()));
}


    @Operation(
            operationId = "updateUser",
            summary = "Обновить информацию об авторизованном пользователе",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = UserDTO.class))
                    }),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    /**
     * Данный метод должен обрабатывать HTTP PATCH-запросы на указанном пути ("/me"). HTTP метод PATCH используется
     * для обновления информации о текущем пользователе.
     */
    @PatchMapping("/me")
    public ResponseEntity<UserDTO> updateUser(@RequestBody RegisterReq user, Authentication authentication) {
        System.out.println("запрос на смену имени, фамилии");
        User foundUser = userService.editUser(user,authentication);
        UserDTO dto = userMapping.toDto(foundUser);
        logger.info("Updating user: {}", user.getFirstName());

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @Operation(
            operationId = "updateUserImage",
            summary = "Обновить аватар авторизованного пользователя",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    /**
     * Обрабатывает HTTP PATCH-запросы на указанном пути ("/me/image"). HTTP метод PATCH используется для
     * обновления изображения пользователя.
     *
     * value = "/me/image" - это путь, по которому будет доступен данный метод.
     *
     * consumes = MediaType.MULTIPART_FORM_DATA_VALUE - это атрибут consumes аннотации, который указывает,
     * какой тип данных может быть принят в запросе. В данном случае, MediaType.MULTIPART_FORM_DATA_VALUE указывает,
     * что метод ожидает получить данные формы с использованием кодировки multipart/form-data. Это обычно используется
     * для загрузки файлов.
     * Возвращаемый тип ResponseEntity<Void> указывает, что ответ не содержит тела.
     *
     * @RequestParam("image") MultipartFile image - аннотация @RequestParam указывает, что метод ожидает получить
     * параметр image из запроса. MultipartFile - это тип данных, представляющий загруженный файл. В данном случае,
     * параметр image будет содержать загруженное изображение пользователя.
     *
     * Метод возвращает ResponseEntity с кодом состояния HTTP 200 (OK), что означает успешное выполнение
     * операции обновления изображения пользователя. В данном случае, возвращается пустое тело
     * ответа (ResponseEntity.status(200).build()).
     */
//    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<AvatarDTO> updateUserImage(@RequestParam("image") MultipartFile file, Authentication authentication) throws IOException {
//
//        UserAvatar entity = new UserAvatar();
//        try {
//            // код, который кладет картинку в entity
//            byte[] bytes = file.getBytes();
//            entity.setBytes(bytes);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        entity.setId(Long.valueOf(UUID.randomUUID().toString()));
//        // код сохранения картинки в БД
//         UserAvatar savedEntity = avatarRepository.saveAndFlush(entity);
//        return savedEntity.getId();

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestParam("image") MultipartFile image) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("User {} update avatar", authentication.getName());
        adsImageService.saveAvatar(authentication.getName(), image);
        return ResponseEntity.status(200).build();
    }

    @GetMapping(value = "/{id}/getImage")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id) {
        log.info("Get avatar from user with id " + id);
        return ResponseEntity.ok(adsImageService.getAvatar(id));
    }

            // код сохранения картинки в БД
//            UserAvatar savedEntity = avatarRepository.save(entity);
//            return savedEntity.getId();
//        System.out.println("запрос на смену аватарки пользователя вызван");
//        System.out.println(authentication.getName());
//        avatarService.saveImage(file,authentication);
//        AvatarDTO avatar = imageMapper.toDTO( avatarService.saveImage(file,authentication));
//        return new ResponseEntity<>(HttpStatus.CREATED);
    }



