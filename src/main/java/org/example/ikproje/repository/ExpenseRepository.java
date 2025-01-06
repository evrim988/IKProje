package org.example.ikproje.repository;

import org.example.ikproje.entity.Expense;
import org.example.ikproje.entity.User;
import org.example.ikproje.view.VwExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT new org.example.ikproje.view.VwExpense(e.id,CONCAT(u.firstName,' ',u.lastName),CONCAT(cm.firstName,' ',cm.lastName),e.status,e.amount,e.description,e.receiptUrl,e.createAt) FROM Expense e JOIN User u ON u.id=e.userId JOIN User cm ON cm.companyId=?2 WHERE u.id=?1 AND cm.userRole=org.example.ikproje.entity.enums.EUserRole.COMPANY_MANAGER AND e.state=org.example.ikproje.entity.enums.EState.ACTIVE")
    List<VwExpense> findAllVwExpenseByUserId(Long id,Long companyId);

    @Query("SELECT new org.example.ikproje.view.VwExpense(e.id,CONCAT(u.firstName,' ',u.lastName),CONCAT(cm.firstName,' ',cm.lastName),e.status,e.amount,e.description,e.receiptUrl,e.createAt) FROM Expense e JOIN User u ON u.id=e.userId JOIN User cm ON cm.companyId=?1 WHERE u.companyId=?1 AND cm.userRole=org.example.ikproje.entity.enums.EUserRole.COMPANY_MANAGER AND e.state=org.example.ikproje.entity.enums.EState.ACTIVE AND e.status=org.example.ikproje.entity.enums.EExpenseStatus.PENDING")
    List<VwExpense> findAllVwExpenseByCompanyId(Long companyId);
    
    


}