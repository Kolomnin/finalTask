package com.example.finaltask.controller;

import com.example.finaltask.model.dto.*;
import com.example.finaltask.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")

public class CommentController {

    private final CommentService commentService;

    @Operation(
            operationId = "getComments",
            summary = "Получить комментарии объявления",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "ОК",
                            content = {@Content(
                                    mediaType = "*/*",
                                    schema = @Schema(implementation = CommentDTO.class))
                            }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )

    /**
     * Получить комментарии объявления
     * Обрабатывает GET-запрос на пути с переменной {id} и возвращает список комментариев для указанного идентификатора.
     * public ResponseEntity<ResponseWrapperComment<CommentDTO>> - это тип возвращаемого значения метода. В данном случае
     * используется ResponseEntity, который представляет собой ответ на HTTP-запрос, включающий статус ответа,
     * заголовки и тело ответа. ResponseWrapperComment<CommentDTO> - это класс, представляющий счетчик и список комментариев.
     */
    @GetMapping("{id}/comments")
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable Integer id,
                                                               Authentication authentication) {
        return ResponseEntity.ok(commentService.getAllCommentsByAdsId(id,authentication));
    }

    @Operation(
            operationId = "addComment",
            summary = "Добавить комментарий к объявлению",
            tags = {"Комментарии"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(
                                    mediaType = "*/*",
                                    schema = @Schema(implementation = CommentDTO.class))
                            }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )

    /**
     * Добавить комментарий к объявлению
     * Обрабатывает POST-запрос на пути с переменной {id} и создает новый комментарий для указанного идентификатора/
     * @PathVariable Integer id - аннотация @PathVariable указывает, что значение переменной {id} в пути должно быть
     * связано с параметром id метода. Integer id - это тип данных переменной id, которая будет содержать
     * значение идентификатора.
     *
     * @Parameter(description = "Необходимо корректно заполнить комментарий", example = "Тест")
     * @RequestBody CommentDTO commentDTO - аннотация @Parameter используется для описания параметра commentDTO.
     * description - это описание параметра, которое указывает на необходимость корректного заполнения комментария.
     * example - это пример значения параметра. @RequestBody указывает, что параметр commentDTO должен быть получен
     * из тела запроса и преобразован в объект типа CommentDTO.
     *
     * метод возвращает ResponseEntity с кодом состояния HTTP 201 (CREATED), что означает успешное создание нового
     * комментария. В данном случае, возвращается пустое тело комментария (new ResponseEntity<>(HttpStatus.CREATED)).
     */
    @PostMapping("{id}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Integer id, @Parameter(description = "комментарий", example = "комментарий") @RequestBody CreateCommentDTO comment, Authentication authentication) {
        return ResponseEntity.ok(commentService.addComment( id,comment, authentication));
    }


    @Operation(
            operationId = "deleteComment",
            summary = "Удалить комментарий",
            tags = "Комментарии",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаляем комментарий ",
                            content = {@Content(
                                    mediaType = "*/*",
                                    schema = @Schema(implementation = CommentDTO.class))
                            }
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }

    )

    /**
     * Удалить комментарий
     * Обрабатывает DELETE-запрос на пути с переменными {adId} и {commentId} и удаляет комментарий с указанными
     * идентификаторами.
     *
     * "{adId}/comments/{commentId}" - это путь, по которому будет доступен данный метод.
     * {adId} и {commentId} - это переменные пути, которые будут заменены на фактические значения идентификаторов
     * в запросе. Например, если запрос будет /ads/123/comments/456, то переменные {adId} и {commentId} будут
     * заменены на значения 123 и 456 соответственно.
     *
     * @PathVariable Integer adId - аннотация @PathVariable указывает, что значение переменной {adId} в пути
     * должно быть связано с параметром adId метода. Integer adId - это тип данных переменной adId, которая
     * будет содержать значение идентификатора объявления.
     *
     * @PathVariable Integer commentId - аннотация @PathVariable указывает, что значение переменной {commentId}
     * в пути должно быть связано с параметром commentId метода. Integer commentId - это тип данных переменной
     * commentId, которая будет содержать значение идентификатора комментария.
     *
     * Метод возвращает ResponseEntity с кодом состояния HTTP 204 (NO_CONTENT), что означает успешное удаление
     * комментария. В данном случае, возвращается пустое тело (new ResponseEntity<>(HttpStatus.NO_CONTENT)).
     */
    @PreAuthorize("hasRole('ADMIN') or " +
            "@commentService.isOwnerCommentById(#commentId).getEmail()==authentication.principal.username")
    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        commentService.deleteCommentById(adId,commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
//    @PutMapping
//    public ResponseEntity<Comment> editComment(@RequestBody Comment comment) {
//        Comment comment1 = commentService.editComment(comment);
//        if (comment1 == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(comment1);
//    }


    @Operation(
            operationId = "patchComment",
            summary = "Обновить комментарий",
            tags = "Комментарии",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Изменяемый комментарий ",
                            content = {@Content(
                                    mediaType = "*/*",
                                    schema = @Schema(implementation = CommentDTO.class))
                            }
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )

    /**
     * Обновить комментарий
     * Обрабатывает PATCH-запрос на пути с переменными {adId} и {commentId} и обновляет комментарий с
     * указанными идентификаторами.
     *
     * @RequestBody CommentDTO commentDTO - аннотация @RequestBody указывает, что параметр commentDTO должен
     * быть получен из тела запроса и преобразован в объект типа CommentDTO. Этот параметр представляет обновленные
     * данные комментария.
     *
     * @PathVariable Integer adId - аннотация @PathVariable указывает, что значение переменной {adId} в пути
     * должно быть связано с параметром adId метода. Integer adId - это тип данных переменной adId, которая будет
     * содержать значение идентификатора объявления.
     *
     * @PathVariable Integer commentId - аннотация @PathVariable указывает, что значение переменной {commentId}
     * в пути должно быть связано с параметром commentId метода. Integer commentId - это тип данных переменной
     * commentId, которая будет содержать значение идентификатора комментария.
     *
     * Метод возвращает ResponseEntity с кодом состояния HTTP 200 (OK), что означает успешное обновление комментария.
     * В данном случае, используется ResponseEntity.ok() для создания успешного ответа без тела. Метод build()
     * вызывается на созданном ResponseEntity, чтобы построить финальный ответ.
     */
    @PreAuthorize("hasRole('ADMIN') or " +
            "@commentService.isOwnerCommentById(#commentId).getEmail()==authentication.principal.username")
    @PatchMapping("{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO commentDTO,
                                                    @PathVariable Integer adId,
                                                    @PathVariable Integer commentId) {
        return ResponseEntity.ok(commentService.editComment(adId,commentId,commentDTO));
    }
}
