package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.Comment;
import org.example.ikproje.entity.Company;
import org.example.ikproje.entity.User;
import org.example.ikproje.entity.enums.EState;
import org.example.ikproje.entity.enums.EUserRole;
import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;
import org.example.ikproje.repository.CommentRepository;
import org.example.ikproje.view.VwComment;
import org.example.ikproje.view.VwCommentDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final CompanyService companyService;

    //En başta sadece 1 kere comment oluşturabilsin yönetici, sonradan değiştirmek için update Methodunu kullanabilir.
    public Boolean createComment(String token,String content, MultipartFile file) throws IOException {
        User companyManager = userService.getUserByToken(token);
        if(!companyManager.getUserRole().equals(EUserRole.COMPANY_MANAGER)) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        if(commentRepository.existsByCompanyManagerId(companyManager.getId())) throw new IKProjeException(ErrorType.COMMENT_ALREADY_EXIST);
        Optional<Company> optionalCompany = companyService.findById(companyManager.getId());
        if(optionalCompany.isEmpty()) throw new IKProjeException(ErrorType.COMPANY_NOTFOUND);
        Comment comment = Comment.builder().companyManagerId(companyManager.getId()).content(content).build();
        comment.setManagerPhoto(cloudinaryService.uploadFile(file));
        commentRepository.save(comment);
        return true;
    }

    //Ziyaretçi için ana sayfada toplamda 5 tane rastgele comment dönüyor.
    public List<VwComment> findAllComments(){
        List<VwComment> comments = commentRepository.findAllVwComment();
        if(comments.size()<=5){
            return comments;
        }
        Collections.shuffle(comments);
        return comments.subList(0,5);
    }

    public List<VwComment> findAllCommentsByCompanyId(String token){
        User companyManager = userService.getUserByToken(token);
        if(!companyManager.getUserRole().equals(EUserRole.COMPANY_MANAGER)) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        return commentRepository.findAllVwCommentByCompanyManagerId(companyManager.getId());
    }

    //Ana sayfada bir yoruma tıkladığında detaya gidebilsin diye ziyaretçi
    public VwCommentDetail getCommentDetail(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new IKProjeException(ErrorType.COMMENT_NOT_FOUND));
        VwCommentDetail commentDetail = commentRepository.findVwCommentDetailsByCommentId(commentId);
        Long totalPersonelCount = userService.getTotalPersonelCountByCompanyManagerId(comment.getCompanyManagerId());
        commentDetail.setTotalPersonel(totalPersonelCount);
        return commentDetail;
    }


    public Boolean updateComment(String token,String newContent, MultipartFile file) throws IOException {
        User companyManager = userService.getUserByToken(token);
        if(!companyManager.getUserRole().equals(EUserRole.COMPANY_MANAGER)) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        Comment comment = commentRepository.findByCompanyManagerId(companyManager.getId()).orElseThrow(()->new IKProjeException(ErrorType.COMMENT_NOT_FOUND));
        comment.setContent(newContent);
        if(file != null){
            comment.setManagerPhoto(cloudinaryService.uploadFile(file));
        }
        commentRepository.save(comment);
        return true;
    }

    public Boolean deleteComment(String token, Long commentId){
        User companyManager = userService.getUserByToken(token);
        if(!companyManager.getUserRole().equals(EUserRole.COMPANY_MANAGER)) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if(optionalComment.isEmpty()) throw new IKProjeException(ErrorType.COMMENT_NOT_FOUND);
        Comment comment = optionalComment.get();
        commentRepository.delete(comment);
        return true;
    }

    public Boolean addManagerPhotoToComment(String token,MultipartFile file) throws IOException{
        Comment comment = getCommentByToken(token);
        comment.setManagerPhoto(cloudinaryService.uploadFile(file));
        commentRepository.save(comment);
        return true;
    }

    public Boolean addCompanyLogoToComment(String token,MultipartFile file) throws IOException{
        Comment comment = getCommentByToken(token);
        comment.setCompanyLogo(cloudinaryService.uploadFile(file));
        commentRepository.save(comment);
        return true;
    }

    private Comment getCommentByToken(String token){
        User companyManager = userService.getUserByToken(token);
        if(companyManager.getUserRole().equals(EUserRole.EMPLOYEE)) throw new IKProjeException(ErrorType.UNAUTHORIZED);
        return commentRepository.findByCompanyManagerId(companyManager.getId()).orElseThrow(()->new IKProjeException(ErrorType.COMMENT_NOT_FOUND));
    }



}
