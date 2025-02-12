package org.example.ikproje.controller;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.service.CommentService;
import org.example.ikproje.view.VwComment;
import org.example.ikproje.view.VwCommentDetail;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.example.ikproje.constant.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMMENT)
@CrossOrigin("*")
public class CommentController {

    private final CommentService commentService;


    @PostMapping(value = CREATE_COMMENT,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('COMPANY_MANAGER')")
    public ResponseEntity<BaseResponse<Boolean>> createComment(@RequestParam String token,
                                                               @RequestParam String content,
                                                               @RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .success(true)
                        .code(200)
                        .message("Comment başarı ile oluşturuldu.")
                        .data(commentService.createComment(token, content, file))
                .build());
    }

    //Ana sayfa ziyaretçiler için 5 random yönetici Commenti
    @GetMapping(GET_COMMENTS)
    public ResponseEntity<BaseResponse<List<VwComment>>> getComments(){
        return ResponseEntity.ok(BaseResponse.<List<VwComment>>builder()
                .data(commentService.findAllComments())
                .success(true)
                .code(200)
                .message("Ana sayfa yorumlar listesi.")
                .build());
    }

    //Ziyaretçi bir commenta tıklarsa o şirket/yönetici ile ilgili detaylı bilgi görmesi için
    @GetMapping(GET_COMMENT)
    public ResponseEntity<BaseResponse<VwCommentDetail>> getComments(@RequestParam Long commentId){
        return ResponseEntity.ok(BaseResponse.<VwCommentDetail>builder()
                .data(commentService.getCommentDetail(commentId))
                .success(true)
                .code(200)
                .message("Yorum detayı.")
                .build());
    }

    @GetMapping(GET_COMMENTSBYCOMPANYID)
    public ResponseEntity<BaseResponse<List<VwComment>>> getCommentsByCompanyId(@RequestParam String token){
        return ResponseEntity.ok(BaseResponse.<List<VwComment>>builder()
                        .code(200)
                        .success(true)
                        .data(commentService.findAllCommentsByCompanyId(token))
                        .message("Yorumlar başarı ile getirildi.")
                .build());
    }

    @PutMapping(value = UPDATE_COMMENT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('COMPANY_MANAGER')")
    public ResponseEntity<BaseResponse<Boolean>> updateComment(@RequestParam String token,
                                                               @RequestParam String content,
                                                               @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("Comment başarı ile güncellendi.")
                .data(commentService.updateComment(token, content, file))
                .build());
    }

    @PutMapping(DELETE_COMMENT)
    @PreAuthorize("hasAuthority('COMPANY_MANAGER')")
    public ResponseEntity<BaseResponse<Boolean>> deleteComment(@RequestParam String token,@RequestParam Long commentId){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .code(200)
                        .success(true)
                        .data(commentService.deleteComment(token, commentId))
                        .message("Comment silme işlemi başarılı")
                .build());
    }

    @PostMapping(value = UPDATE_MANAGER_PHOTO,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('COMPANY_MANAGER')")
    public ResponseEntity<BaseResponse<Boolean>> addManagerPhoto(@RequestParam String token,
                                                                 @RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("Comment fotosu güncellendi.")
                .data(commentService.addManagerPhotoToComment(token, file))
                .build());
    }

    @PostMapping(value = UPDATE_COMPANY_PHOTO,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('COMPANY_MANAGER')")
    public ResponseEntity<BaseResponse<Boolean>> addCompanyLogo(@RequestParam String token,
                                                                 @RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("Comment fotosu güncellendi.")
                .data(commentService.addCompanyLogoToComment(token, file))
                .build());
    }




}