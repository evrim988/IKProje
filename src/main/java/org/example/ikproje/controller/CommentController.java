package org.example.ikproje.controller;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.dto.response.BaseResponse;
import org.example.ikproje.service.CommentService;
import org.example.ikproje.view.VwComment;
import org.example.ikproje.view.VwCommentDetail;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("create-comment")
    public ResponseEntity<BaseResponse<Boolean>> createComment(@RequestParam String token, @RequestParam String content){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .success(commentService.createComment(token, content))
                        .code(200)
                        .message("Comment başarı ile oluşturuldu.")
                .build());
    }

    //Ana sayfa ziyaretçiler için 5 random yönetici Commenti
    @GetMapping("/get-comments")
    public ResponseEntity<BaseResponse<List<VwComment>>> getComments(){
        return ResponseEntity.ok(BaseResponse.<List<VwComment>>builder()
                .data(commentService.findAllComments())
                .success(true)
                .code(200)
                .message("Ana sayfa yorumlar listesi.")
                .build());
    }

    //Ziyaretçi bir commenta tıklarsa o şirket/yönetici ile ilgili detaylı bilgi görmesi için
    @GetMapping("/get-comment")
    public ResponseEntity<BaseResponse<VwCommentDetail>> getComments(@RequestParam Long commentId){
        return ResponseEntity.ok(BaseResponse.<VwCommentDetail>builder()
                .data(commentService.getCommentDetail(commentId))
                .success(true)
                .code(200)
                .message("Yorum detayı.")
                .build());
    }

    @PutMapping("/update-comment")
    public ResponseEntity<BaseResponse<Boolean>> updateComment(@RequestParam String token,@RequestParam String content){
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(commentService.updateComment(token, content))
                .code(200)
                .message("Comment başarı ile güncellendi.")
                .build());
    }

    @PostMapping(value = "update-manager-photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<Boolean>> addManagerPhoto(@RequestParam String token,
                                                                 @RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(commentService.addManagerPhotoToComment(token, file))
                .code(200)
                .message("Comment fotosu güncellendi.")
                .build());
    }

    @PostMapping(value = "update-company-logo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<Boolean>> addCompanyLogo(@RequestParam String token,
                                                                 @RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(commentService.addCompanyLogoToComment(token, file))
                .code(200)
                .message("Comment fotosu güncellendi.")
                .build());
    }




}
