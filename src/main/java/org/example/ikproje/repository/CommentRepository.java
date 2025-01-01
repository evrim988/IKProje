package org.example.ikproje.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.ikproje.entity.Comment;
import org.example.ikproje.view.VwComment;
import org.example.ikproje.view.VwCommentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Boolean existsByCompanyManagerId(Long companyManagerId);

    Optional<Comment> findByCompanyManagerId(Long id);

    @Query("SELECT new org.example.ikproje.view.VwComment(co.name, CONCAT(u.firstName,' ',u.lastName),c.managerPhoto,c.companyLogo,c.content) FROM Comment c JOIN User u ON u.id=c.companyManagerId JOIN Company co ON co.id=u.companyId")
    List<VwComment> findAllVwComment();

    @Query("SELECT new org.example.ikproje.view.VwCommentDetail(co.name, CONCAT(u.firstName,' ',u.lastName),c.managerPhoto,c.companyLogo,c.content, 0L,ud.birthDate,a.city,co.phone) FROM Comment c JOIN User u ON u.id=c.companyManagerId JOIN Company co ON co.id=u.companyId JOIN UserDetails ud ON ud.userId=u.id JOIN Address a ON co.addressId=a.id WHERE c.id=1")
    VwCommentDetail findVwCommentDetailsByCommentId(Long commentId);



}
